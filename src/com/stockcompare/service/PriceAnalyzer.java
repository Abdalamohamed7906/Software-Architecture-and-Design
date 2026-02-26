package com.stockcompare.service;

import com.stockcompare.domain.StockData;
import java.util.ArrayList;
import java.util.List;

/**
 * COMPONENT: PriceAnalyzer
 * PROVIDED INTERFACE: IPriceAnalyzer
 * REQUIRED INTERFACES: None
 */
public class PriceAnalyzer implements IPriceAnalyzer {
    
    @Override
    public double calculatePriceChange(List<StockData> data) {
        if (data == null || data.size() < 2) {
            return 0.0;
        }
        
        double startPrice = data.get(0).getClose();
        double endPrice = data.get(data.size() - 1).getClose();
        
        return ((endPrice - startPrice) / startPrice) * 100;
    }
    
    @Override
    public double[] findHighLow(List<StockData> data) {
        if (data == null || data.isEmpty()) {
            return new double[]{0.0, 0.0};
        }
        
        double high = Double.MIN_VALUE;
        double low = Double.MAX_VALUE;
        
        for (StockData stock : data) {
            if (stock.getHigh() > high) high = stock.getHigh();
            if (stock.getLow() < low) low = stock.getLow();
        }
        
        return new double[]{high, low};
    }
    
    @Override
    public double calculateAveragePrice(List<StockData> data) {
        if (data == null || data.isEmpty()) {
            return 0.0;
        }
        
        double sum = 0.0;
        for (StockData stock : data) {
            sum += stock.getClose();
        }
        
        return sum / data.size();
    }
    
    @Override
    public List<StockData> normalizeData(List<StockData> data) {
        if (data == null || data.isEmpty()) {
            return new ArrayList<>();
        }
        
        double basePrice = data.get(0).getClose();
        List<StockData> normalized = new ArrayList<>();
        
        for (StockData stock : data) {
            double normalizedClose = ((stock.getClose() - basePrice) / basePrice) * 100;
            
            StockData normalizedStock = new StockData(
                stock.getSymbol(),
                stock.getDate(),
                stock.getOpen(),
                stock.getHigh(),
                stock.getLow(),
                normalizedClose,
                stock.getVolume()
            );
            
            normalized.add(normalizedStock);
        }
        
        return normalized;
    }
    
    @Override
    public String formatForDisplay(List<StockData> data) {
        if (data == null || data.isEmpty()) {
            return "No data available";
        }
        
        StringBuilder formatted = new StringBuilder();
        formatted.append(String.format("%-12s %10s%n", "Date", "Close"));
        formatted.append("-".repeat(25)).append("\n");
        
        for (StockData stock : data) {
            formatted.append(String.format("%-12s $%9.2f%n",
                stock.getDate(), stock.getClose()
            ));
        }
        
        return formatted.toString();
    }
}
