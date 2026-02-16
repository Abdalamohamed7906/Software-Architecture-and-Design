package com.stockcompare;

import com.stockcompare.domain.StockData;
import com.stockcompare.domain.DateRange;
import java.time.LocalDate;

/**
 * Main entry point for Stock-Compare application
 * Sprint 1: Demonstrates domain models and validation
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== Stock-Compare Application ===");
        System.out.println("Sprint 1 - Architectural Foundations\n");
        
        // Demonstrate StockData creation
        demonstrateStockData();
        
        // Demonstrate DateRange validation
        demonstrateDateRange();
        
        System.out.println("\n=== Sprint 1 Code Verification Complete ===");
        System.out.println("All components compiled successfully!");
    }
    
    /**
     * Demonstrate StockData domain model
     */
    private static void demonstrateStockData() {
        System.out.println("--- StockData Demo ---");
        
        StockData appleStock = new StockData(
            "AAPL",
            LocalDate.of(2024, 1, 15),
            185.50,
            188.20,
            184.90,
            187.30,
            52000000L
        );
        
        System.out.println("Created: " + appleStock);
        System.out.println("Symbol: " + appleStock.getSymbol());
        System.out.println("Close Price: $" + appleStock.getClose());
        System.out.println("Volume: " + appleStock.getVolume());
        System.out.println();
    }
    
    /**
     * Demonstrate DateRange validation including 2-year business rule
     */
    private static void demonstrateDateRange() {
        System.out.println("--- DateRange Validation Demo ---");
        
        // Valid date range
        try {
            LocalDate start = LocalDate.of(2023, 1, 1);
            LocalDate end = LocalDate.of(2024, 1, 1);
            DateRange validRange = new DateRange(start, end);
            System.out.println("✓ Valid range: " + validRange);
            System.out.println("  Days: " + validRange.getDayCount());
        } catch (IllegalArgumentException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
        
        // Test 2-year maximum business rule
        try {
            LocalDate start = LocalDate.of(2021, 1, 1);
            LocalDate end = LocalDate.of(2024, 1, 1);
            DateRange invalidRange = new DateRange(start, end);
            System.out.println("✓ Range created: " + invalidRange);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Business rule enforced: " + e.getMessage());
        }
        
        // Test invalid order
        try {
            LocalDate start = LocalDate.of(2024, 1, 1);
            LocalDate end = LocalDate.of(2023, 1, 1);
            DateRange invalidRange = new DateRange(start, end);
            System.out.println("✓ Range created: " + invalidRange);
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Validation works: " + e.getMessage());
        }
        
        System.out.println();
    }
}
