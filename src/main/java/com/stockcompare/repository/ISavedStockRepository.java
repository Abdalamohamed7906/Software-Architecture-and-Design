package com.stockcompare.repository;

import com.stockcompare.domain.model.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * ISavedStockRepository — matches SavedStockRepository in your architecture diagram.
 * Used by Save/Load/Delete sequence diagrams via the service layer.
 */
public interface ISavedStockRepository {
    SavedStock           save(String userId, String symbol,
                              LocalDate start, LocalDate end,
                              List<PriceData> data);
    List<SavedStock>     findByUserId(String userId);
    Optional<SavedStock> findById(String savedStockId);
    boolean              deleteById(String savedStockId);
}
