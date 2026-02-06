package com.shareapp;

import com.shareapp.architecture.RepositoryFactory;
import com.shareapp.model.DateRange;
import com.shareapp.model.StockPrice;
import com.shareapp.repository.IExternalDataSource;
import com.shareapp.repository.IStockRepository;
import com.shareapp.repository.MockExternalDataSource;
import com.shareapp.service.IStockPriceService;
import com.shareapp.service.StockPriceServiceImpl;

import java.time.LocalDate;
import java.util.List;

/**
 * Main Application Class - Sprint 1 Demonstration
 * 
 * This class demonstrates the complete architecture with all layers working together:
 * - Domain Model (StockPrice, DateRange)
 * - Service Layer (IStockPriceService, StockPriceServiceImpl)
 * - Repository Layer (IStockRepository, MockStockRepository)
 * - External Data Source (IExternalDataSource, MockExternalDataSource)
 * - Factory Pattern (RepositoryFactory)
 * 
 * Run this class to see the architecture in action for Sprint 1 code review.
 */
public class SharePriceApp {
    
    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("SHARE PRICE COMPARISON APPLICATION - SPRINT 1 DEMO");
        System.out.println("Demonstrating Simple Architecture Principles");
        System.out.println("=".repeat(80));
        System.out.println();
        
        // Setup: Create dependencies using Factory Pattern
        System.out.println("--- SETUP: Creating Components ---");
        IStockRepository repository = RepositoryFactory.createDefaultRepository();
        IExternalDataSource dataSource = new MockExternalDataSource();
        IStockPriceService service = new StockPriceServiceImpl(repository, dataSource);
        System.out.println();
        
        // Demo 1: Fetch and Display Stock Data
        demonstrateFetchAndDisplay(service);
        
        // Demo 2: Demonstrate Caching
        demonstrateCaching(service);
        
        // Demo 3: Demonstrate Date Range Validation
        demonstrateDateRangeValidation();
        
        // Demo 4: Demonstrate Data Comparison
        demonstrateComparison(service);
        
