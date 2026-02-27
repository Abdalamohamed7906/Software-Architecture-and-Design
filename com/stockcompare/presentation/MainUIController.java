package com.stockcompare.presentation;

import com.stockcompare.service.IStockDataManager;
import com.stockcompare.service.StockDataException;
import com.stockcompare.domain.DateRange;
import com.stockcompare.domain.StockData;
import java.util.List;

/**
 * COMPONENT: MainUIController
 * PROVIDED INTERFACES: None (top-level coordinator)
 * REQUIRED INTERFACES: IInputHandler, IChartDisplay, IStockDataManager
 */
public class MainUIController {
    
    private final IStockDataManager stockDataManager;
    private final IChartDisplay chartDisplay;
    private final IInputHandler inputHandler;
    
    public MainUIController(IStockDataManager stockDataManager,
                           IChartDisplay chartDisplay,
                           IInputHandler inputHandler) {
        this.stockDataManager = stockDataManager;
        this.chartDisplay = chartDisplay;
        this.inputHandler = inputHandler;
    }
    
    public void handleFetchStockData() {
        try {
            // Use IInputHandler to get user input
            String symbol = inputHandler.getStockSymbol();
            DateRange dateRange = inputHandler.getDateRange();
            
            // Use IStockDataManager to fetch data
            List<StockData> data = stockDataManager.fetchStockData(symbol, dateRange);
            
            // Use IChartDisplay to show results
            chartDisplay.renderChart(data);
            
            System.out.println("✓ Successfully displayed " + data.size() + " records");
            
        } catch (StockDataException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
    
    public void displayError(String message) {
        System.err.println("ERROR: " + message);
    }
}
