package com.stockcompare.presentation;

import com.stockcompare.domain.DateRange;

/**
 * Interface for user input handling
 * Captures and validates user input
 * 
 * ○ PROVIDED INTERFACE
 * This interface is PROVIDED by: InputFormComponent
 * This interface is REQUIRED by: MainUIController
 */
public interface IInputHandler {
    
    /**
     * Get stock symbol from user input
     * 
     * @return Stock ticker symbol
     */
    String getStockSymbol();
    
    /**
     * Get date range from user input
     * 
     * @return DateRange object
     */
    DateRange getDateRange();
    
    /**
     * Validate all user inputs
     * 
     * @return true if all inputs are valid
     */
    boolean validateInput();
    
    /**
     * Display validation error message
     * 
     * @param field Field name with error
     * @param message Error message
     */
    void displayValidationError(String field, String message);
    
    /**
     * Clear all input fields
     */
    void clearInputs();
    
    /**
     * Enable or disable input controls
     * 
     * @param enabled true to enable, false to disable
     */
    void setInputEnabled(boolean enabled);
}
