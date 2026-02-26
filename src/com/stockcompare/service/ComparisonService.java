package com.stockcompare.service;

import com.stockcompare.domain.DateRange;
import com.stockcompare.domain.StockData;
import java.util.List;

/**
 * COMPONENT: ComparisonService
 * PROVIDED INTERFACE: IComparisonService
 * REQUIRED INTERFACES: IStockDataManager, IPriceAnalyzer
 */
public class ComparisonService implements IComparisonService {
    
    private final IStockDataManager stockDataManager;
    private final IPriceAnalyzer priceAnalyzer;
    
    public ComparisonService(IStockDataManager stockDataManager,
                            IPriceAnalyzer priceAnalyzer) {
        this.stockDataManager = stockDataManager;
        this.priceAnalyzer = priceAnalyzer;
    }
    
    @Override
    public ComparisonResult compareStocks(String symbol1, String symbol2, DateRange dateRange) 
            throws StockDataException {
        
        // Fetch data for both stocks
        List<StockData> data1 = stockDataManager.fetchStockData(symbol1, dateRange);
        List<StockData> data2 = stockDataManager.fetchStockData(symbol2, dateRange);
        
        // Align the data ranges
        return alignDataRanges(data1, data2);
    }
    
    @Override
    public ComparisonResult alignDataRanges(List<StockData> data1, List<StockData> data2) {
        // Simple implementation - return both datasets
        return new ComparisonResult(data1, data2);
    }
    
    @Override
    public PerformanceMetrics calculateRelativePerformance(List<StockData> data1, 
                                                           List<StockData> data2) {
        
        // Use IPriceAnalyzer to calculate returns
        double return1 = priceAnalyzer.calculatePriceChange(data1);
        double return2 = priceAnalyzer.calculatePriceChange(data2);
        
        return new PerformanceMetrics(return1, return2);
    }
}
