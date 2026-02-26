package com.stockcompare.presentation;

import com.stockcompare.domain.DateRange;
import java.time.LocalDate;

/**
 * COMPONENT: InputHandler
 * PROVIDED INTERFACE: IInputHandler
 * REQUIRED INTERFACES: None
 */
public class InputHandler implements IInputHandler {
    
    private String stockSymbol;
    private DateRange dateRange;
    
    public InputHandler() {
        this.stockSymbol = "AAPL";
        this.dateRange = new DateRange(
            LocalDate.now().minusMonths(6),
            LocalDate.now()
        );
    }
    
    @Override
    public String getStockSymbol() {
        return stockSymbol;
    }
    
    @Override
    public DateRange getDateRange() {
        return dateRange;
    }
    
    @Override
    public boolean validateInput() {
        return stockSymbol != null && !stockSymbol.isEmpty();
    }
    
    @Override
    public void displayValidationError(String field, String message) {
        System.err.println("ERROR in " + field + ": " + message);
    }
    
    @Override
    public void clearInputs() {
        stockSymbol = null;
        dateRange = null;
    }
    
    @Override
    public void setInputEnabled(boolean enabled) {
        // No-op for demo
    }
    
    public void setStockSymbol(String symbol) {
        this.stockSymbol = symbol;
    }
    
    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }
}
