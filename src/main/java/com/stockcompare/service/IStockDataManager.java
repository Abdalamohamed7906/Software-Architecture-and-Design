package com.stockcompare.service;

import com.stockcompare.domain.DateRange;
import com.stockcompare.domain.StockData;
import java.util.List;

/**
 * Core interface for managing stock data operations
 * This is the main business logic interface
 * 
 * ○ PROVIDED INTERFACE
 * This interface is PROVIDED by: StockDataManager component
 * This interface is REQUIRED by: MainUIController, ComparisonService
 */
public interface IStockDataManager {
    
    /**
     * Fetch stock data for a given symbol and date range
     * Will try local storage first, then external API if needed
     * 
     * @param symbol Stock ticker symbol (e.g., "AAPL")
     * @param dateRange Date range for data query
     * @return List of stock data points
     * @throws StockDataException if data cannot be retrieved
     */
    List<StockData> fetchStockData(String symbol, DateRange dateRange) 
        throws StockDataException;
    
    /**
     * Get stored stock data from local repository only
     * Does not fetch from external API
     * 
     * @param symbol Stock ticker symbol
     * @param dateRange Date range for query
     * @return List of stock data points, may be empty
     */
    List<StockData> getStoredData(String symbol, DateRange dateRange);
    
    /**
     * Check if data is available locally for given parameters
     * 
     * @param symbol Stock ticker symbol
     * @param dateRange Date range to check
     * @return true if complete data exists locally
     */
    boolean isDataAvailable(String symbol, DateRange dateRange);
    
    /**
     * Validate if a stock symbol is valid
     * 
     * @param symbol Stock ticker symbol to validate
     * @return true if symbol is valid format
     */
    boolean validateSymbol(String symbol);
    
    /**
     * Clear cached data for a specific symbol
     * 
     * @param symbol Stock ticker symbol
     */
    void clearCache(String symbol);
}
