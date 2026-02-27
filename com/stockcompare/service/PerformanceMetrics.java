package com.stockcompare.service;

/**
 * Performance metrics for comparison
 */
public class PerformanceMetrics {
    private double stock1Return;
    private double stock2Return;
    private double relativeDifference;
    
    public PerformanceMetrics(double stock1Return, double stock2Return) {
        this.stock1Return = stock1Return;
        this.stock2Return = stock2Return;
        this.relativeDifference = stock1Return - stock2Return;
    }
    
    public double getStock1Return() {
        return stock1Return;
    }
    
    public double getStock2Return() {
        return stock2Return;
    }
    
    public double getRelativeDifference() {
        return relativeDifference;
    }
}
