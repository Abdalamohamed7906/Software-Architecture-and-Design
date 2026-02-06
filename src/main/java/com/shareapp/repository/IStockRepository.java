package com.shareapp.repository;

import com.shareapp.model.StockPrice;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository Layer Interface - Stock Price Repository
 * 
 * This interface defines the contract for data persistence operations.
 * It follows the Repository Pattern to abstract the data access logic.
 * 
 * Implementations:
 * - SQLiteRepository: Stores data in SQLite database
 * - JSONRepository: Stores data in JSON files
 * 
 * Benefits:
 * - Decouples business logic from data access
 * - Enables switching between persistence mechanisms
 * - Facilitates testing with mock repositories
 * 
 * Sprint 1: Abstract interface defining repository contract
 */
public interface IStockRepository {
    
    /**
     * Saves a collection of stock prices to persistent storage.
     * 
     * Behavior:
     * - If a record already exists (same symbol and date), it should be updated
     * - If a record doesn't exist, it should be inserted
     * - Operations should be atomic (all or nothing)
     * 
     * @param prices List of StockPrice objects to save
     * @throws RepositoryException if save operation fails
     */
    void save(List<StockPrice> prices);
    
    /**
     * Saves a single stock price record.
     * 
     * @param price StockPrice object to save
     * @throws RepositoryException if save operation fails
     */
    void save(StockPrice price);
    
    /**
     * Retrieves stock prices for a specific symbol within a date range.
     * 
     * @param symbol Stock ticker symbol (case-insensitive)
     * @param startDate Beginning of date range (inclusive)
     * @param endDate End of date range (inclusive)
     * @return List of StockPrice objects, sorted by date ascending
     *         Returns empty list if no data found
     */
    List<StockPrice> findBySymbolAndDateRange(String symbol, LocalDate startDate, LocalDate endDate);
    
    /**
     * Retrieves all stock prices for a specific symbol.
     * 
     * @param symbol Stock ticker symbol
     * @return List of all StockPrice objects for the symbol
     */
    List<StockPrice> findBySymbol(String symbol);
    
    /**
     * Checks if data exists for a specific symbol and date.
     * 
     * @param symbol Stock ticker symbol
     * @param date Specific date to check
     * @return true if data exists, false otherwise
     */
    boolean exists(String symbol, LocalDate date);
    
    /**
     * Updates an existing stock price record.
     * 
     * @param price StockPrice with updated values
     * @throws RepositoryException if record doesn't exist or update fails
     */
    void update(StockPrice price);
    
    /**
     * Deletes all data for a specific symbol.
     * Useful for clearing cache or removing old data.
     * 
     * @param symbol Stock ticker symbol
     * @return Number of records deleted
     */
    int deleteBySymbol(String symbol);
    
    /**
     * Deletes stock prices older than a specified date.
     * Useful for data cleanup and managing storage.
     * 
     * @param beforeDate Delete records before this date
     * @return Number of records deleted
     */
    int deleteBeforeDate(LocalDate beforeDate);
    
    /**
     * Gets a count of all stored stock price records.
     * 
     * @return Total number of records in repository
     */
    long count();
    
    /**
     * Gets list of all unique symbols currently stored.
     * 
     * @return List of stock symbols
     */
    List<String> getAllSymbols();
}
