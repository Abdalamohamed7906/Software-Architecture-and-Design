package com.stockcompare.data;

import com.stockcompare.domain.StockData;
import com.stockcompare.domain.DateRange;
import java.time.LocalDate;
import java.util.List;

/**
 * INTERFACE: IRepository
 * PROVIDED BY: Repository component
 * REQUIRED BY: StockDataManager
 */
public interface IRepository {
    
    boolean save(StockData stockData);
    
    int saveAll(List<StockData> stockDataList);
    
    List<StockData> retrieve(String symbol, DateRange dateRange);
    
    boolean exists(String symbol, LocalDate date);
    
    boolean delete(String symbol);
    
    List<String> getAllSymbols();
    
    void clearAll();
}
