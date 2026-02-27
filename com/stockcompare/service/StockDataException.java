package com.stockcompare.service;

/**
 * Custom exception for stock data operations
 */
public class StockDataException extends Exception {
    
    public StockDataException(String message) {
        super(message);
    }
    
    public StockDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
