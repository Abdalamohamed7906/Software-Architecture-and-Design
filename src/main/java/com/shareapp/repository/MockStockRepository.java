package com.shareapp.repository;

import com.shareapp.model.StockPrice;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Mock implementation of IStockRepository for Sprint 1
 * 
 * Uses in-memory storage (HashMap) to demonstrate the Repository Pattern
 * without requiring actual database setup.
 * 
 * Sprint 1 Purpose:
 * - Demonstrates repository pattern
 * - Enables testing of service layer
 * - Provides working system for code review
 * 
 * Future Sprints: Replace with actual SQLiteRepository or JSONRepository
 */
public class MockStockRepository implements IStockRepository {
    
    // In-memory storage: Map<Symbol, Map<Date, StockPrice>>
    private final Map<String, Map<LocalDate, StockPrice>> storage = new HashMap<>();
    
    public MockStockRepository() {
        System.out.println("[Repository] MockStockRepository initialized");
    }
    
    @Override
    public void save(List<StockPrice> prices) {
        if (prices == null || prices.isEmpty()) {
            return;
        }
        
        System.out.println(String.format("[Repository] Saving %d records", prices.size()));
        
        for (StockPrice price : prices) {
            save(price);
        }
    }
    
    @Override
    public void save(StockPrice price) {
        if (price == null) {
            throw new IllegalArgumentException("StockPrice cannot be null");
        }
        
        String symbol = price.getSymbol().toUpperCase();
        
        // Get or create map for this symbol
        storage.putIfAbsent(symbol, new HashMap<>());
        
        // Store the price (overwrites if exists)
        storage.get(symbol).put(price.getDate(), price);
        
        System.out.println(String.format("[Repository] Saved %s for %s", symbol, price.getDate()));
    }
    
    @Override
    public List<StockPrice> findBySymbolAndDateRange(String symbol, LocalDate startDate, LocalDate endDate) {
        if (symbol == null || startDate == null || endDate == null) {
            return new ArrayList<>();
        }
        
        symbol = symbol.toUpperCase();
        
        Map<LocalDate, StockPrice> symbolData = storage.get(symbol);
        
        if (symbolData == null || symbolData.isEmpty()) {
            System.out.println(String.format("[Repository] No data found for %s", symbol));
            return new ArrayList<>();
        }
        
        // Filter by date range and sort
        List<StockPrice> result = symbolData.values().stream()
            .filter(sp -> !sp.getDate().isBefore(startDate) && !sp.getDate().isAfter(endDate))
            .sorted(Comparator.comparing(StockPrice::getDate))
            .collect(Collectors.toList());
        
        System.out.println(String.format("[Repository] Found %d records for %s between %s and %s",
                                        result.size(), symbol, startDate, endDate));
        
        return result;
    }
    
    @Override
    public List<StockPrice> findBySymbol(String symbol) {
        if (symbol == null) {
            return new ArrayList<>();
        }
        
        symbol = symbol.toUpperCase();
        Map<LocalDate, StockPrice> symbolData = storage.get(symbol);
        
        if (symbolData == null) {
            return new ArrayList<>();
        }
        
        return symbolData.values().stream()
            .sorted(Comparator.comparing(StockPrice::getDate))
            .collect(Collectors.toList());
    }
    
    @Override
    public boolean exists(String symbol, LocalDate date) {
        if (symbol == null || date == null) {
            return false;
        }
        
        symbol = symbol.toUpperCase();
        Map<LocalDate, StockPrice> symbolData = storage.get(symbol);
        
        return symbolData != null && symbolData.containsKey(date);
    }
    
    @Override
    public void update(StockPrice price) {
        if (price == null) {
            throw new IllegalArgumentException("StockPrice cannot be null");
        }
        
        if (!exists(price.getSymbol(), price.getDate())) {
            throw new RuntimeException(
                String.format("Cannot update - record doesn't exist: %s on %s",
                            price.getSymbol(), price.getDate()));
        }
        
        // Update is same as save for this implementation
        save(price);
        System.out.println(String.format("[Repository] Updated %s for %s", 
                                        price.getSymbol(), price.getDate()));
    }
    
    @Override
    public int deleteBySymbol(String symbol) {
        if (symbol == null) {
            return 0;
        }
        
        symbol = symbol.toUpperCase();
        Map<LocalDate, StockPrice> symbolData = storage.remove(symbol);
        
        int count = (symbolData != null) ? symbolData.size() : 0;
        
        if (count > 0) {
            System.out.println(String.format("[Repository] Deleted %d records for %s", count, symbol));
        }
        
        return count;
    }
    
    @Override
    public int deleteBeforeDate(LocalDate beforeDate) {
        if (beforeDate == null) {
            return 0;
        }
        
        int totalDeleted = 0;
        
        for (Map<LocalDate, StockPrice> symbolData : storage.values()) {
            List<LocalDate> datesToDelete = symbolData.keySet().stream()
                .filter(date -> date.isBefore(beforeDate))
                .collect(Collectors.toList());
            
            datesToDelete.forEach(symbolData::remove);
            totalDeleted += datesToDelete.size();
        }
        
        System.out.println(String.format("[Repository] Deleted %d records before %s", 
                                        totalDeleted, beforeDate));
        
        return totalDeleted;
    }
    
    @Override
    public long count() {
        return storage.values().stream()
            .mapToLong(Map::size)
            .sum();
    }
    
    @Override
    public List<String> getAllSymbols() {
        return new ArrayList<>(storage.keySet());
    }
}
