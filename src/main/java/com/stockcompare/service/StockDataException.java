package com.stockcompare.service;

/**
 * Custom exception for stock data operations
 * Used throughout the application for data-related errors
 */
public class StockDataException extends Exception {
    
    public StockDataException(String message) {
        super(message);
    }
    
    public StockDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
