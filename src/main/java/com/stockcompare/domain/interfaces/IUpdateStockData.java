package com.stockcompare.domain.interfaces;

import com.stockcompare.domain.model.PriceData;
import java.util.List;

/**
 * IUpdateStockData — system interface for UC11 (Admin only).
 *
 * Matches Update Stored Stock Data sequence diagram:
 *   System → triggerUpdate() → UpdateService
 *   UpdateService → fetchLatestData() → StockAPI
 *   UpdateService → replaceData()     → Database
 *   UpdateService → updateComplete()  → System
 *
 * Matches your diagram method signatures exactly:
 *   Boolean updateStoredStockData()
 *   PriceData[] fetchLatestStockData()
 *
 * SOLID — Single Responsibility: admin stock refresh only.
 */
public interface IUpdateStockData {
    boolean      updateStoredStockData();
    boolean      updateStoredStockData(List<String> symbols);
    List<PriceData> fetchLatestStockData(String symbol);
}
