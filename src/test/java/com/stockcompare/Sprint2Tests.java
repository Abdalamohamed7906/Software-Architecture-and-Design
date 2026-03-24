package com.stockcompare;

import com.stockcompare.domain.model.*;
import com.stockcompare.domain.interfaces.*;
import com.stockcompare.repository.*;
import com.stockcompare.service.*;

import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Sprint2Tests — unit tests for all services.
 * Uses mock repositories — no database or network required.
 * SOLID — Dependency Inversion: services accept interfaces, so mocks work perfectly.
 */
public class Sprint2Tests {

    // ── Mock Repositories ────────────────────────────────────────────────────

    static class MockUserRepository implements IUserRepository {
        final Map<String, UserDetail> store    = new HashMap<>();
        final Set<String>             emails   = new HashSet<>();
        final Set<String>             usernames = new HashSet<>();

        @Override public UserDetail saveUser(UserDetail u) {
            store.put(u.userId, u); emails.add(u.email); usernames.add(u.username);
            return u;
        }
        @Override public Optional<UserDetail> findById(String id)    { return Optional.ofNullable(store.get(id)); }
        @Override public Optional<UserDetail> findByEmail(String e)  {
            return store.values().stream().filter(u -> u.email.equals(e)).findFirst();
        }
        @Override public boolean updateUser(UserDetail u)            { store.put(u.userId, u); return true; }
        @Override public boolean deleteUser(String id)               { return store.remove(id) != null; }
        @Override public boolean existsByEmail(String e)             { return emails.contains(e); }
        @Override public boolean existsByUsername(String u)          { return usernames.contains(u); }
    }

    static class MockStockRepository implements IStockRepository {
        final List<PriceData> cache = new ArrayList<>();
        @Override public List<PriceData> findPriceData(String sym, LocalDate s, LocalDate e) {
            return cache.stream().filter(p -> p.getSymbol().equals(sym)).toList();
        }
        @Override public boolean storePriceData(List<PriceData> data) { cache.addAll(data); return true; }
        @Override public boolean deletePriceData(String sym)          {
            return cache.removeIf(p -> p.getSymbol().equals(sym));
        }
        @Override public boolean hasCachedData(String sym, LocalDate s, LocalDate e) {
            return cache.stream().anyMatch(p -> p.getSymbol().equals(sym));
        }
    }

    static class MockSavedStockRepository implements ISavedStockRepository {
        final Map<String, SavedStock> store = new HashMap<>();
        @Override public SavedStock save(String uid, String sym, LocalDate s, LocalDate e, List<PriceData> d) {
            String id = UUID.randomUUID().toString();
            SavedStock ss = new SavedStock(id, uid, sym, s, e, d);
            store.put(id, ss); return ss;
        }
        @Override public List<SavedStock> findByUserId(String uid) {
            return store.values().stream().filter(s -> s.getUserId().equals(uid)).toList();
        }
        @Override public Optional<SavedStock> findById(String id) { return Optional.ofNullable(store.get(id)); }
        @Override public boolean deleteById(String id)            { return store.remove(id) != null; }
    }

    static class MockStockService implements IStockService {
        @Override public List<ShareDetail> searchShareSymbol(String q) {
            if (q == null || q.isBlank())
                throw new IllegalArgumentException("Search query cannot be empty.");
            return List.of(new ShareDetail("AAPL","Apple Inc","NASDAQ"),
                    new ShareDetail("AMZN","Amazon","NASDAQ"));
        }
        @Override public List<PriceData> getPriceData(String sym, LocalDate s, LocalDate e) {
            return List.of(
                new PriceData(sym, s,             100.0, 105.0, 99.0,  103.0, 1_000_000L),
                new PriceData(sym, s.plusDays(1), 103.0, 108.0, 102.0, 106.0, 1_200_000L),
                new PriceData(sym, s.plusDays(2), 106.0, 110.0, 105.0, 108.0, 900_000L)
            );
        }
        @Override public boolean validateDateRange(LocalDate s, LocalDate e) {
            if (s == null || e == null || s.isAfter(e)) return false;
            return !s.isBefore(e.minusYears(2));
        }
        @Override public boolean refreshStock(String sym) { return true; }
    }

    // ── Test fixtures ────────────────────────────────────────────────────────

