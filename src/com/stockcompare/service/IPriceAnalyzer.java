package com.stockcompare.service;

import com.stockcompare.domain.StockData;
import java.util.List;

/**
 * INTERFACE: IPriceAnalyzer
 * PROVIDED BY: PriceAnalyzer component
 * REQUIRED BY: StockDataManager, ComparisonService
 */
public interface IPriceAnalyzer {
    
    double calculatePriceChange(List<StockData> data);
    
    double[] findHighLow(List<StockData> data);
    
    double calculateAveragePrice(List<StockData> data);
    
    List<StockData> normalizeData(List<StockData> data);
    
    String formatForDisplay(List<StockData> data);
}
