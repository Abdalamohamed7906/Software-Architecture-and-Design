package com.stockcompare.presentation;

import com.stockcompare.domain.StockData;
import java.util.List;

/**
 * INTERFACE: IChartDisplay
 * PROVIDED BY: ChartDisplay component
 * REQUIRED BY: MainUIController
 */
public interface IChartDisplay {
    
    void renderChart(List<StockData> data);
    
    void renderComparisonChart(List<StockData> data1, List<StockData> data2, 
                                String symbol1, String symbol2);
    
    void clearChart();
    
    void updateChart(List<StockData> data);
    
    void setChartTitle(String title);
    
    void configureChart(boolean showGrid, boolean showLegend);
}
