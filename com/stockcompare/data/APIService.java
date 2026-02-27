package com.stockcompare.data;

import com.stockcompare.domain.DateRange;
import com.stockcompare.domain.StockData;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * COMPONENT: APIService
 * PROVIDED INTERFACE: IAPIService
 * REQUIRED INTERFACES: None
 */
public class APIService implements IAPIService {
    
    private final Random random = new Random();
    
    @Override
    public List<StockData> fetchHistoricalData(String symbol, DateRange dateRange) 
            throws APIException {
        
        System.out.println("    [API] Fetching data for " + symbol);
        
        // Simulate API delay
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Generate mock data
        List<StockData> data = new ArrayList<>();
        LocalDate current = dateRange.getStartDate();
        double basePrice = getBasePriceForSymbol(symbol);
        
        while (!current.isAfter(dateRange.getEndDate())) {
            double open = basePrice + (random.nextDouble() * 10 - 5);
            double high = open + random.nextDouble() * 5;
            double low = open - random.nextDouble() * 5;
            double close = low + random.nextDouble() * (high - low);
            long volume = 1000000L + random.nextInt(50000000);
            
            data.add(new StockData(symbol, current, open, high, low, close, volume));
            
            basePrice = close;
            current = current.plusDays(1);
        }
        
        return data;
    }
    
    @Override
    public boolean validateSymbol(String symbol) {
        return symbol != null && symbol.matches("^[A-Z]{1,5}$");
    }
    
    @Override
    public boolean isConnectionAvailable() {
        return true;
    }
    
    @Override
    public int getRateLimitRemaining() {
        return 100;
    }
    
    private double getBasePriceForSymbol(String symbol) {
        switch (symbol) {
            case "AAPL": return 185.0;
            case "GOOGL": return 140.0;
            case "MSFT": return 380.0;
            case "TSLA": return 250.0;
            default: return 100.0;
        }
    }
}
