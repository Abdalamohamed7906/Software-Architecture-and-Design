package com.shareapp.service;

import com.shareapp.model.StockPrice;
import java.time.LocalDate;
import java.util.List;

/**
 * Service Layer Interface - Stock Price Service
 * 
 * This interface defines the contract for stock price operations.
 * It demonstrates the Dependency Inversion Principle - high-level components
 * depend on this abstraction, not on concrete implementations.
 * 
 * Responsibilities:
 * - Orchestrate data retrieval (cache vs. API)
 * - Coordinate between Repository and External Data Source
 * - Implement business logic for data caching strategy
 * 
 * Sprint 1: Abstract interface defining service contract
 */
public interface IStockPriceService {
    
    /**
     * Retrieves stock prices for a given symbol within a date range.
     * 
     * Implementation Strategy:
     * 1. Check if data exists in local repository (cache)
     * 2. If complete data available locally, return it
     * 3. If not, fetch from external data source
     * 4. Save fetched data to local repository
     * 5. Return the data
     * 
     * @param symbol Stock ticker symbol (e.g., "AAPL")
     * @param startDate Beginning of date range
     * @param endDate End of date range
     * @return List of StockPrice objects, sorted by date
     * @throws IllegalArgumentException if inputs are invalid
     * @throws ServiceException if data retrieval fails
     */
    List<StockPrice> getStockPrices(String symbol, LocalDate startDate, LocalDate endDate);
    
    /**
     * Forces a refresh of data from external source, bypassing cache.
     * Useful when user wants the most recent data.
     * 
     * @param symbol Stock ticker symbol
     * @throws ServiceException if refresh fails
     */
    void refreshData(String symbol, LocalDate startDate, LocalDate endDate);
    
    /**
     * Checks if complete data is available locally for the given range.
     * Allows UI to determine whether to show "offline" indicator.
     * 
     * @param symbol Stock ticker symbol
     * @param startDate Beginning of date range
     * @param endDate End of date range
     * @return true if all data is cached locally, false otherwise
     */
    boolean isDataAvailableLocally(String symbol, LocalDate startDate, LocalDate endDate);
    
    /**
     * Validates if a stock symbol is valid before attempting to fetch data.
     * 
     * @param symbol Stock ticker symbol
     * @return true if symbol is valid, false otherwise
     */
    boolean validateSymbol(String symbol);
}
