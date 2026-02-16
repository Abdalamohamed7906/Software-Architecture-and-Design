package com.stockcompare.service;

import com.stockcompare.data.IAPIService;
import com.stockcompare.data.ICacheManager;
import com.stockcompare.data.IRepository;
import com.stockcompare.domain.DateRange;
import com.stockcompare.domain.StockData;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Abstract implementation of stock data management
 * Provides template for concrete implementations
 * Demonstrates Simple Architecture principles
 * 
 * COMPONENT: StockDataManager
 * 
 * ○ PROVIDED INTERFACE: IStockDataManager
 *    - Provides stock data management services to other components
 * 
 * ◐ REQUIRED INTERFACES:
 *    - IRepository (for local data persistence)
 *    - IAPIService (for external API calls)
 *    - ICacheManager (for performance caching)
 */
public abstract class AbstractStockDataManager implements IStockDataManager {
    
    // ◐ REQUIRED: IRepository interface
    protected IRepository repository;
    
    // ◐ REQUIRED: IAPIService interface
    protected IAPIService apiService;
    
    // ◐ REQUIRED: ICacheManager interface
    protected ICacheManager cacheManager;
    
    // Stock symbol validation pattern (e.g., AAPL, GOOGL)
    private static final Pattern SYMBOL_PATTERN = Pattern.compile("^[A-Z]{1,5}$");
    
    /**
     * Constructor with dependency injection
     * 
     * @param repository Data repository
     * @param apiService External API service
     * @param cacheManager Cache manager
     */
    public AbstractStockDataManager(IRepository repository, 
                                     IAPIService apiService,
                                     ICacheManager cacheManager) {
        this.repository = repository;
        this.apiService = apiService;
        this.cacheManager = cacheManager;
    }
    
    @Override
    public List<StockData> fetchStockData(String symbol, DateRange dateRange) 
            throws StockDataException {
        
        // Validate symbol first
        if (!validateSymbol(symbol)) {
            throw new StockDataException("Invalid stock symbol: " + symbol);
        }
        
        // Template method pattern - delegate to concrete implementations
        return performFetch(symbol, dateRange);
    }
    
    /**
     * Abstract method for concrete implementation of fetch logic
     * Each implementation can define its own caching/fetching strategy
     * 
     * @param symbol Stock symbol
     * @param dateRange Date range
     * @return Stock data list
     * @throws StockDataException if fetch fails
     */
    protected abstract List<StockData> performFetch(String symbol, DateRange dateRange) 
            throws StockDataException;
    
    @Override
    public List<StockData> getStoredData(String symbol, DateRange dateRange) {
        // Only retrieve from local storage, no API calls
        return repository.retrieve(symbol, dateRange);
    }
    
    @Override
    public boolean isDataAvailable(String symbol, DateRange dateRange) {
        List<StockData> storedData = repository.retrieve(symbol, dateRange);
        
        // Check if we have complete data for the date range
        // This is a simplified check - you may want to verify all dates are present
        return storedData != null && !storedData.isEmpty();
    }
    
    @Override
    public boolean validateSymbol(String symbol) {
        if (symbol == null || symbol.trim().isEmpty()) {
            return false;
        }
        
        // Check if symbol matches expected pattern
        return SYMBOL_PATTERN.matcher(symbol.toUpperCase()).matches();
    }
    
    @Override
    public void clearCache(String symbol) {
        // Clear all cache entries for this symbol
        // Implementation depends on cache key structure
        // This is a placeholder - concrete implementation would be more specific
    }
    
    /**
     * Helper method to generate cache key
     * 
     * @param symbol Stock symbol
     * @param dateRange Date range
     * @return Cache key string
     */
    protected String generateCacheKey(String symbol, DateRange dateRange) {
        return cacheManager.generateKey(
            symbol,
            dateRange.getStartDate().toString(),
            dateRange.getEndDate().toString()
        );
    }
}
