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
