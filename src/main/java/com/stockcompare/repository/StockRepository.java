package com.stockcompare.repository;

import com.stockcompare.data.SQLiteDatabase;
import com.stockcompare.domain.model.PriceData;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 * StockRepository — SQLite implementation of IStockRepository.
 * Acts as "TempStorage" in your Retrieve Price Data sequence diagram:
 *   PriceService → storeTempData() → StockRepository (SQLite)
 *   PriceService → findPriceData() → StockRepository (cache hit)
 *
 * Also matches "Database" in the Update Stock Data sequence diagram:
 *   UpdateService → replaceData() → StockRepository
 *
 * SOLID — Single Responsibility: price data caching only.
 */
public class StockRepository implements IStockRepository {

    private final SQLiteDatabase db;

    public StockRepository(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public List<PriceData> findPriceData(String symbol, LocalDate start, LocalDate end) {
        String sql = "SELECT * FROM price_data WHERE symbol=? AND date>=? AND date<=? ORDER BY date ASC";
        List<PriceData> result = new ArrayList<>();
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, symbol.toUpperCase());
            ps.setString(2, start.toString());
            ps.setString(3, end.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new PriceData(
                        rs.getString("symbol"),
                        LocalDate.parse(rs.getString("date")),
                        rs.getDouble("open"),
                        rs.getDouble("high"),
                        rs.getDouble("low"),
                        rs.getDouble("close"),
                        rs.getLong("volume")
                ));
            }
        } catch (SQLException e) {
            System.err.println("[StockRepository] findPriceData: " + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean storePriceData(List<PriceData> data) {
        String sql = "INSERT OR IGNORE INTO price_data (symbol,date,open,high,low,close,volume) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            for (PriceData p : data) {
                ps.setString(1, p.getSymbol());
                ps.setString(2, p.getDate().toString());
                ps.setDouble(3, p.getOpen());
                ps.setDouble(4, p.getHigh());
                ps.setDouble(5, p.getLow());
                ps.setDouble(6, p.getClose());
                ps.setLong(7, p.getVolume());
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        } catch (SQLException e) {
            System.err.println("[StockRepository] storePriceData: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePriceData(String symbol) {
        String sql = "DELETE FROM price_data WHERE symbol=?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, symbol.toUpperCase());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("[StockRepository] deletePriceData: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean hasCachedData(String symbol, LocalDate start, LocalDate end) {
        String sql = "SELECT COUNT(*) FROM price_data WHERE symbol=? AND date>=? AND date<=?";
        try (PreparedStatement ps = db.getConnection().prepareStatement(sql)) {
            ps.setString(1, symbol.toUpperCase());
            ps.setString(2, start.toString());
            ps.setString(3, end.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