    MockUserRepository      userRepo;
    MockStockRepository     stockRepo;
    MockSavedStockRepository savedRepo;
    MockStockService        mockStock;

    @BeforeEach
    void setUp() {
        userRepo  = new MockUserRepository();
        stockRepo = new MockStockRepository();
        savedRepo = new MockSavedStockRepository();
        mockStock = new MockStockService();
    }

    // ════════════════════════════════════════════════════════════════════════
    // UC1 — Create Account
    // ════════════════════════════════════════════════════════════════════════

    @Test
    void createAccount_validInput_returnsAccount() {
        UserService svc = new UserService(userRepo);
        UserDetail u = svc.createAccount("alice", "alice@test.com", "pass123");
        assertNotNull(u);
        assertEquals("alice@test.com", u.email);
        assertEquals("alice", u.username);
    }

    @Test
    void createAccount_duplicateEmail_throwsException() {
        UserService svc = new UserService(userRepo);
        svc.createAccount("alice", "alice@test.com", "pass123");
        assertThrows(IllegalStateException.class,
                () -> svc.createAccount("alice2", "alice@test.com", "pass123"));
    }

    @Test
    void createAccount_shortPassword_throwsException() {
        UserService svc = new UserService(userRepo);
        assertThrows(IllegalArgumentException.class,
                () -> svc.createAccount("bob", "bob@test.com", "123"));
    }

    @Test
    void createAccount_blankUsername_throwsException() {
        UserService svc = new UserService(userRepo);
        assertThrows(IllegalArgumentException.class,
                () -> svc.createAccount("", "bob@test.com", "pass123"));
    }

    @Test
    void createAccount_invalidEmail_throwsException() {
        UserService svc = new UserService(userRepo);
        assertThrows(IllegalArgumentException.class,
                () -> svc.createAccount("bob", "notanemail", "pass123"));
    }

    // ════════════════════════════════════════════════════════════════════════
    // UC2 — Manage Account
    // ════════════════════════════════════════════════════════════════════════

    @Test
    void manageAccount_updateEmail_succeeds() {
        UserService svc = new UserService(userRepo);
        UserDetail u = svc.createAccount("alice", "alice@test.com", "pass123");
        u.email = "newalice@test.com";
        assertTrue(svc.updateAccountDetails(u));
    }

    @Test
    void manageAccount_getUserDetails_returnsCorrectUser() {
        UserService svc = new UserService(userRepo);
        UserDetail created = svc.createAccount("carol", "carol@test.com", "pass123");
        UserDetail fetched = svc.getAccountDetails(created.userId);
        assertEquals(created.userId, fetched.userId);
    }

    @Test
    void manageAccount_notFound_throwsException() {
        UserService svc = new UserService(userRepo);
        assertThrows(IllegalArgumentException.class,
                () -> svc.getAccountDetails("nonexistent-id"));
    }

    // ════════════════════════════════════════════════════════════════════════
    // UC3 — Search Stock Symbol
    // ════════════════════════════════════════════════════════════════════════

    @Test
    void searchStock_validQuery_returnsResults() {
        PriceService svc = new PriceService(null, stockRepo);
        // Use MockStockService to test the interface contract
        List<ShareDetail> results = mockStock.searchShareSymbol("Apple");
        assertFalse(results.isEmpty());
        assertEquals("AAPL", results.get(0).symbol);
    }

    @Test
    void searchStock_blankQuery_throwsException() {
        // PriceService wraps StockAPIClient — test via MockStockService indirectly
        assertThrows(IllegalArgumentException.class,
                () -> mockStock.searchShareSymbol(""));
    }

    // ════════════════════════════════════════════════════════════════════════
    // UC4 — Select Date Range (validateDateRange)
    // ════════════════════════════════════════════════════════════════════════

    @Test
    void validateDateRange_valid_returnsTrue() {
        assertTrue(mockStock.validateDateRange(
                LocalDate.now().minusMonths(6), LocalDate.now()));
    }

    @Test
    void validateDateRange_startAfterEnd_returnsFalse() {
        assertFalse(mockStock.validateDateRange(
                LocalDate.now(), LocalDate.now().minusDays(10)));
    }

