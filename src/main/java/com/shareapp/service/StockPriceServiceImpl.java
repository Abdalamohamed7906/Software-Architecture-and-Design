package com.shareapp.service;

import com.shareapp.model.StockPrice;
import com.shareapp.model.DateRange;
import com.shareapp.repository.IStockRepository;
import com.shareapp.repository.IExternalDataSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete implementation of IStockPriceService
 * 
 * Sprint 1: Mock implementation with dummy data to demonstrate architecture
 * 
 * Demonstrates:
 * - Dependency Injection (constructor injection)
 * - Service Layer orchestration
 * - Coordination between repository and external source
 * 
 * Future Sprints: Will implement actual caching logic and error handling
 */
public class StockPriceServiceImpl implements IStockPriceService {
    
    private final IStockRepository repository;
    private final IExternalDataSource externalDataSource;
    
    /**
     * Constructor Injection - Dependencies provided externally
     * Enables testing with mock objects and runtime configuration
     * 
     * @param repository Data persistence layer
     * @param externalDataSource External API client
     */
    public StockPriceServiceImpl(IStockRepository repository, IExternalDataSource externalDataSource) {
        if (repository == null || externalDataSource == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.repository = repository;
        this.externalDataSource = externalDataSource;
    }
    
    @Override
    public List<StockPrice> getStockPrices(String symbol, LocalDate startDate, LocalDate endDate) {
        // Validate inputs
        if (symbol == null || symbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Symbol cannot be null or empty");
        }
        
        // Validate date range using DateRange value object
        DateRange dateRange = new DateRange(startDate, endDate);
        
        // Normalize symbol to uppercase
        symbol = symbol.toUpperCase();
        
        System.out.println(String.format("[Service] Fetching data for %s from %s to %s", 
                                        symbol, startDate, endDate));
        
        // Strategy: Try local repository first
        List<StockPrice> cachedData = repository.findBySymbolAndDateRange(symbol, startDate, endDate);
        
        if (isDataComplete(cachedData, dateRange)) {
            System.out.println("[Service] Complete data found in cache");
            return cachedData;
        }
        
        // Data not complete or not cached - fetch from external source
        System.out.println("[Service] Fetching from external data source");
        try {
            List<StockPrice> fetchedData = externalDataSource.fetchStockPrices(symbol, startDate, endDate);
            
            // Save to repository for future use
            repository.save(fetchedData);
            
            return fetchedData;
        } catch (Exception e) {
            System.err.println("[Service] Error fetching data: " + e.getMessage());
            
            // Fallback: return cached data if available, even if incomplete
            if (!cachedData.isEmpty()) {
                System.out.println("[Service] Returning partial cached data as fallback");
                return cachedData;
            }
            
            throw new RuntimeException("Failed to retrieve stock data", e);
        }
    }
    
    @Override
    public void refreshData(String symbol, LocalDate startDate, LocalDate endDate) {
        System.out.println(String.format("[Service] Forcing refresh for %s", symbol));
        
        symbol = symbol.toUpperCase();
        
        // Fetch fresh data from external source
        List<StockPrice> freshData = externalDataSource.fetchStockPrices(symbol, startDate, endDate);
        
        // Update repository
        repository.save(freshData);
        
        System.out.println("[Service] Data refreshed successfully");
    }
    
    @Override
    public boolean isDataAvailableLocally(String symbol, LocalDate startDate, LocalDate endDate) {
        symbol = symbol.toUpperCase();
        List<StockPrice> cachedData = repository.findBySymbolAndDateRange(symbol, startDate, endDate);
        DateRange dateRange = new DateRange(startDate, endDate);
        
        return isDataComplete(cachedData, dateRange);
    }
    
    @Override
    public boolean validateSymbol(String symbol) {
        if (symbol == null || symbol.trim().isEmpty()) {
            return false;
        }
        
        // Basic validation - length and format
        symbol = symbol.trim().toUpperCase();
        
        // Stock symbols are typically 1-5 characters, alphanumeric
        if (symbol.length() > 5 || !symbol.matches("^[A-Z]+$")) {
            return false;
        }
        
        // Could delegate to external data source for actual validation
        return externalDataSource.validateSymbol(symbol);
    }
    
    /**
     * Helper method to check if cached data is complete for the requested range
     * 
     * @param cachedData Data from repository
     * @param dateRange Requested date range
     * @return true if data covers all business days in range
     */
    private boolean isDataComplete(List<StockPrice> cachedData, DateRange dateRange) {
        if (cachedData == null || cachedData.isEmpty()) {
            return false;
        }
        
        // Sprint 1: Simplified check - just verify we have some data
        // Future: Check for all business days (excluding weekends/holidays)
        
        LocalDate earliestDate = cachedData.stream()
            .map(StockPrice::getDate)
            .min(LocalDate::compareTo)
            .orElse(null);
            
        LocalDate latestDate = cachedData.stream()
            .map(StockPrice::getDate)
            .max(LocalDate::compareTo)
            .orElse(null);
        
        if (earliestDate == null || latestDate == null) {
            return false;
        }
        
        // Check if cached data covers the requested range
        return !earliestDate.isAfter(dateRange.getStartDate()) && 
               !latestDate.isBefore(dateRange.getEndDate());
    }
}
