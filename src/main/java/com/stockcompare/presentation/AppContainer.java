package com.stockcompare.presentation;

import com.stockcompare.data.*;
import com.stockcompare.domain.interfaces.*;
import com.stockcompare.repository.*;
import com.stockcompare.service.*;

import java.sql.SQLException;

/**
 * AppContainer — manual dependency injection container.
 *
 * Wires all layers:
 *   Data → Repository → Service → Presentation
 *
 * Matches your architecture diagram layers:
 *   Data Access Layer:  SQLiteDatabase, StockAPIClient
 *   Repository Layer:   UserRepository, StockRepository, SavedStockRepository
 *   Business Services:  UserService, PriceService, StockAnalysisService,
 *                       SavedStockService, ExportService, UpdateService
 *
 * SOLID — Dependency Inversion: this is the only place where concrete
 *         classes are wired together. Everything else depends on interfaces.
 * SOLID — Single Responsibility: wiring only — no business logic here.
 */
public class AppContainer {

    // ── Data layer ────────────────────────────────────────────────────────────
    public final SQLiteDatabase  database;
    public final StockAPIClient  stockAPIClient;

    // ── Repository layer ──────────────────────────────────────────────────────
    public final IUserRepository        userRepository;
    public final IStockRepository       stockRepository;
    public final ISavedStockRepository  savedStockRepository;

    // ── Service layer (Business Services in your architecture diagram) ────────
    public final IAccountService        userService;
    public final IStockService          priceService;
    public final IStockAnalysisService  analysisService;
    public final ISavedStockService     savedStockService;
    public final IExportService         exportService;
    public final IUpdateStockData       updateService;

    public AppContainer() throws SQLException {
        // Data layer
        this.database       = SQLiteDatabase.getInstance();
        this.stockAPIClient = new StockAPIClient();

        // Repository layer — each depends on interfaces above
        this.userRepository      = new UserRepository(database);
        this.stockRepository     = new StockRepository(database);
        this.savedStockRepository = new SavedStockRepository(database, stockRepository);

        // Service layer — each depends on repository interfaces
        this.userService      = new UserService(userRepository);
        this.priceService     = new PriceService(stockAPIClient, stockRepository);
        this.analysisService  = new StockAnalysisService(priceService);
        this.savedStockService = new SavedStockService(savedStockRepository);
        this.exportService    = new ExportService();
        this.updateService    = new UpdateService(priceService);

        System.out.println("[AppContainer] All services wired successfully.");
    }

    public void shutdown() {
        database.close();
        System.out.println("[AppContainer] Shutdown complete.");
    }
}
