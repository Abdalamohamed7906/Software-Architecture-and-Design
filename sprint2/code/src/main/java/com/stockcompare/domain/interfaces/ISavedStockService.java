package com.stockcompare.domain.interfaces;

import com.stockcompare.domain.model.*;
import java.time.LocalDate;
import java.util.List;

/**
 * ISavedStockService — business interface for UC6, UC7, UC10.
 *
 * Matches Save Stock Data sequence:
 *   UI → saveStockData() → SaveService → storeData() → Database
 *
 * Matches Load Saved Stock Data sequence:
 *   UI → getSavedStocks() → LoadService → fetchSavedStocks() → Database
 *   UI → loadStockData()  → LoadService → fetchStockData()   → Database
 *
 * Matches Delete Saved Stock sequence:
 *   UI → deleteStock() → DeleteService → removeStock() → Database
 *
 * SOLID — Single Responsibility: saved stock lifecycle only.
 */
public interface ISavedStockService {
    SavedStock       saveStockData(String userId, String symbol,
                                   LocalDate start, LocalDate end,
                                   List<PriceData> data);
    List<SavedStock> getSavedStocks(String userId);
    List<PriceData>  loadStockData(String savedStockId);
    boolean          deleteStock(String savedStockId);
}
