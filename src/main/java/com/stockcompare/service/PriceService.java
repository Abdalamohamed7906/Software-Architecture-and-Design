package com.stockcompare.service;

import com.stockcompare.data.StockAPIClient;
import com.stockcompare.domain.interfaces.IStockService;
import com.stockcompare.domain.model.*;
import com.stockcompare.repository.IStockRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * PriceService — implements IStockService.
 *
 * Matches Retrieve Share Price Data sequence diagram:
 *   UI → getPriceData() → PriceService
 *   PriceService → fetchData()      → StockAPI (Yahoo Finance)
 *   StockAPI → data → PriceService
 *   PriceService → storeTempData()  → TempStorage (SQLiteStockRepository)
 *   PriceService → returnData()     → UI
 *
 * Matches Search Share Symbol sequence diagram:
 *   UI → searchShare(query) → SearchService[PriceService]
 *   PriceService → fetchSymbols(query) → StockAPI
 *   StockAPI → symbols → PriceService → results → UI
 *
 * Matches Select Date Range sequence diagram:
 *   UI → validateDates() → DateService[PriceService] → valid/invalid
 *
 * Matches Update Stored Stock Data sequence diagram:
 *   UpdateService → refreshStock() → PriceService
 *   PriceService → fetchLatestData() → StockAPI → replaceData() → StockRepository
 *
 * SOLID — Single Responsibility: stock data retrieval only.
 * SOLID — Open/Closed: swap StockAPIClient without touching this service.
 * SOLID — Dependency Inversion: depends on interfaces, not concretions.
 */
public class PriceService implements IStockService {

    private final StockAPIClient   stockAPIClient;  // StockAPI in diagrams
    private final IStockRepository stockRepository; // TempStorage in diagrams

    public PriceService(StockAPIClient stockAPIClient,
                        IStockRepository stockRepository) {
        this.stockAPIClient  = stockAPIClient;
        this.stockRepository = stockRepository;
    }

    // searchShare(query) → fetchSymbols(query) → StockAPI
    @Override
    public List<ShareDetail> searchShareSymbol(String query) {
        if (query == null || query.isBlank())
            throw new IllegalArgumentException("Search query cannot be empty.");
        try {
            List<ShareDetail> results = stockAPIClient.fetchSymbols(query.trim());
            System.out.println("[PriceService] Found " + results.size() + " symbols for: " + query);
            return results;
        } catch (Exception e) {
            System.err.println("[PriceService] Search failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // getPriceData() → fetchData() → StockAPI → storeTempData() → TempStorage
    @Override
    public List<PriceData> getPriceData(String symbol, LocalDate start, LocalDate end) {
        if (!validateDateRange(start, end))
            throw new IllegalArgumentException("Invalid date range. Max 2 years, start must be before end.");

        String sym = symbol.toUpperCase();

        // Check TempStorage (SQLite cache) first
        if (stockRepository.hasCachedData(sym, start, end)) {
            System.out.println("[PriceService] Cache hit for " + sym);
            return stockRepository.findPriceData(sym, start, end);
        }

        // fetchData() → StockAPI
        try {
            System.out.println("[PriceService] Fetching " + sym + " from Yahoo Finance...");
            List<PriceData> data = stockAPIClient.fetchData(sym, start, end);
            // storeTempData() → TempStorage
            stockRepository.storePriceData(data);
            System.out.println("[PriceService] Cached " + data.size() + " records for " + sym);
            return data;
        } catch (Exception e) {
            System.err.println("[PriceService] API failed, loading from cache: " + e.getMessage());
            return stockRepository.findPriceData(sym, start, end);
        }
    }

    // validateDates() in Select Date Range diagram
    @Override
    public boolean validateDateRange(LocalDate start, LocalDate end) {
        if (start == null || end == null) return false;
        if (start.isAfter(end)) return false;
        if (start.isBefore(end.minusYears(2))) return false;
        return true;
    }

    // fetchLatestData() → replaceData() in Update Stock Data diagram
    @Override
    public boolean refreshStock(String symbol) {
        String sym = symbol.toUpperCase();
        try {
            System.out.println("[PriceService] Refreshing " + sym + "...");
            stockRepository.deletePriceData(sym);
            List<PriceData> fresh = stockAPIClient.fetchLatestData(sym);
            boolean ok = stockRepository.storePriceData(fresh);
            System.out.println("[PriceService] Refresh " + (ok ? "success" : "failed") + " for " + sym);
            return ok;
        } catch (Exception e) {
            System.err.println("[PriceService] refreshStock failed: " + e.getMessage());
            return false;
        }
    }
}
