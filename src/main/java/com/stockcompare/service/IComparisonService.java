package com.stockcompare.service;

import com.stockcompare.domain.DateRange;
import com.stockcompare.domain.StockData;
import java.util.List;

/**
 * Interface for comparing multiple stocks
 * 
 * ○ PROVIDED INTERFACE
 * This interface is PROVIDED by: ComparisonService component
 * This interface is REQUIRED by: MainUIController (potentially)
 * 
 * ◐ REQUIRED INTERFACES (by ComparisonService component):
 *    - IStockDataManager (to fetch stock data)
 *    - IPriceAnalyzer (to analyze comparison results)
 */
public interface IComparisonService {
    
    /**
     * Compare two stocks over a date range
     * Returns aligned data for both stocks
     * 
     * @param symbol1 First stock symbol
     * @param symbol2 Second stock symbol
     * @param dateRange Date range for comparison
     * @return Comparison result containing both stock data sets
     * @throws StockDataException if data cannot be retrieved
     */
    ComparisonResult compareStocks(String symbol1, String symbol2, DateRange dateRange) 
        throws StockDataException;
    
    /**
     * Align two datasets to have matching dates
     * Only includes dates present in both datasets
     * 
     * @param data1 First stock data list
     * @param data2 Second stock data list
     * @return Aligned comparison result
     */
    ComparisonResult alignDataRanges(List<StockData> data1, List<StockData> data2);
    
    /**
     * Calculate relative performance between two stocks
     * Returns percentage change comparison
     * 
     * @param data1 First stock data
     * @param data2 Second stock data
     * @return Performance metrics
     */
    PerformanceMetrics calculateRelativePerformance(List<StockData> data1, 
                                                     List<StockData> data2);
}

/**
 * Result of stock comparison containing both datasets
 */
class ComparisonResult {
    private List<StockData> stock1Data;
    private List<StockData> stock2Data;
    
    public ComparisonResult(List<StockData> stock1Data, List<StockData> stock2Data) {
        this.stock1Data = stock1Data;
        this.stock2Data = stock2Data;
    }
    
    public List<StockData> getStock1Data() {
        return stock1Data;
    }
    
    public List<StockData> getStock2Data() {
        return stock2Data;
    }
}

/**
 * Performance metrics for comparison
 */
class PerformanceMetrics {
    private double stock1Return;
    private double stock2Return;
    private double relativeDifference;
    
    public PerformanceMetrics(double stock1Return, double stock2Return) {
        this.stock1Return = stock1Return;
        this.stock2Return = stock2Return;
        this.relativeDifference = stock1Return - stock2Return;
    }
    
    public double getStock1Return() {
        return stock1Return;
    }
    
    public double getStock2Return() {
        return stock2Return;
    }
    
    public double getRelativeDifference() {
        return relativeDifference;
    }
}
