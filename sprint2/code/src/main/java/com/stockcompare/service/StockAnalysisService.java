package com.stockcompare.service;

import com.stockcompare.domain.interfaces.*;
import com.stockcompare.domain.model.PriceData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * StockAnalysisService — implements IStockAnalysisService.
 *
 * Matches View Share Price Graph sequence diagram:
 *   UI → requestGraph() → GraphService[StockAnalysisService]
 *   StockAnalysisService → getPriceData()     → PriceService
 *   PriceService → data → StockAnalysisService
 *   StockAnalysisService → generateGraph(data) [self-loop, data prep]
 *   StockAnalysisService → graph → UI → displayGraph() → User
 *
 * Matches Compare Share Prices sequence diagram:
 *   UI → compareShares() → CompareService[StockAnalysisService]
 *   StockAnalysisService → fetchMultipleData() → StockAPI (via PriceService)
 *   StockAnalysisService → processComparison() [self-loop]
 *   StockAnalysisService → results → UI → displayComparison() → User
 *
 * SOLID — Single Responsibility: analysis and comparison logic only.
 * SOLID — Dependency Inversion: depends on IStockService, not PriceService directly.
 */
public class StockAnalysisService implements IStockAnalysisService {

    private final IStockService priceService; // injected — PriceService

    public StockAnalysisService(IStockService priceService) {
        this.priceService = priceService;
    }

    // getPriceDataForGraph() — calls getPriceData() on PriceService in View Graph diagram
    @Override
    public List<PriceData> getPriceDataForGraph(String symbol,
                                                 LocalDate start, LocalDate end) {
        if (symbol == null || symbol.isBlank())
            throw new IllegalArgumentException("Symbol cannot be empty.");

        System.out.println("[StockAnalysisService] Generating graph data for " + symbol);
        return priceService.getPriceData(symbol.toUpperCase(), start, end);
    }

    // compareShares() → fetchMultipleData() → processComparison() in Compare diagram
    @Override
    public List<List<PriceData>> compareShares(List<String> symbols,
                                                LocalDate start, LocalDate end) {
        if (symbols == null || symbols.size() < 2)
            throw new IllegalArgumentException("At least 2 symbols required for comparison.");

        System.out.println("[StockAnalysisService] Comparing: " + symbols);
        List<List<PriceData>> result = new ArrayList<>();

        // fetchMultipleData() — fetches each symbol via PriceService
        for (String symbol : symbols) {
            List<PriceData> data = priceService.getPriceData(
                    symbol.toUpperCase(), start, end);
            // processComparison() self-loop — normalise/align dates if needed
            result.add(data);
        }

        System.out.println("[StockAnalysisService] Comparison complete for " + symbols.size() + " symbols.");
        return result;
    }
}
