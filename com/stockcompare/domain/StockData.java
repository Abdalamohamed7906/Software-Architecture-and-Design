package com.stockcompare.domain;

import java.time.LocalDate;

/**
 * Domain model representing stock price data for a single day
 */
public class StockData {
    
    private String symbol;
    private LocalDate date;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;
    
    public StockData(String symbol, LocalDate date, double open, 
                     double high, double low, double close, long volume) {
        this.symbol = symbol;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }
    
    public String getSymbol() { return symbol; }
    public LocalDate getDate() { return date; }
    public double getOpen() { return open; }
    public double getHigh() { return high; }
    public double getLow() { return low; }
    public double getClose() { return close; }
    public long getVolume() { return volume; }
    
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setOpen(double open) { this.open = open; }
    public void setHigh(double high) { this.high = high; }
    public void setLow(double low) { this.low = low; }
    public void setClose(double close) { this.close = close; }
    public void setVolume(long volume) { this.volume = volume; }
    
    @Override
    public String toString() {
        return String.format("StockData{symbol='%s', date=%s, close=%.2f}", 
                           symbol, date, close);
    }
}
