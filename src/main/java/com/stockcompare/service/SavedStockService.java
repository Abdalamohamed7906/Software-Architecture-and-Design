package com.stockcompare.service;

import com.stockcompare.domain.interfaces.ISavedStockService;
import com.stockcompare.domain.model.*;
import com.stockcompare.repository.ISavedStockRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * SavedStockService — implements ISavedStockService.
 *
 * Matches Save Stock Data sequence diagram:
 *   UI → saveStockData() → SaveService[SavedStockService]
 *   SavedStockService → storeData() → Database (SavedStockRepository)
 *   Database → success → SavedStockService → saveConfirmed() → UI
 *
 * Matches Load Saved Stock Data sequence diagram:
 *   UI → getSavedStocks()  → LoadService[SavedStockService]
 *   SavedStockService → fetchSavedStocks() → Database
 *   UI → loadStockData(id) → LoadService[SavedStockService]
 *   SavedStockService → fetchStockData()   → Database
 *
 * Matches Delete Saved Stock sequence diagram:
 *   UI → deleteStock() → DeleteService[SavedStockService]
 *   SavedStockService → removeStock() → Database
 *   Database → success → deletionConfirmed() → UI
 *
 * SOLID — Single Responsibility: saved stock lifecycle only.
 * SOLID — Dependency Inversion: depends on ISavedStockRepository interface.
 */
public class SavedStockService implements ISavedStockService {

    private final ISavedStockRepository savedStockRepository;

    public SavedStockService(ISavedStockRepository savedStockRepository) {
        this.savedStockRepository = savedStockRepository;
    }

    // saveStockData() → storeData() → Database
    @Override
    public SavedStock saveStockData(String userId, String symbol,
                                    LocalDate start, LocalDate end,
                                    List<PriceData> data) {
        if (userId == null || userId.isBlank())
            throw new IllegalArgumentException("User ID required to save stock.");
        if (symbol == null || symbol.isBlank())
            throw new IllegalArgumentException("Symbol required.");
        if (data == null || data.isEmpty())
            throw new IllegalArgumentException("No price data to save.");

        SavedStock saved = savedStockRepository.save(
                userId, symbol.toUpperCase(), start, end, data);
        System.out.println("[SavedStockService] Saved: " + saved);
        return saved;
    }

    // getSavedStocks() → fetchSavedStocks() → Database
    @Override
    public List<SavedStock> getSavedStocks(String userId) {
        if (userId == null || userId.isBlank())
            throw new IllegalArgumentException("User ID required.");
        List<SavedStock> stocks = savedStockRepository.findByUserId(userId);
        System.out.println("[SavedStockService] Loaded " + stocks.size() + " saved stocks.");
        return stocks;
    }

    // loadStockData() → fetchStockData() → Database
    @Override
    public List<PriceData> loadStockData(String savedStockId) {
        return savedStockRepository.findById(savedStockId)
                .map(SavedStock::getPriceData)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Saved stock not found: " + savedStockId));
    }

    // deleteStock() → removeStock() → Database
    @Override
    public boolean deleteStock(String savedStockId) {
        if (savedStockId == null || savedStockId.isBlank())
            throw new IllegalArgumentException("Saved stock ID required.");
        boolean ok = savedStockRepository.deleteById(savedStockId);
        System.out.println("[SavedStockService] Delete " + savedStockId + ": " + (ok ? "success" : "failed"));
        return ok;
    }
}