        System.out.println("=".repeat(80));
        System.out.println("SPRINT 1 DEMO COMPLETED SUCCESSFULLY");
        System.out.println("Architecture Principles Demonstrated:");
        System.out.println("✓ Separation of Concerns (Layered Architecture)");
        System.out.println("✓ Dependency Inversion (Interfaces)");
        System.out.println("✓ Single Responsibility (Each component has one job)");
        System.out.println("✓ Repository Pattern (Data access abstraction)");
        System.out.println("✓ Factory Pattern (Object creation)");
        System.out.println("=".repeat(80));
    }
    
    /**
     * Demo 1: Fetch stock data and display it
     */
    private static void demonstrateFetchAndDisplay(IStockPriceService service) {
        System.out.println("--- DEMO 1: Fetch and Display Stock Data ---");
        
        String symbol = "AAPL";
        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now();
        
        System.out.println(String.format("Fetching %s from %s to %s", symbol, startDate, endDate));
        
        List<StockPrice> prices = service.getStockPrices(symbol, startDate, endDate);
        
        System.out.println(String.format("\nRetrieved %d records:", prices.size()));
        
        // Display first 5 records
        prices.stream()
            .limit(5)
            .forEach(price -> System.out.println(String.format(
                "  %s: Open=%.2f, High=%.2f, Low=%.2f, Close=%.2f, Volume=%d",
                price.getDate(), price.getOpen(), price.getHigh(), 
                price.getLow(), price.getClose(), price.getVolume())));
        
        if (prices.size() > 5) {
            System.out.println(String.format("  ... and %d more records", prices.size() - 5));
        }
        
        System.out.println();
    }
    
    /**
     * Demo 2: Demonstrate caching behavior
     */
    private static void demonstrateCaching(IStockPriceService service) {
        System.out.println("--- DEMO 2: Demonstrate Caching ---");
        
        String symbol = "GOOGL";
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 31);
        
        System.out.println("First fetch (should go to external data source):");
        List<StockPrice> firstFetch = service.getStockPrices(symbol, startDate, endDate);
        System.out.println(String.format("Retrieved %d records\n", firstFetch.size()));
        
        System.out.println("Second fetch (should use cache):");
        List<StockPrice> secondFetch = service.getStockPrices(symbol, startDate, endDate);
        System.out.println(String.format("Retrieved %d records\n", secondFetch.size()));
        
        boolean dataAvailable = service.isDataAvailableLocally(symbol, startDate, endDate);
        System.out.println(String.format("Is data available locally? %s\n", dataAvailable));
    }
    
    /**
     * Demo 3: Demonstrate date range validation
     */
    private static void demonstrateDateRangeValidation() {
        System.out.println("--- DEMO 3: Date Range Validation ---");
        
        // Valid range
        try {
            DateRange validRange = new DateRange(
                LocalDate.now().minusMonths(6),
                LocalDate.now()
            );
            System.out.println(String.format("✓ Valid range created: %s", validRange));
        } catch (Exception e) {
            System.err.println(String.format("✗ Unexpected error: %s", e.getMessage()));
        }
        
        // Invalid range (exceeds 2 years)
        try {
            DateRange invalidRange = new DateRange(
                LocalDate.now().minusYears(3),
                LocalDate.now()
            );
            System.err.println("✗ Should have thrown exception for range > 2 years");
        } catch (IllegalArgumentException e) {
            System.out.println(String.format("✓ Correctly rejected invalid range: %s", e.getMessage()));
        }
        
        // Invalid range (start after end)
        try {
            DateRange invalidRange = new DateRange(
                LocalDate.now(),
                LocalDate.now().minusMonths(1)
            );
            System.err.println("✗ Should have thrown exception for start > end");
        } catch (IllegalArgumentException e) {
            System.out.println(String.format("✓ Correctly rejected invalid range: %s", e.getMessage()));
        }
        
        System.out.println();
    }
    
    /**
     * Demo 4: Demonstrate comparing two stocks
     */
    private static void demonstrateComparison(IStockPriceService service) {
        System.out.println("--- DEMO 4: Stock Comparison ---");
        
        String symbol1 = "AAPL";
        String symbol2 = "MSFT";
        LocalDate startDate = LocalDate.of(2024, 6, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 30);
        
        System.out.println(String.format("Comparing %s vs %s for June 2024\n", symbol1, symbol2));
        
        List<StockPrice> prices1 = service.getStockPrices(symbol1, startDate, endDate);
        List<StockPrice> prices2 = service.getStockPrices(symbol2, startDate, endDate);
        
        if (!prices1.isEmpty() && !prices2.isEmpty()) {
            StockPrice firstDay1 = prices1.get(0);
            StockPrice lastDay1 = prices1.get(prices1.size() - 1);
            
            StockPrice firstDay2 = prices2.get(0);
            StockPrice lastDay2 = prices2.get(prices2.size() - 1);
            
            System.out.println(String.format("%s Performance:", symbol1));
            System.out.println(String.format("  Start: %.2f  End: %.2f  Change: %.2f%%",
                firstDay1.getClose(), lastDay1.getClose(), 
                calculatePercentChange(firstDay1.getClose(), lastDay1.getClose())));
            
            System.out.println(String.format("\n%s Performance:", symbol2));
            System.out.println(String.format("  Start: %.2f  End: %.2f  Change: %.2f%%",
                firstDay2.getClose(), lastDay2.getClose(),
                calculatePercentChange(firstDay2.getClose(), lastDay2.getClose())));
        }
        
        System.out.println();
    }
    
    /**
     * Helper method to calculate percentage change
     */
    private static double calculatePercentChange(java.math.BigDecimal start, java.math.BigDecimal end) {
        if (start.compareTo(java.math.BigDecimal.ZERO) == 0) {
            return 0.0;
        }
        return end.subtract(start)
                  .divide(start, 4, java.math.BigDecimal.ROUND_HALF_UP)
                  .multiply(new java.math.BigDecimal("100"))
                  .doubleValue();
    }
}
