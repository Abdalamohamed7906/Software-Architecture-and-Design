package com.stockcompare.presentation;

import com.stockcompare.service.IStockDataManager;
import com.stockcompare.domain.StockData;
import com.stockcompare.domain.DateRange;
import java.util.List;

/**
 * Main UI Controller Component
 * Orchestrates user interactions and coordinates between UI components
 * 
 * COMPONENT: MainUIController
 * 
 * ○ PROVIDED INTERFACES: None (top-level coordinator)
 * 
 * ◐ REQUIRED INTERFACES:
 *    - IStockDataManager (for stock data operations)
 *    - IChartDisplay (for displaying charts)
 *    - IInputHandler (for user input)
 */
public class MainUIController {
    
    // ◐ REQUIRED: IStockDataManager interface
    private final IStockDataManager stockDataManager;
    
    // ◐ REQUIRED: IChartDisplay interface
    private final IChartDisplay chartDisplay;
    
    // ◐ REQUIRED: IInputHandler interface
    private final IInputHandler inputHandler;
    
    /**
     * Constructor with dependency injection
     * All REQUIRED interfaces are injected
     * 
     * @param stockDataManager Component that PROVIDES IStockDataManager
     * @param chartDisplay Component that PROVIDES IChartDisplay
     * @param inputHandler Component that PROVIDES IInputHandler
     */
    public MainUIController(IStockDataManager stockDataManager,
                           IChartDisplay chartDisplay,
                           IInputHandler inputHandler) {
        this.stockDataManager = stockDataManager;
        this.chartDisplay = chartDisplay;
        this.inputHandler = inputHandler;
    }
    
    /**
     * Initialize the user interface
     */
    public void initialize() {
        System.out.println("MainUIController initialized");
        System.out.println("Ready to accept user input");
    }
    
    /**
     * Handle user request to fetch and display stock data
     */
    public void handleFetchStockData() {
        try {
            // Get input from InputHandler (REQUIRED interface)
            String symbol = inputHandler.getStockSymbol();
            DateRange dateRange = inputHandler.getDateRange();
            
            // Validate input
            if (!inputHandler.validateInput()) {
                displayError("Invalid input. Please check your entries.");
                return;
            }
            
            // Fetch data using StockDataManager (REQUIRED interface)
            List<StockData> data = stockDataManager.fetchStockData(symbol, dateRange);
            
            // Display data using ChartDisplay (REQUIRED interface)
            chartDisplay.renderChart(data);
            
        } catch (Exception e) {
            displayError("Error fetching stock data: " + e.getMessage());
        }
    }
    
    /**
     * Handle user request to compare two stocks
     */
    public void handleCompareStocks() {
        try {
            // Get input for two stocks
            String symbol1 = inputHandler.getStockSymbol();
            // In real implementation, would get second symbol
            String symbol2 = "GOOGL"; // Example
            DateRange dateRange = inputHandler.getDateRange();
            
            // Fetch data for both stocks
            List<StockData> data1 = stockDataManager.fetchStockData(symbol1, dateRange);
            List<StockData> data2 = stockDataManager.fetchStockData(symbol2, dateRange);
            
            // Display comparison
            chartDisplay.renderComparisonChart(data1, data2, symbol1, symbol2);
            
        } catch (Exception e) {
            displayError("Error comparing stocks: " + e.getMessage());
        }
    }
    
    /**
     * Display error message to user
     * 
     * @param message Error message
     */
    public void displayError(String message) {
        System.err.println("ERROR: " + message);
    }
    
    /**
     * Update the view with new data
     * 
     * @param data Stock data to display
     */
    public void updateView(List<StockData> data) {
        if (data == null || data.isEmpty()) {
            displayError("No data to display");
            return;
        }
        
        chartDisplay.updateChart(data);
    }
}
