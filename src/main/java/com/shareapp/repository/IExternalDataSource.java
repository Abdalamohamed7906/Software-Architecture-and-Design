package com.shareapp.repository;

import com.shareapp.model.StockPrice;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository Layer Interface - External Data Source
 * 
 * This interface defines the contract for fetching stock data from external APIs.
 * It follows the Adapter Pattern to abstract the external API integration.
 * 
 * Implementations:
 * - YahooFinanceAPIClient: Fetches from Yahoo Finance
 * - AlphaVantageClient: Alternative data source
 * - MockExternalDataSource: For testing and Sprint 1 demo
 * 
 * Benefits:
 * - Decouples service logic from specific API
 * - Enables switching between data providers
 * - Facilitates testing without API calls
 * 
 * Sprint 1: Abstract interface defining external data source contract
 */
public interface IExternalDataSource {
    
    /**
     * Fetches stock prices from external API for a specific symbol and date range.
     * 
     * Implementation Notes:
     * - Should handle API rate limiting
     * - Should validate symbol before making API call
     * - Should handle API errors gracefully
     * - Should map API response to StockPrice domain model
     * 
     * @param symbol Stock ticker symbol (e.g., "AAPL", "GOOGL")
     * @param startDate Beginning of date range
     * @param endDate End of date range
     * @return List of StockPrice objects, sorted by date
     * @throws DataSourceException if API call fails
     * @throws IllegalArgumentException if inputs are invalid
     */
    List<StockPrice> fetchStockPrices(String symbol, LocalDate startDate, LocalDate endDate);
    
    /**
     * Validates if a stock symbol exists and is valid.
     * 
     * This prevents unnecessary API calls for invalid symbols.
     * 
     * @param symbol Stock ticker symbol to validate
     * @return true if symbol is valid and exists, false otherwise
     */
    boolean validateSymbol(String symbol);
    
    /**
     * Checks if the data source is currently available (network connectivity).
     * 
     * @return true if API can be reached, false otherwise
     */
    boolean isAvailable();
    
    /**
     * Gets the name/identifier of this data source.
     * Useful for logging and debugging.
     * 
     * @return Name of the data source (e.g., "Yahoo Finance", "Alpha Vantage")
     */
    String getDataSourceName();
}
