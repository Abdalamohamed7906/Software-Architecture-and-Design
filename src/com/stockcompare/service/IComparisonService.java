package com.stockcompare.service;

import com.stockcompare.domain.DateRange;
import com.stockcompare.domain.StockData;
import java.util.List;

/**
 * INTERFACE: IComparisonService
 * PROVIDED BY: ComparisonService component
 * REQUIRED BY: MainUIController (potentially)
 */
public interface IComparisonService {
    
    ComparisonResult compareStocks(String symbol1, String symbol2, DateRange dateRange) 
        throws StockDataException;
    
    ComparisonResult alignDataRanges(List<StockData> data1, List<StockData> data2);
    
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
    
    public List<StockData> getStock1Data() { return stock1Data; }
    public List<StockData> getStock2Data() { return stock2Data; }
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
    
    public double getStock1Return() { return stock1Return; }
    public double getStock2Return() { return stock2Return; }
    public double getRelativeDifference() { return relativeDifference; }
}
