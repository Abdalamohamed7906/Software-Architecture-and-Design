package com.stockcompare.presentation;

import com.stockcompare.domain.DateRange;

/**
 * INTERFACE: IInputHandler
 * PROVIDED BY: InputHandler component
 * REQUIRED BY: MainUIController
 */
public interface IInputHandler {
    
    String getStockSymbol();
    
    DateRange getDateRange();
    
    boolean validateInput();
    
    void displayValidationError(String field, String message);
    
    void clearInputs();
    
    void setInputEnabled(boolean enabled);
}
