package com.stockcompare.service;

import com.stockcompare.data.IAPIService;
import com.stockcompare.data.ICacheManager;
import com.stockcompare.data.IRepository;
import com.stockcompare.domain.DateRange;
import com.stockcompare.domain.StockData;
import java.time.Duration;
import java.util.List;

/**
 * COMPONENT: StockDataManager
 * PROVIDED INTERFACE: IStockDataManager
 * REQUIRED INTERFACES: IRepository, IAPIService, ICacheManager, IPriceAnalyzer
 */
public class StockDataManager implements IStockDataManager {
    
    private final IRepository repository;
    private final IAPIService apiService;
    private final ICacheManager cacheManager;
    private final IPriceAnalyzer priceAnalyzer;
    
    public StockDataManager(IRepository repository,
                           IAPIService apiService,
                           ICacheManager cacheManager,
                           IPriceAnalyzer priceAnalyzer) {
        this.repository = repository;
        this.apiService = apiService;
        this.cacheManager = cacheManager;
        this.priceAnalyzer = priceAnalyzer;
    }
    
    @Override
    public List<StockData> fetchStockData(String symbol, DateRange dateRange) 
            throws StockDataException {
        
        if (!validateSymbol(symbol)) {
            throw new StockDataException("Invalid stock symbol: " + symbol);
        }
        
        // Step 1: Check cache
        String cacheKey = generateCacheKey(symbol, dateRange);
        List<StockData> cachedData = cacheManager.get(cacheKey);
        
        if (cachedData != null) {
            System.out.println("  ✓ Cache hit for " + symbol);
            return cachedData;
        }
        
        // Step 2: Check local repository
        List<StockData> storedData = repository.retrieve(symbol, dateRange);
        
        if (storedData != null && !storedData.isEmpty()) {
            System.out.println("  ✓ Repository hit for " + symbol);
            cacheManager.put(cacheKey, storedData, Duration.ofMinutes(30));
            return storedData;
        }
        
        // Step 3: Fetch from API
        System.out.println("  ⟳ Fetching from API for " + symbol);
        try {
            List<StockData> apiData = apiService.fetchHistoricalData(symbol, dateRange);
            
            // Save to repository and cache
            repository.saveAll(apiData);
            cacheManager.put(cacheKey, apiData, Duration.ofMinutes(30));
            
            return apiData;
        } catch (Exception e) {
            throw new StockDataException("Failed to fetch data for " + symbol, e);
        }
    }
    
    @Override
    public boolean validateSymbol(String symbol) {
        return symbol != null && symbol.matches("^[A-Z]{1,5}$");
    }
    
    @Override
    public void clearCache(String symbol) {
        cacheManager.clear();
    }
    
    private String generateCacheKey(String symbol, DateRange dateRange) {
        return cacheManager.generateKey(
            symbol,
            dateRange.getStartDate().toString(),
            dateRange.getEndDate().toString()
        );
    }

    @Override
    public List<StockData> getStoredData(String symbol, DateRange dateRange) {
        return repository.retrieve(symbol, dateRange);
    }

    @Override
    public boolean isDataAvailable(String symbol, DateRange dateRange) {
        List<StockData> data = repository.retrieve(symbol, dateRange);
        return data != null && !data.isEmpty();
    }
}
