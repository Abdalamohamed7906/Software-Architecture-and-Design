package com.stockcompare.data;

import com.stockcompare.domain.StockData;
import java.time.Duration;
import java.util.List;

/**
 * Interface for in-memory caching of stock data
 * Improves performance by reducing database and API calls
 * 
 * ○ PROVIDED INTERFACE
 * This interface is PROVIDED by: CacheManager component
 * This interface is REQUIRED by: StockDataManager
 */
public interface ICacheManager {
    
    /**
     * Store data in cache with time-to-live
     * 
     * @param key Unique cache key
     * @param data Stock data to cache
     * @param ttl Time-to-live duration
     */
    void put(String key, List<StockData> data, Duration ttl);
    
    /**
     * Retrieve data from cache
     * 
     * @param key Cache key
     * @return Cached stock data, or null if not found/expired
     */
    List<StockData> get(String key);
    
    /**
     * Invalidate a specific cache entry
     * 
     * @param key Cache key to invalidate
     */
    void invalidate(String key);
    
    /**
     * Clear all cached data
     */
    void clear();
    
    /**
     * Check if cache entry is expired
     * 
     * @param key Cache key
     * @return true if entry exists and is expired
     */
    boolean isExpired(String key);
    
    /**
     * Generate cache key for stock query
     * 
     * @param symbol Stock symbol
     * @param startDate Start date string
     * @param endDate End date string
     * @return Generated cache key
     */
    String generateKey(String symbol, String startDate, String endDate);
}
