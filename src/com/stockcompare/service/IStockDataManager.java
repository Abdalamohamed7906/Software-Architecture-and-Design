package com.stockcompare.service;

import com.stockcompare.domain.DateRange;
import com.stockcompare.domain.StockData;
import java.util.List;

/**
 * INTERFACE: IStockDataManager
 * PROVIDED BY: StockDataManager component
 * REQUIRED BY: MainUIController, ComparisonService
 */
public interface IStockDataManager {
    
    List<StockData> fetchStockData(String symbol, DateRange dateRange) 
        throws StockDataException;
    
    List<StockData> getStoredData(String symbol, DateRange dateRange);
    
    boolean isDataAvailable(String symbol, DateRange dateRange);
    
    boolean validateSymbol(String symbol);
    
    void clearCache(String symbol);
}
