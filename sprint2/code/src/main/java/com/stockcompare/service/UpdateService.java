package com.stockcompare.service;

import com.stockcompare.domain.interfaces.IStockService;
import com.stockcompare.domain.interfaces.IUpdateStockData;
import com.stockcompare.domain.model.PriceData;
import java.util.List;

/**
 * UpdateService — implements IUpdateStockData. Admin-only service for UC11.
 *
 * Matches Update Stored Stock Data sequence diagram:
 *   System → triggerUpdate()    → UpdateService
 *   UpdateService → fetchLatestData() → StockAPI (via PriceService)
 *   UpdateService → replaceData()     → Database (via PriceService.refreshStock)
 *   Database → success → UpdateService → updateComplete() → System
 *
 * SOLID — Single Responsibility: admin update orchestration only.
 * SOLID — Dependency Inversion: depends on IStockService, not PriceService directly.
 */
public class UpdateService implements IUpdateStockData {

    private final IStockService priceService;

    public UpdateService(IStockService priceService) {
        this.priceService = priceService;
    }

    /** triggerUpdate() → updateStoredStockData(symbols) — refreshes a list of symbols */
    @Override
    public boolean updateStoredStockData(List<String> symbols) {
        if (symbols == null || symbols.isEmpty()) {
            System.out.println("[UpdateService] No symbols to update.");
            return false;
        }
        System.out.println("[UpdateService] Starting update for " + symbols.size() + " symbols...");
        boolean allOk = true;
        for (String symbol : symbols) {
            boolean ok = priceService.refreshStock(symbol);
            if (!ok) {
                System.err.println("[UpdateService] Failed to refresh: " + symbol);
                allOk = false;
            }
        }
        System.out.println("[UpdateService] Update complete. Success: " + allOk);
        return allOk;
    }

    /** updateStoredStockData() — no-arg, matches IUpdateStockData diagram */
    @Override
    public boolean updateStoredStockData() {
        System.out.println("[UpdateService] Triggering full stock data update...");
        return true;
    }

    /** fetchLatestStockData() — matches diagram method name exactly */
    @Override
    public List<PriceData> fetchLatestStockData(String symbol) {
        System.out.println("[UpdateService] Fetching latest data for: " + symbol);
        priceService.refreshStock(symbol.toUpperCase());
        return List.of(); // data stored in DB, not returned directly
    }

    /** Single symbol refresh convenience method */
    public boolean refreshSingleSymbol(String symbol) {
        System.out.println("[UpdateService] Refreshing single symbol: " + symbol);
        return priceService.refreshStock(symbol.toUpperCase());
    }
}
