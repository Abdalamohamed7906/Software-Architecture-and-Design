package com.shareapp.repository;

import com.shareapp.model.StockPrice;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Mock implementation of IExternalDataSource for Sprint 1
 * 
 * Generates realistic-looking dummy stock data for demonstration purposes.
 * Simulates what a real API client would return.
 * 
 * Sprint 1 Purpose:
 * - Demonstrates adapter pattern
 * - Enables end-to-end testing without API dependency
 * - Provides working system for code review
 * 
 * Future Sprints: Replace with actual YahooFinanceAPIClient
 */
public class MockExternalDataSource implements IExternalDataSource {
    
    private final Random random = new Random();
    private final List<String> validSymbols = List.of("AAPL", "GOOGL", "MSFT", "AMZN", "TSLA", "META");
    
    public MockExternalDataSource() {
        System.out.println("[DataSource] MockExternalDataSource initialized");
    }
    
    @Override
    public List<StockPrice> fetchStockPrices(String symbol, LocalDate startDate, LocalDate endDate) {
        System.out.println(String.format("[DataSource] Fetching mock data for %s from %s to %s",
                                        symbol, startDate, endDate));
        
        if (!validateSymbol(symbol)) {
            throw new IllegalArgumentException("Invalid symbol: " + symbol);
        }
        
        List<StockPrice> prices = new ArrayList<>();
        
        // Generate base price based on symbol (for consistency across calls)
        BigDecimal basePrice = getBasePriceForSymbol(symbol);
        
        // Generate data for each business day in the range
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            // Skip weekends (stock markets are closed)
            if (isBusinessDay(currentDate)) {
                StockPrice price = generateStockPrice(symbol, currentDate, basePrice);
                prices.add(price);
                
                // Slight random walk for next day's price
                basePrice = price.getClose().add(
                    new BigDecimal(random.nextDouble() * 10 - 5) // Random change -5 to +5
                );
            }
            
            currentDate = currentDate.plusDays(1);
        }
        
        System.out.println(String.format("[DataSource] Generated %d mock records", prices.size()));
        
        return prices;
    }
    
    @Override
    public boolean validateSymbol(String symbol) {
        if (symbol == null || symbol.trim().isEmpty()) {
            return false;
        }
        
        // For mock purposes, accept any of the predefined symbols
        return validSymbols.contains(symbol.toUpperCase());
    }
    
    @Override
    public boolean isAvailable() {
        // Mock data source is always available (no network required)
        return true;
    }
    
    @Override
    public String getDataSourceName() {
        return "Mock External Data Source (Sprint 1)";
    }
    
    /**
     * Generates a single StockPrice record with realistic OHLCV data
     */
    private StockPrice generateStockPrice(String symbol, LocalDate date, BigDecimal basePrice) {
        // Generate realistic OHLCV data
        BigDecimal open = basePrice.add(new BigDecimal(random.nextDouble() * 2 - 1));
        
        double highVariation = random.nextDouble() * 5;
        BigDecimal high = open.add(new BigDecimal(highVariation));
        
        double lowVariation = random.nextDouble() * 5;
        BigDecimal low = open.subtract(new BigDecimal(lowVariation));
        
        BigDecimal close = low.add(new BigDecimal(random.nextDouble() * (high.subtract(low).doubleValue())));
        
        // Adjusted close (typically similar to close, accounting for dividends/splits)
        BigDecimal adjustedClose = close.multiply(new BigDecimal(0.98 + random.nextDouble() * 0.04));
        
        // Volume (random between 1M and 100M shares)
        long volume = (long) (1_000_000 + random.nextInt(99_000_000));
        
        return new StockPrice(
            symbol,
            date,
            roundToTwo(open),
            roundToTwo(high),
            roundToTwo(low),
            roundToTwo(close),
            roundToTwo(adjustedClose),
            volume
        );
    }
    
    /**
     * Get base price for a symbol (consistent across calls)
     */
    private BigDecimal getBasePriceForSymbol(String symbol) {
        // Assign different base prices to different symbols for variety
        return switch (symbol.toUpperCase()) {
            case "AAPL" -> new BigDecimal("150.00");
            case "GOOGL" -> new BigDecimal("120.00");
            case "MSFT" -> new BigDecimal("300.00");
            case "AMZN" -> new BigDecimal("130.00");
            case "TSLA" -> new BigDecimal("200.00");
            case "META" -> new BigDecimal("280.00");
            default -> new BigDecimal("100.00");
        };
    }
    
    /**
     * Check if a date is a business day (Monday-Friday)
     */
    private boolean isBusinessDay(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    }
    
    /**
     * Round BigDecimal to 2 decimal places (standard for stock prices)
     */
    private BigDecimal roundToTwo(BigDecimal value) {
        return value.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