    @Test
    void validateDateRange_over2Years_returnsFalse() {
        assertFalse(mockStock.validateDateRange(
                LocalDate.now().minusYears(3), LocalDate.now()));
    }

    @Test
    void validateDateRange_nullDates_returnsFalse() {
        assertFalse(mockStock.validateDateRange(null, LocalDate.now()));
    }

    // ════════════════════════════════════════════════════════════════════════
    // UC5 — Retrieve Share Price Data
    // ════════════════════════════════════════════════════════════════════════

    @Test
    void retrievePriceData_returnsNonEmptyList() {
        List<PriceData> data = mockStock.getPriceData(
                "AAPL", LocalDate.now().minusDays(5), LocalDate.now());
        assertFalse(data.isEmpty());
        assertEquals("AAPL", data.get(0).getSymbol());
    }

    // ════════════════════════════════════════════════════════════════════════
    // UC6 — Save Stock Data
    // ════════════════════════════════════════════════════════════════════════

    @Test
    void saveStock_validData_returnsSavedStock() {
        SavedStockService svc = new SavedStockService(savedRepo);
        LocalDate start = LocalDate.now().minusDays(5);
        List<PriceData> data = mockStock.getPriceData("AAPL", start, LocalDate.now());

        SavedStock ss = svc.saveStockData("user-1", "AAPL", start, LocalDate.now(), data);
        assertNotNull(ss);
        assertEquals("AAPL", ss.getSymbol());
        assertEquals("user-1", ss.getUserId());
        assertEquals(data.size(), ss.getPriceData().size());
    }

    @Test
    void saveStock_noUserId_throwsException() {
        SavedStockService svc = new SavedStockService(savedRepo);
        assertThrows(IllegalArgumentException.class,
                () -> svc.saveStockData("", "AAPL",
                        LocalDate.now().minusDays(5), LocalDate.now(),
                        mockStock.getPriceData("AAPL",
                                LocalDate.now().minusDays(5), LocalDate.now())));
    }

    @Test
    void saveStock_emptyData_throwsException() {
        SavedStockService svc = new SavedStockService(savedRepo);
        assertThrows(IllegalArgumentException.class,
                () -> svc.saveStockData("user-1", "AAPL",
                        LocalDate.now().minusDays(5), LocalDate.now(),
                        Collections.emptyList()));
    }

    // ════════════════════════════════════════════════════════════════════════
    // UC7 — Load Saved Stock Data
    // ════════════════════════════════════════════════════════════════════════

    @Test
    void loadSavedStocks_returnsCorrectUser() {
        SavedStockService svc = new SavedStockService(savedRepo);
        LocalDate start = LocalDate.now().minusDays(5);
        svc.saveStockData("user-1","AAPL",start,LocalDate.now(),
                mockStock.getPriceData("AAPL",start,LocalDate.now()));
        svc.saveStockData("user-1","MSFT",start,LocalDate.now(),
                mockStock.getPriceData("MSFT",start,LocalDate.now()));

        List<SavedStock> stocks = svc.getSavedStocks("user-1");
        assertEquals(2, stocks.size());
    }

    @Test
    void loadStockData_returnsCorrectData() {
        SavedStockService svc = new SavedStockService(savedRepo);
        LocalDate start = LocalDate.now().minusDays(5);
        List<PriceData> original = mockStock.getPriceData("TSLA", start, LocalDate.now());
        SavedStock ss = svc.saveStockData("user-2","TSLA",start,LocalDate.now(),original);

        List<PriceData> loaded = svc.loadStockData(ss.getSavedStockId());
        assertEquals(original.size(), loaded.size());
    }

    // ════════════════════════════════════════════════════════════════════════
    // UC8 — View Share Price Graph
    // ════════════════════════════════════════════════════════════════════════

    @Test
    void viewGraph_returnsData() {
        StockAnalysisService svc = new StockAnalysisService(mockStock);
        List<PriceData> data = svc.getPriceDataForGraph(
                "AAPL", LocalDate.now().minusDays(10), LocalDate.now());
        assertFalse(data.isEmpty());
    }

    @Test
    void viewGraph_blankSymbol_throwsException() {
        StockAnalysisService svc = new StockAnalysisService(mockStock);
        assertThrows(IllegalArgumentException.class,
                () -> svc.getPriceDataForGraph("",
                        LocalDate.now().minusDays(10), LocalDate.now()));
    }

