package com.stockcompare.service;

import com.stockcompare.domain.StockData;
import java.util.List;

/**
 * Result of stock comparison containing both datasets
 */
public class ComparisonResult {
    private List<StockData> stock1Data;
    private List<StockData> stock2Data;
    
    public ComparisonResult(List<StockData> stock1Data, List<StockData> stock2Data) {
        this.stock1Data = stock1Data;
        this.stock2Data = stock2Data;
    }
    
    public List<StockData> getStock1Data() {
        return stock1Data;
    }
    
    public List<StockData> getStock2Data() {
        return stock2Data;
    }
}
