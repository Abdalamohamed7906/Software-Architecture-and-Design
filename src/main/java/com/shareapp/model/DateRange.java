package com.shareapp.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Value Object representing a date range with validation.
 * Ensures date ranges don't exceed 2 years as per requirements.
 * 
 * Sprint 1: Demonstrates domain-driven design and business rule enforcement
 */
public class DateRange {
    
    private static final int MAX_DAYS = 730; // Maximum 2 years
    
    private final LocalDate startDate;
    private final LocalDate endDate;
    
    /**
     * Creates a validated date range
     * 
     * @param startDate Beginning of the range
     * @param endDate End of the range
     * @throws IllegalArgumentException if validation fails
     */
    public DateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
        
        if (endDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("End date cannot be in the future");
        }
        
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        if (daysBetween > MAX_DAYS) {
            throw new IllegalArgumentException(
                String.format("Date range exceeds maximum of %d days (2 years). Requested: %d days",
                            MAX_DAYS, daysBetween));
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
     * Get the number of days in this range
     */
    public long getDays() {
        return ChronoUnit.DAYS.between(startDate, endDate) + 1; // +1 to include both endpoints
    }
    
    /**
     * Check if a date falls within this range
     */
    public boolean contains(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateRange dateRange = (DateRange) o;
        return startDate.equals(dateRange.startDate) && endDate.equals(dateRange.endDate);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }
    
    @Override
    public String toString() {
        return String.format("DateRange{%s to %s (%d days)}", startDate, endDate, getDays());
    }
}
