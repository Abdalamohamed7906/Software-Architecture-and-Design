package com.shareapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Domain Model Entity representing a single day's stock price data.
 * This class follows the Value Object pattern for immutability and data integrity.
 * 
 * Sprint 1: Abstract implementation demonstrating Simple Architecture principles
 * - Single Responsibility: Represents stock price data only
 * - No dependencies on other layers
 */
public class StockPrice {
    
    // Core identification fields
    private final String symbol;
    private final LocalDate date;
    
    // OHLCV data (Open, High, Low, Close, Volume)
    private final BigDecimal open;
    private final BigDecimal high;
    private final BigDecimal low;
    private final BigDecimal close;
    private final BigDecimal adjustedClose;
    private final long volume;
    
    /**
     * Constructor for creating a StockPrice instance
     * Uses BigDecimal for precise decimal calculations (important for financial data)
     * 
     * @param symbol Stock ticker symbol (e.g., "AAPL", "GOOGL")
     * @param date Trading date
     * @param open Opening price
     * @param high Highest price during the day
     * @param low Lowest price during the day
     * @param close Closing price
     * @param adjustedClose Adjusted closing price (accounting for splits, dividends)
     * @param volume Number of shares traded
     */
    public StockPrice(String symbol, LocalDate date, BigDecimal open, BigDecimal high, 
                     BigDecimal low, BigDecimal close, BigDecimal adjustedClose, long volume) {
        // Validation to ensure data integrity
        if (symbol == null || symbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Symbol cannot be null or empty");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        
        this.symbol = symbol.toUpperCase(); // Normalize to uppercase
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjustedClose = adjustedClose;
        this.volume = volume;
    }
    
    // Getters (no setters - immutable object)
    
    public String getSymbol() {
        return symbol;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public BigDecimal getOpen() {
        return open;
    }
    
    public BigDecimal getHigh() {
        return high;
    }
    
    public BigDecimal getLow() {
        return low;
    }
    
    public BigDecimal getClose() {
        return close;
    }
    
    public BigDecimal getAdjustedClose() {
        return adjustedClose;
    }
    
    public long getVolume() {
        return volume;
    }
    
    /**
     * Calculate price change for the day
     * @return Difference between close and open
     */
    public BigDecimal getDailyChange() {
        if (close != null && open != null) {
            return close.subtract(open);
        }
        return BigDecimal.ZERO;
    }
    
    /**
     * Calculate percentage change for the day
     * @return Percentage change from open to close
     */
    public BigDecimal getPercentageChange() {
        if (close != null && open != null && open.compareTo(BigDecimal.ZERO) != 0) {
            return close.subtract(open)
                       .divide(open, 4, BigDecimal.ROUND_HALF_UP)
                       .multiply(new BigDecimal("100"));
        }
        return BigDecimal.ZERO;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockPrice that = (StockPrice) o;
        return symbol.equals(that.symbol) && date.equals(that.date);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(symbol, date);
    }
    
    @Override
    public String toString() {
        return String.format("StockPrice{symbol='%s', date=%s, close=%.2f, volume=%d}",
                           symbol, date, close, volume);
    }
}
