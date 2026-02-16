package com.stockcompare.presentation;

import com.stockcompare.domain.StockData;
import java.util.List;

/**
 * Interface for chart display component
 * Handles rendering of stock price charts
 * 
 * ○ PROVIDED INTERFACE
 * This interface is PROVIDED by: ChartDisplayComponent
 * This interface is REQUIRED by: MainUIController
 */
public interface IChartDisplay {
    
    /**
     * Render chart for single stock
     * 
     * @param data Stock data to display
     */
    void renderChart(List<StockData> data);
    
    /**
     * Render comparison chart for two stocks
     * 
     * @param data1 First stock data
     * @param data2 Second stock data
     * @param symbol1 First stock symbol for legend
     * @param symbol2 Second stock symbol for legend
     */
    void renderComparisonChart(List<StockData> data1, List<StockData> data2, 
                                String symbol1, String symbol2);
    
    /**
     * Clear the current chart
     */
    void clearChart();
    
    /**
     * Update chart with new data
     * 
     * @param data New stock data
     */
    void updateChart(List<StockData> data);
    
    /**
     * Set chart title
     * 
     * @param title Title text
     */
    void setChartTitle(String title);
    
    /**
     * Configure chart appearance
     * 
     * @param showGrid Show grid lines
     * @param showLegend Show legend
     */
    void configureChart(boolean showGrid, boolean showLegend);
}
