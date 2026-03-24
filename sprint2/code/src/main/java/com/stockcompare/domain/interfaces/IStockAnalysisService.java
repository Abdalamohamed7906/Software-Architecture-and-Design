package com.stockcompare.domain.interfaces;

import com.stockcompare.domain.model.PriceData;
import java.time.LocalDate;
import java.util.List;

/**
 * IStockAnalysisService — business interface for UC8 and UC9.
 *
 * Matches View Share Price Graph sequence:
 *   UI → requestGraph() → GraphService → getPriceData() → PriceService
 *                                      → generateGraph(data) → GraphService
 *
 * Matches Compare Share Prices sequence:
 *   UI → compareShares() → CompareService → fetchMultipleData() → StockAPI
 *                                         → processComparison() [self-loop]
 *
 * SOLID — Single Responsibility: analysis and visualisation data preparation only.
 */
public interface IStockAnalysisService {
    List<PriceData>       getPriceDataForGraph(String symbol, LocalDate start, LocalDate end);
    List<List<PriceData>> compareShares(List<String> symbols, LocalDate start, LocalDate end);
}
