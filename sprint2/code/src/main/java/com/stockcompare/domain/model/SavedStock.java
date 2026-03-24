package com.stockcompare.domain.model;

import java.time.LocalDate;
import java.util.List;

/**
 * SavedStock — a stock saved by a RegisteredUser with its cached price data.
 * Used in ISavedStockService, LoadService, DeleteService diagrams.
 */
public class SavedStock {
    private final String    savedStockId;
    private final String    userId;
    private final String    symbol;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private List<PriceData> priceData;

    public SavedStock(String savedStockId, String userId, String symbol,
                      LocalDate startDate, LocalDate endDate,
                      List<PriceData> priceData) {
        this.savedStockId = savedStockId;
        this.userId       = userId;
        this.symbol       = symbol;
        this.startDate    = startDate;
        this.endDate      = endDate;
        this.priceData    = priceData;
    }

    public String    getSavedStockId()              { return savedStockId; }
    public String    getUserId()                    { return userId;       }
    public String    getSymbol()                    { return symbol;       }
    public LocalDate getStartDate()                 { return startDate;    }
    public LocalDate getEndDate()                   { return endDate;      }
    public List<PriceData> getPriceData()           { return priceData;    }
    public void setPriceData(List<PriceData> data)  { this.priceData=data; }

    @Override
    public String toString() {
        int count = priceData != null ? priceData.size() : 0;
        return String.format("SavedStock{id='%s', symbol='%s', range=%s→%s, records=%d}",
                savedStockId, symbol, startDate, endDate, count);
    }
}
