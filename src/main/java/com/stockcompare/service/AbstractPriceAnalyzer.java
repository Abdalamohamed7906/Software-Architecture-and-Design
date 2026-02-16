package com.stockcompare.service;

import com.stockcompare.domain.StockData;
import java.util.List;

/**
 * Abstract class for stock price analysis
 * Provides common analysis functionality
 * 
 * COMPONENT: PriceAnalyzer
 * 
 * ○ PROVIDED INTERFACE: IPriceAnalyzer
 *    - Provides price analysis services to other components
 * 
 * ◐ REQUIRED INTERFACES: None
 */
public abstract class AbstractPriceAnalyzer implements IPriceAnalyzer {
    
    /**
     * Calculate price change over the period
     * 
     * @param data Stock data list
     * @return Price change as percentage
     */
    @Override
    public double calculatePriceChange(List<StockData> data) {
        if (data == null || data.size() < 2) {
            return 0.0;
        }
        
        // Get first and last closing prices
        double startPrice = data.get(0).getClose();
        double endPrice = data.get(data.size() - 1).getClose();
        
        // Calculate percentage change
        return ((endPrice - startPrice) / startPrice) * 100;
    }
    
    /**
     * Find highest and lowest prices in dataset
     * 
     * @param data Stock data list
     * @return Array with [high, low]
     */
    @Override
    public double[] findHighLow(List<StockData> data) {
        if (data == null || data.isEmpty()) {
            return new double[]{0.0, 0.0};
        }
        
        double high = Double.MIN_VALUE;
        double low = Double.MAX_VALUE;
        
        for (StockData stock : data) {
            if (stock.getHigh() > high) {
                high = stock.getHigh();
            }
            if (stock.getLow() < low) {
                low = stock.getLow();
            }
        }
        
        return new double[]{high, low};
    }
    
    /**
     * Calculate average closing price
     * 
     * @param data Stock data list
     * @return Average closing price
     */
    @Override
    public double calculateAveragePrice(List<StockData> data) {
        if (data == null || data.isEmpty()) {
            return 0.0;
        }
        
        double sum = 0.0;
        for (StockData stock : data) {
            sum += stock.getClose();
        }
        
        return sum / data.size();
    }
    
    /**
     * Abstract method for normalized data preparation
     * Concrete implementations define normalization strategy
     * 
     * @param data Stock data to normalize
     * @return Normalized data
     */
    public abstract List<StockData> normalizeData(List<StockData> data);
    
    /**
     * Abstract method for display data formatting
     * Concrete implementations define formatting rules
     * 
     * @param data Stock data to format
     * @return Formatted data suitable for display
     */
    public abstract Object formatForDisplay(List<StockData> data);
}
