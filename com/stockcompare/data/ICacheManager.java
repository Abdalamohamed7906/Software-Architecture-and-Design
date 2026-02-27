package com.stockcompare.data;

import com.stockcompare.domain.StockData;
import java.time.Duration;
import java.util.List;

/**
 * INTERFACE: ICacheManager
 * PROVIDED BY: CacheManager component
 * REQUIRED BY: StockDataManager
 */
public interface ICacheManager {
    
    void put(String key, List<StockData> data, Duration ttl);
    
    List<StockData> get(String key);
    
    void invalidate(String key);
    
    void clear();
    
    boolean isExpired(String key);
    
    String generateKey(String symbol, String startDate, String endDate);
}
