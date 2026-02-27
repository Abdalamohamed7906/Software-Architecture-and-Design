package com.stockcompare.data;

import com.stockcompare.domain.StockData;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * COMPONENT: CacheManager
 * PROVIDED INTERFACE: ICacheManager
 * REQUIRED INTERFACES: None
 */
public class CacheManager implements ICacheManager {
    
    private static class CacheEntry {
        List<StockData> data;
        Instant expiryTime;
        
        CacheEntry(List<StockData> data, Instant expiryTime) {
            this.data = data;
            this.expiryTime = expiryTime;
        }
    }
    
    private final Map<String, CacheEntry> cache = new HashMap<>();
    
    @Override
    public void put(String key, List<StockData> data, Duration ttl) {
        Instant expiry = Instant.now().plus(ttl);
        cache.put(key, new CacheEntry(data, expiry));
        System.out.println("    [Cache] Stored: " + key);
    }
    
    @Override
    public List<StockData> get(String key) {
        CacheEntry entry = cache.get(key);
        
        if (entry == null) {
            return null;
        }
        
        if (Instant.now().isAfter(entry.expiryTime)) {
            cache.remove(key);
            return null;
        }
        
        return entry.data;
    }
    
    @Override
    public void invalidate(String key) {
        cache.remove(key);
    }
    
    @Override
    public void clear() {
        cache.clear();
        System.out.println("    [Cache] Cleared");
    }
    
    @Override
    public boolean isExpired(String key) {
        CacheEntry entry = cache.get(key);
        return entry == null || Instant.now().isAfter(entry.expiryTime);
    }
    
    @Override
    public String generateKey(String symbol, String startDate, String endDate) {
        return String.format("%s:%s:%s", symbol, startDate, endDate);
    }
}
