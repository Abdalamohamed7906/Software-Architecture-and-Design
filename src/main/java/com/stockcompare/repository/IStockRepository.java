package com.stockcompare.repository;

import com.stockcompare.domain.model.PriceData;
import java.time.LocalDate;
import java.util.List;

/**
 * IStockRepository — matches StockRepository in your architecture diagram.
 * Acts as TempStorage in your Retrieve Price Data sequence diagram.
 */
public interface IStockRepository {
    List<PriceData> findPriceData(String symbol, LocalDate start, LocalDate end);
    boolean         storePriceData(List<PriceData> data);
    boolean         deletePriceData(String symbol);
    boolean         hasCachedData(String symbol, LocalDate start, LocalDate end);
}
