package com.stockcompare.repository;

import com.stockcompare.data.SQLiteDatabase;
import com.stockcompare.domain.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 * SavedStockRepository — SQLite implementation of ISavedStockRepository.
 * Matches "Database" in Save/Load/Delete sequence diagrams:
 *   storeData()        → SavedStockRepository (Save diagram)
 *   fetchSavedStocks() → SavedStockRepository (Load diagram)
 *   fetchStockData()   → SavedStockRepository (Load diagram)
 *   removeStock()      → SavedStockRepository (Delete diagram)
 *
 * SOLID — Single Responsibility: saved stock persistence only.
 */
public class SavedStockRepository implements ISavedStockRepository {

    private final SQLiteDatabase  db;
    private final IStockRepository stockRepo;

    public SavedStockRepository(SQLiteDatabase db, IStockRepository stockRepo) {
        this.db        = db;
        this.stockRepo = stockRepo;
    }

    // storeData() in Save sequence diagram
    @Override
    public SavedStock save(String userId, String symbol,
                           LocalDate start, LocalDate end,
                           List<PriceData> data) {
        String id  = UUID.randomUUID().toString();
        String sql = "INSERT INTO saved_stocks (saved_stock_id,user_id,symbol,start_date,end_date) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, userId);
            ps.setString(3, symbol.toUpperCase());
            ps.setString(4, start.toString());
            ps.setString(5, end.toString());
            ps.executeUpdate();
            // also cache the price data in StockRepository
            stockRepo.storePriceData(data);
            return new SavedStock(id, userId, symbol.toUpperCase(), start, end, data);
        } catch (SQLException e) {
            throw new RuntimeException("save failed: " + e.getMessage(), e);
        }
    }

    // fetchSavedStocks() in Load sequence diagram
    @Override
    public List<SavedStock> findByUserId(String userId) {
        String sql = "SELECT * FROM saved_stocks WHERE user_id=?";
        List<SavedStock> result = new ArrayList<>();
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String    id    = rs.getString("saved_stock_id");
                String    sym   = rs.getString("symbol");
                LocalDate start = LocalDate.parse(rs.getString("start_date"));
                LocalDate end   = LocalDate.parse(rs.getString("end_date"));
                List<PriceData> data = stockRepo.findPriceData(sym, start, end);
                result.add(new SavedStock(id, userId, sym, start, end, data));
            }
        } catch (SQLException e) {
            System.err.println("[SavedStockRepository] findByUserId: " + e.getMessage());
        }
        return result;
    }

    // fetchStockData() in Load sequence diagram
    @Override
    public Optional<SavedStock> findById(String savedStockId) {
        String sql = "SELECT * FROM saved_stocks WHERE saved_stock_id=?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, savedStockId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String    userId = rs.getString("user_id");
                String    sym    = rs.getString("symbol");
                LocalDate start  = LocalDate.parse(rs.getString("start_date"));
                LocalDate end    = LocalDate.parse(rs.getString("end_date"));
                List<PriceData> data = stockRepo.findPriceData(sym, start, end);
                return Optional.of(new SavedStock(savedStockId, userId, sym, start, end, data));
            }
        } catch (SQLException e) {
            System.err.println("[SavedStockRepository] findById: " + e.getMessage());
        }
        return Optional.empty();
    }

    // removeStock() in Delete sequence diagram
    @Override
    public boolean deleteById(String savedStockId) {
        String sql = "DELETE FROM saved_stocks WHERE saved_stock_id=?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, savedStockId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[SavedStockRepository] deleteById: " + e.getMessage());
            return false;
        }
    }
}
