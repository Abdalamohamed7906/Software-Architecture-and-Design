package com.stockcompare.data;

import com.stockcompare.domain.StockData;
import com.stockcompare.domain.DateRange;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface for local data persistence
 * Abstracts the underlying storage mechanism (SQLite or JSON)
 * 
 * ○ PROVIDED INTERFACE
 * This interface is PROVIDED by: LocalRepository component
 * This interface is REQUIRED by: StockDataManager
 */
public interface IRepository {
    
    /**
     * Save stock data to persistent storage
     * 
     * @param stockData Stock data to save
     * @return true if save successful
     */
    boolean save(StockData stockData);
    
    /**
     * Save multiple stock data points
     * 
     * @param stockDataList List of stock data to save
     * @return Number of records saved
     */
    int saveAll(List<StockData> stockDataList);
    
    /**
     * Retrieve stock data for a symbol and date range
     * 
     * @param symbol Stock ticker symbol
     * @param dateRange Date range to retrieve
     * @return List of stock data points
     */
    List<StockData> retrieve(String symbol, DateRange dateRange);
    
    /**
     * Check if data exists for a specific symbol and date
     * 
     * @param symbol Stock ticker symbol
     * @param date Specific date
     * @return true if data exists
     */
    boolean exists(String symbol, LocalDate date);
    
    /**
     * Delete all data for a specific symbol
     * 
     * @param symbol Stock ticker symbol
     * @return true if deletion successful
     */
    boolean delete(String symbol);
    
    /**
     * Get all stored symbols
     * 
     * @return List of unique stock symbols in storage
     */
    List<String> getAllSymbols();
    
    /**
     * Clear all data from storage
     */
    void clearAll();
}