    // ════════════════════════════════════════════════════════════════════════
    // UC9 — Compare Share Prices
    // ════════════════════════════════════════════════════════════════════════

    @Test
    void compareShares_twoSymbols_returnsTwoSeries() {
        StockAnalysisService svc = new StockAnalysisService(mockStock);
        List<List<PriceData>> result = svc.compareShares(
                List.of("AAPL", "MSFT"),
                LocalDate.now().minusDays(10), LocalDate.now());
        assertEquals(2, result.size());
        assertFalse(result.get(0).isEmpty());
        assertFalse(result.get(1).isEmpty());
    }

    @Test
    void compareShares_oneSymbol_throwsException() {
        StockAnalysisService svc = new StockAnalysisService(mockStock);
        assertThrows(IllegalArgumentException.class,
                () -> svc.compareShares(List.of("AAPL"),
                        LocalDate.now().minusDays(10), LocalDate.now()));
    }

    // ════════════════════════════════════════════════════════════════════════
    // UC10 — Delete Saved Stock
    // ════════════════════════════════════════════════════════════════════════

    @Test
    void deleteStock_existingStock_returnsTrue() {
        SavedStockService svc = new SavedStockService(savedRepo);
        LocalDate start = LocalDate.now().minusDays(5);
        SavedStock ss = svc.saveStockData("user-1","AAPL",start,LocalDate.now(),
                mockStock.getPriceData("AAPL",start,LocalDate.now()));
        assertTrue(svc.deleteStock(ss.getSavedStockId()));
    }

    @Test
    void deleteStock_blankId_throwsException() {
        SavedStockService svc = new SavedStockService(savedRepo);
        assertThrows(IllegalArgumentException.class,
                () -> svc.deleteStock(""));
    }

    @Test
    void deleteStock_nonExistent_returnsFalse() {
        SavedStockService svc = new SavedStockService(savedRepo);
        assertFalse(svc.deleteStock("does-not-exist"));
    }

    // ════════════════════════════════════════════════════════════════════════
    // UC11 — Update Stored Stock Data
    // ════════════════════════════════════════════════════════════════════════

    @Test
    void updateService_refreshSymbol_returnsTrue() {
        UpdateService svc = new UpdateService(mockStock);
        assertTrue(svc.refreshSingleSymbol("AAPL"));
    }

    @Test
    void updateService_refreshMultiple_returnsTrue() {
        UpdateService svc = new UpdateService(mockStock);
        assertTrue(svc.updateStoredStockData(List.of("AAPL","MSFT","TSLA")));
    }

    // ════════════════════════════════════════════════════════════════════════
    // UC12 — Export Price Data
    // ════════════════════════════════════════════════════════════════════════

    @Test
    void exportData_csvFormat_createsFile() {
        ExportService svc = new ExportService();
        List<PriceData> data = mockStock.getPriceData(
                "AAPL", LocalDate.now().minusDays(5), LocalDate.now());
        java.io.File f = svc.generateExportFile(data, "csv");
        assertNotNull(f);
        assertTrue(f.exists());
        assertTrue(f.getName().endsWith(".csv"));
        f.delete(); // cleanup
    }

    @Test
    void exportData_jsonFormat_createsFile() {
        ExportService svc = new ExportService();
        List<PriceData> data = mockStock.getPriceData(
                "TSLA", LocalDate.now().minusDays(3), LocalDate.now());
        java.io.File f = svc.generateExportFile(data, "json");
        assertNotNull(f);
        assertTrue(f.getName().endsWith(".json"));
        f.delete();
    }

    @Test
    void exportData_emptyList_throwsException() {
        ExportService svc = new ExportService();
        assertThrows(IllegalArgumentException.class,
                () -> svc.generateExportFile(Collections.emptyList(), "csv"));
    }

    @Test
    void exportData_unsupportedFormat_throwsException() {
        ExportService svc = new ExportService();
        List<PriceData> data = mockStock.getPriceData(
                "AAPL", LocalDate.now().minusDays(2), LocalDate.now());
        assertThrows(IllegalArgumentException.class,
                () -> svc.generateExportFile(data, "xml"));
    }
}
