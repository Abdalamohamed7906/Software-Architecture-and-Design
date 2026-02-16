package com.stockcompare.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a date range for stock data queries
 * Enforces business rule: maximum 2 years range
 */
public class DateRange {
    
    private LocalDate startDate;
    private LocalDate endDate;
    private static final long MAX_DAYS = 730; // 2 years
    
    /**
     * Creates a date range with validation
     * @param startDate Beginning of range
     * @param endDate End of range
     * @throws IllegalArgumentException if range exceeds 2 years
     */
    public DateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        if (daysBetween > MAX_DAYS) {
            throw new IllegalArgumentException(
                "Date range exceeds maximum of 2 years (" + MAX_DAYS + " days)");
        }
        
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    /**
     * Calculate number of days in range
     * @return Number of days between start and end
     */
    public long getDayCount() {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
    
    /**
     * Check if a date falls within this range
     * @param date Date to check
     * @return true if date is within range
     */
    public boolean contains(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
    
    @Override
    public String toString() {
        return String.format("DateRange{%s to %s}", startDate, endDate);
    }
}
