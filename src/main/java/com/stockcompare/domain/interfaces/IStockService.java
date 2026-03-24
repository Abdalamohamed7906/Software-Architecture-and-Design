package com.stockcompare.domain.interfaces;

import com.stockcompare.domain.model.*;
import java.time.LocalDate;
import java.util.List;

/**
 * IStockService — business interface for UC3, UC4, UC5, UC11.
 *
 * Matches Search Share Symbol sequence:
 *   UI → searchShare(query) → SearchService → fetchSymbols(query) → StockAPI
 *
 * Matches Retrieve Share Price Data sequence:
 *   UI → getPriceData() → PriceService → fetchData() → StockAPI
 *                                      → storeTempData() → TempStorage
 *
 * Matches Select Date Range sequence:
 *   UI → validateDates() → DateService → valid/invalid
 *
 * Matches Update Stored Stock Data sequence:
 *   System → triggerUpdate() → UpdateService → fetchLatestData() → StockAPI
 *                                            → replaceData() → Database
 *
 * SOLID — Single Responsibility: stock data retrieval and search only.
 */
public interface IStockService {
    List<ShareDetail> searchShareSymbol(String query);
    List<PriceData>   getPriceData(String symbol, LocalDate start, LocalDate end);
    boolean           validateDateRange(LocalDate start, LocalDate end);
    boolean           refreshStock(String symbol);
}
