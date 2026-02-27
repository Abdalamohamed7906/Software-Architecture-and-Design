package com.stockcompare.data;

import com.stockcompare.domain.DateRange;
import com.stockcompare.domain.StockData;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * COMPONENT: Repository
 * PROVIDED INTERFACE: IRepository
 * REQUIRED INTERFACES: None
 */
public class Repository implements IRepository {
    
    private final Map<String, List<StockData>> storage = new HashMap<>();
    
    @Override
    public boolean save(StockData stockData) {
        String symbol = stockData.getSymbol();
        storage.putIfAbsent(symbol, new ArrayList<>());
        storage.get(symbol).add(stockData);
        return true;
    }
    
    @Override
    public int saveAll(List<StockData> stockDataList) {
        int count = 0;
        for (StockData data : stockDataList) {
            if (save(data)) {
                count++;
            }
        }
        System.out.println("    [Repository] Saved " + count + " records");
        return count;
    }
    
    @Override
    public List<StockData> retrieve(String symbol, DateRange dateRange) {
        List<StockData> allData = storage.get(symbol);
        
        if (allData == null) {
            return new ArrayList<>();
        }
        
        return allData.stream()
            .filter(data -> dateRange.contains(data.getDate()))
            .sorted(Comparator.comparing(StockData::getDate))
            .collect(Collectors.toList());
    }
    
    @Override
    public boolean exists(String symbol, LocalDate date) {
        List<StockData> data = storage.get(symbol);
        if (data == null) return false;
        return data.stream().anyMatch(stock -> stock.getDate().equals(date));
    }
    
    @Override
    public boolean delete(String symbol) {
        return storage.remove(symbol) != null;
    }
    
    @Override
    public List<String> getAllSymbols() {
        return new ArrayList<>(storage.keySet());
    }
    
    @Override
    public void clearAll() {
        storage.clear();
    }
}
