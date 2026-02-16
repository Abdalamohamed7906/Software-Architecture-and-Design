package com.stockcompare.service;

import com.stockcompare.domain.StockData;
import java.util.List;

/**
 * Interface for stock price analysis
 * 
 * ○ PROVIDED INTERFACE
 * This interface is PROVIDED by: PriceAnalyzer component
 * This interface is REQUIRED by: StockDataManager, ComparisonService
 */
public interface IPriceAnalyzer {
    
    /**
     * Calculate price change over the period
     * 
     * @param data Stock data list
     * @return Price change as percentage
     */
    double calculatePriceChange(List<StockData> data);
    
    /**
     * Find highest and lowest prices in dataset
     * 
     * @param data Stock data list
     * @return Array with [high, low]
     */
    double[] findHighLow(List<StockData> data);
    
    /**
     * Calculate average closing price
     * 
     * @param data Stock data list
     * @return Average closing price
     */
    double calculateAveragePrice(List<StockData> data);
    
    /**
     * Normalize data for comparison
     * 
     * @param data Stock data to normalize
     * @return Normalized data
     */
    List<StockData> normalizeData(List<StockData> data);
    
    /**
     * Format data for display
     * 
     * @param data Stock data to format
     * @return Formatted data suitable for display
     */
    Object formatForDisplay(List<StockData> data);
}
