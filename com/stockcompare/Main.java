package com.stockcompare;

import com.stockcompare.domain.StockData;
import com.stockcompare.domain.DateRange;
import com.stockcompare.service.*;
import com.stockcompare.data.*;
import com.stockcompare.presentation.*;
import java.time.LocalDate;
import java.util.List;

public class  Main {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║  Stock-Compare - Component Architecture   ║");
        System.out.println("║  Sprint 1 - UML2 Component Demonstration  ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
        
        try {
            runComponentDemo();
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void runComponentDemo() throws StockDataException {
        
        System.out.println("┌─ Building Component Architecture ─────────┐");
        
        System.out.println("│ Creating Data Layer Components...         │");
        IRepository repository = new Repository();
        ICacheManager cacheManager = new CacheManager();
        IAPIService apiService = new APIService();
        System.out.println("│   ✓ Repository                             │");
        System.out.println("│   ✓ CacheManager                           │");
        System.out.println("│   ✓ APIService                             │");
        
        System.out.println("│ Creating Service Layer Components...      │");
        IPriceAnalyzer priceAnalyzer = new PriceAnalyzer();
        IStockDataManager stockDataManager = new StockDataManager(
            repository, apiService, cacheManager, priceAnalyzer
        );
        IComparisonService comparisonService = new ComparisonService(
            stockDataManager, priceAnalyzer
        );
        System.out.println("│   ✓ PriceAnalyzer                          │");
        System.out.println("│   ✓ StockDataManager                       │");
        System.out.println("│   ✓ ComparisonService                      │");
        
        System.out.println("│ Creating Presentation Layer Components... │");
        IInputHandler inputHandler = new InputHandler();
        IChartDisplay chartDisplay = new ChartDisplay();
        MainUIController controller = new MainUIController(
            stockDataManager, chartDisplay, inputHandler
        );
        System.out.println("│   ✓ InputHandler                           │");
        System.out.println("│   ✓ ChartDisplay                           │");
        System.out.println("│   ✓ MainUIController                       │");
        
        System.out.println("└────────────────────────────────────────────┘\n");
        System.out.println("✓ All 9 components created successfully!\n");
        
        demonstrateWorkflow(controller, stockDataManager, priceAnalyzer, 
                           comparisonService, chartDisplay);
    }
    
    private static void demonstrateWorkflow(MainUIController controller,
                                           IStockDataManager stockDataManager,
                                           IPriceAnalyzer priceAnalyzer,
                                           IComparisonService comparisonService,
                                           IChartDisplay chartDisplay) 
            throws StockDataException {
        
        System.out.println("┌─ Workflow Demo ───────────────────────────┐");
        System.out.println("│ Scenario: User fetches AAPL stock data    │");
        System.out.println("└────────────────────────────────────────────┘\n");
        
        System.out.println("STEP 1: MainUIController.handleFetchStockData()");
        controller.handleFetchStockData();
        
        System.out.println("\nSTEP 2: Analyzing data with PriceAnalyzer");
        DateRange dateRange = new DateRange(
            LocalDate.now().minusMonths(1),
            LocalDate.now()
        );
        List<StockData> data = stockDataManager.fetchStockData("AAPL", dateRange);
        
        double priceChange = priceAnalyzer.calculatePriceChange(data);
        double avgPrice = priceAnalyzer.calculateAveragePrice(data);
        double[] highLow = priceAnalyzer.findHighLow(data);
        
        System.out.println("  Price Change: " + String.format("%.2f%%", priceChange));
        System.out.println("  Average Price: $" + String.format("%.2f", avgPrice));
        System.out.println("  High: $" + String.format("%.2f", highLow[0]));
        System.out.println("  Low: $" + String.format("%.2f", highLow[1]));
        
        System.out.println("\nSTEP 3: Comparing AAPL vs GOOGL");
        ComparisonResult result = comparisonService.compareStocks("AAPL", "GOOGL", dateRange);
        PerformanceMetrics metrics = comparisonService.calculateRelativePerformance(
            result.getStock1Data(), result.getStock2Data()
        );
        
        chartDisplay.renderComparisonChart(
            result.getStock1Data(), result.getStock2Data(), "AAPL", "GOOGL"
        );
        
        System.out.println("  AAPL Return: " + String.format("%.2f%%", metrics.getStock1Return()));
        System.out.println("  GOOGL Return: " + String.format("%.2f%%", metrics.getStock2Return()));
        System.out.println("  Relative Difference: " + String.format("%.2f%%", metrics.getRelativeDifference()));
        
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║         Demo Complete ✓                    ║");
        System.out.println("║  All 9 components working together!        ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
