package com.stockcompare.data;

import com.stockcompare.domain.StockData;
import com.stockcompare.domain.DateRange;
import java.util.List;

/**
 * Interface for external API data source
 * Abstracts the external API (Yahoo Finance, etc.)
 * 
 * ○ PROVIDED INTERFACE
 * This interface is PROVIDED by: APIService component
 * This interface is REQUIRED by: StockDataManager
 */
public interface IAPIService {
    
    /**
     * Fetch historical stock data from external API
     * 
     * @param symbol Stock ticker symbol
     * @param dateRange Date range for data
     * @return List of stock data points
     * @throws APIException if API call fails
     */
    List<StockData> fetchHistoricalData(String symbol, DateRange dateRange) 
        throws APIException;
    
    /**
     * Validate if a stock symbol exists
     * 
     * @param symbol Stock ticker symbol
     * @return true if symbol is valid
     */
    boolean validateSymbol(String symbol);
    
    /**
     * Check if API connection is available
     * 
     * @return true if can connect to API
     */
    boolean isConnectionAvailable();
    
    /**
     * Get API rate limit status
     * 
     * @return Number of API calls remaining
     */
    int getRateLimitRemaining();
}

/**
 * Exception for API-related errors
 */
class APIException extends Exception {
    
    public APIException(String message) {
        super(message);
    }
    
    public APIException(String message, Throwable cause) {
        super(message, cause);
    }
}
