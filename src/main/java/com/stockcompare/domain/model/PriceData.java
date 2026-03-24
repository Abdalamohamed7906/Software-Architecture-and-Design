package com.stockcompare.domain.model;

import java.time.LocalDate;

/**
 * PriceData — one day's OHLCV record for a symbol.
 * Matches PriceData[] return types throughout your system interface diagrams.
 * Clean Architecture — Entity: pure domain value object.
 */
public class PriceData {
    private final String    symbol;
    private final LocalDate date;
    private final double    open, high, low, close;
    private final long      volume;

    public PriceData(String symbol, LocalDate date,
                     double open, double high, double low,
                     double close, long volume) {
        this.symbol = symbol; this.date   = date;
        this.open   = open;   this.high   = high;
        this.low    = low;    this.close  = close;
        this.volume = volume;
    }

    public String    getSymbol() { return symbol; }
    public LocalDate getDate()   { return date;   }
    public double    getOpen()   { return open;   }
    public double    getHigh()   { return high;   }
    public double    getLow()    { return low;    }
    public double    getClose()  { return close;  }
    public long      getVolume() { return volume; }

    @Override
    public String toString() {
        return String.format("%-6s | %s | O:%.2f H:%.2f L:%.2f C:%.2f V:%d",
                symbol, date, open, high, low, close, volume);
    }
}
