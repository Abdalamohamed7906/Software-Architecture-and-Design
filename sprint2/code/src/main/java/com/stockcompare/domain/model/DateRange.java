package com.stockcompare.domain.model;

import java.time.LocalDate;

/**
 * DateRange — validated date range.
 * Matches DateRange in ISelectDateRange system interface diagram.
 * Business rule: max 2 years, start must be before end.
 */
public class DateRange {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public DateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null)
            throw new IllegalArgumentException("Dates cannot be null");
        if (startDate.isAfter(endDate))
            throw new IllegalArgumentException("Start date must be before end date");
        if (startDate.isBefore(endDate.minusYears(2)))
            throw new IllegalArgumentException("Date range cannot exceed 2 years");
        this.startDate = startDate;
        this.endDate   = endDate;
    }

    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate()   { return endDate;   }

    @Override
    public String toString() { return startDate + " → " + endDate; }
}
