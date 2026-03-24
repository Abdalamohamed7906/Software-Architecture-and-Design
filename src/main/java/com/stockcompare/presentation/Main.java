package com.stockcompare.presentation;

import com.stockcompare.domain.model.*;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Main — console entry point.
 * Covers all 12 use cases from your use case diagram.
 * Clean Architecture — Presentation layer: no business logic here,
 * all calls go through service interfaces via AppContainer.
 */
public class Main {

    private static AppContainer app;
    private static final Scanner sc = new Scanner(System.in);

    // Session state
    private static UserDetail currentUser = null;

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("  StockCompare — Share Price Analysis");
        System.out.println("  Sprint 2 | Clean Architecture + SOLID");
        System.out.println("============================================");

        try {
            app = new AppContainer();
        } catch (Exception e) {
            System.err.println("[FATAL] Could not start app: " + e.getMessage());
            return;
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> app.shutdown()));

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1"  -> createAccount();       // UC1
                case "2"  -> manageAccount();       // UC2
                case "3"  -> searchSymbol();        // UC3
                case "4"  -> retrievePriceData();   // UC4 + UC5
                case "5"  -> viewGraph();           // UC8
                case "6"  -> compareShares();       // UC9
                case "7"  -> saveStock();           // UC6
                case "8"  -> loadSavedStocks();     // UC7
                case "9"  -> deleteSavedStock();    // UC10
                case "10" -> exportData();          // UC12
                case "11" -> updateStockData();     // UC11 (Admin)
                case "0"  -> running = false;
                default   -> System.out.println("Invalid option. Try again.");
            }
        }

        System.out.println("Goodbye!");
    }

    // ── Menu ─────────────────────────────────────────────────────────────────

    private static void printMainMenu() {
        System.out.println("\n────────────────────────────────────────────");
        String userStr = currentUser != null
                ? "Logged in: " + currentUser.username : "Not logged in";
        System.out.println("  " + userStr);
        System.out.println("────────────────────────────────────────────");
        System.out.println(" 1.  Create Account");
        System.out.println(" 2.  Manage Account");
        System.out.println(" 3.  Search Stock Symbol");
        System.out.println(" 4.  Retrieve Share Price Data");
        System.out.println(" 5.  View Share Price Graph (console)");
        System.out.println(" 6.  Compare Share Prices");
        System.out.println(" 7.  Save Stock Data");
        System.out.println(" 8.  Load Saved Stock Data");
        System.out.println(" 9.  Delete Saved Stock");
        System.out.println(" 10. Export Price Data");
        System.out.println(" 11. Update Stored Stock Data (Admin)");
        System.out.println(" 0.  Exit");
        System.out.print("Choice: ");
    }

    // ── UC1: Create Account ───────────────────────────────────────────────────

    private static void createAccount() {
        System.out.println("\n[UC1] Create Account");
        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine().trim();
        try {
            UserDetail user = app.userService.createAccount(username, email, password);
            currentUser = user;
            System.out.println("✅ Account created! Welcome, " + user.username);
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ── UC2: Manage Account ───────────────────────────────────────────────────

    private static void manageAccount() {
        System.out.println("\n[UC2] Manage Account");
        if (currentUser == null) { System.out.println("Please log in first (create an account)."); return; }

        System.out.println("Current account: " + currentUser);
        System.out.print("New email (press Enter to keep '" + currentUser.email + "'): ");
        String newEmail = sc.nextLine().trim();
        System.out.print("New password (press Enter to keep): ");
        String newPass = sc.nextLine().trim();

        if (!newEmail.isBlank()) currentUser.email = newEmail;
        if (!newPass.isBlank())  currentUser.passwordHash = newPass; // will be re-hashed in service

        try {
            boolean ok = app.userService.updateAccountDetails(currentUser);
            System.out.println(ok ? "✅ Account updated." : "❌ Update failed.");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ── UC3: Search Stock Symbol ──────────────────────────────────────────────

    private static void searchSymbol() {
        System.out.println("\n[UC3] Search Stock Symbol");
        System.out.print("Enter company name or symbol: ");
        String query = sc.nextLine().trim();
        try {
            List<ShareDetail> results = app.priceService.searchShareSymbol(query);
            if (results.isEmpty()) {
                System.out.println("No results found for: " + query);
            } else {
                System.out.println("\nResults:");
                results.forEach(s -> System.out.println("  " + s));
            }
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ── UC4 + UC5: Select Date Range + Retrieve Share Price Data ─────────────

    private static List<PriceData> lastFetchedData = new ArrayList<>();
    private static String lastFetchedSymbol = "";

    private static void retrievePriceData() {
        System.out.println("\n[UC4+UC5] Select Date Range & Retrieve Share Price Data");
        System.out.print("Symbol (e.g. AAPL): ");
        String symbol = sc.nextLine().trim().toUpperCase();

        LocalDate start = readDate("Start date (YYYY-MM-DD, default 6 months ago): ",
                LocalDate.now().minusMonths(6));
        LocalDate end   = readDate("End date (YYYY-MM-DD, default today): ",
                LocalDate.now());

        if (!app.priceService.validateDateRange(start, end)) {
            System.out.println("❌ Invalid date range. Max 2 years, start must be before end.");
            return;
        }

        try {
            List<PriceData> data = app.priceService.getPriceData(symbol, start, end);
            lastFetchedData   = data;
            lastFetchedSymbol = symbol;
            if (data.isEmpty()) {
                System.out.println("No data found for " + symbol);
                return;
            }
            System.out.println("\nPrice data for " + symbol + " (" + data.size() + " records):");
            // Show first 5 and last 5
            int show = Math.min(5, data.size());
            for (int i = 0; i < show; i++)
                System.out.println("  " + data.get(i));
            if (data.size() > 10) System.out.println("  ... (" + (data.size()-10) + " more rows)");
            for (int i = Math.max(show, data.size()-5); i < data.size(); i++)
                System.out.println("  " + data.get(i));
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ── UC8: View Share Price Graph (ASCII) ───────────────────────────────────

    private static void viewGraph() {
        System.out.println("\n[UC8] View Share Price Graph");
        System.out.print("Symbol (e.g. TSLA, or press Enter to use last fetched '"
                + lastFetchedSymbol + "'): ");
        String input = sc.nextLine().trim();
        String symbol = input.isBlank() ? lastFetchedSymbol : input.toUpperCase();

        if (symbol.isBlank()) { System.out.println("No symbol. Fetch data first."); return; }

        LocalDate start = readDate("Start date: ", LocalDate.now().minusMonths(3));
        LocalDate end   = readDate("End date:   ", LocalDate.now());

        try {
            List<PriceData> data = app.analysisService.getPriceDataForGraph(symbol, start, end);
            if (data.isEmpty()) { System.out.println("No data available."); return; }
            printAsciiGraph(symbol, data);
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ── UC9: Compare Share Prices ─────────────────────────────────────────────

    private static void compareShares() {
        System.out.println("\n[UC9] Compare Share Prices");
        System.out.print("Symbol 1 (e.g. AAPL): ");
        String sym1 = sc.nextLine().trim().toUpperCase();
        System.out.print("Symbol 2 (e.g. MSFT): ");
        String sym2 = sc.nextLine().trim().toUpperCase();

        LocalDate start = readDate("Start date: ", LocalDate.now().minusMonths(3));
        LocalDate end   = readDate("End date:   ", LocalDate.now());

        try {
            List<List<PriceData>> results = app.analysisService.compareShares(
                    List.of(sym1, sym2), start, end);

            System.out.println("\nComparison: " + sym1 + " vs " + sym2);
            System.out.printf("%-8s  %-14s  %-10s  %-10s%n",
                    "Symbol", "Date", "Close", "Change");
            System.out.println("-".repeat(50));

            for (List<PriceData> series : results) {
                if (series.isEmpty()) continue;
                PriceData first = series.get(0);
                PriceData last  = series.get(series.size()-1);
                double change   = last.getClose() - first.getClose();
                double pct      = (change / first.getClose()) * 100;
                System.out.printf("%-8s  %-14s  %10.2f  %+.2f (%.2f%%)%n",
                        last.getSymbol(), last.getDate(),
                        last.getClose(), change, pct);
            }
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ── UC6: Save Stock Data ──────────────────────────────────────────────────

    private static void saveStock() {
        System.out.println("\n[UC6] Save Stock Data");
        if (currentUser == null) { System.out.println("Please log in first."); return; }
        if (lastFetchedData.isEmpty()) {
            System.out.println("No data to save. Please retrieve price data first (option 4).");
            return;
        }
        System.out.println("Saving " + lastFetchedSymbol + " (" + lastFetchedData.size() + " records)...");
        try {
            PriceData first = lastFetchedData.get(0);
            PriceData last  = lastFetchedData.get(lastFetchedData.size()-1);
            SavedStock ss = app.savedStockService.saveStockData(
                    currentUser.userId, lastFetchedSymbol,
                    first.getDate(), last.getDate(), lastFetchedData);
            System.out.println("✅ Saved: " + ss);
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ── UC7: Load Saved Stock Data ────────────────────────────────────────────

    private static void loadSavedStocks() {
        System.out.println("\n[UC7] Load Saved Stock Data");
        if (currentUser == null) { System.out.println("Please log in first."); return; }
        try {
            List<SavedStock> stocks = app.savedStockService.getSavedStocks(currentUser.userId);
            if (stocks.isEmpty()) { System.out.println("No saved stocks found."); return; }
            System.out.println("\nSaved stocks:");
            for (int i = 0; i < stocks.size(); i++)
                System.out.println("  " + (i+1) + ". " + stocks.get(i));

            System.out.print("Enter number to load (0 to cancel): ");
            int choice = readInt();
            if (choice < 1 || choice > stocks.size()) return;

            SavedStock selected = stocks.get(choice-1);
            List<PriceData> data = app.savedStockService.loadStockData(selected.getSavedStockId());
            lastFetchedData   = data;
            lastFetchedSymbol = selected.getSymbol();
            System.out.println("✅ Loaded " + data.size() + " records for " + selected.getSymbol());
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ── UC10: Delete Saved Stock ──────────────────────────────────────────────

    private static void deleteSavedStock() {
        System.out.println("\n[UC10] Delete Saved Stock");
        if (currentUser == null) { System.out.println("Please log in first."); return; }
        try {
            List<SavedStock> stocks = app.savedStockService.getSavedStocks(currentUser.userId);
            if (stocks.isEmpty()) { System.out.println("No saved stocks to delete."); return; }
            System.out.println("\nSaved stocks:");
            for (int i = 0; i < stocks.size(); i++)
                System.out.println("  " + (i+1) + ". " + stocks.get(i));

            System.out.print("Enter number to delete (0 to cancel): ");
            int choice = readInt();
            if (choice < 1 || choice > stocks.size()) return;

            SavedStock selected = stocks.get(choice-1);
            System.out.print("Confirm delete '" + selected.getSymbol() + "'? (y/n): ");
            if (!sc.nextLine().trim().equalsIgnoreCase("y")) {
                System.out.println("Cancelled.");
                return;
            }
            boolean ok = app.savedStockService.deleteStock(selected.getSavedStockId());
            System.out.println(ok ? "✅ Deleted." : "❌ Delete failed.");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ── UC12: Export Price Data ───────────────────────────────────────────────

    private static void exportData() {
        System.out.println("\n[UC12] Export Price Data");
        if (lastFetchedData.isEmpty()) {
            System.out.println("No data to export. Retrieve or load stock data first.");
            return;
        }
        System.out.print("Format (csv / json): ");
        String format = sc.nextLine().trim();
        try {
            File file = app.exportService.generateExportFile(lastFetchedData, format);
            boolean ready = app.exportService.exportPriceData(file);
            if (ready)
                System.out.println("✅ Exported to: " + file.getAbsolutePath());
            else
                System.out.println("❌ Export failed.");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ── UC11: Update Stored Stock Data (Admin) ────────────────────────────────

    private static void updateStockData() {
        System.out.println("\n[UC11] Update Stored Stock Data (Admin)");
        System.out.print("Enter symbol to refresh (e.g. AAPL): ");
        String symbol = sc.nextLine().trim().toUpperCase();
        if (symbol.isBlank()) { System.out.println("Symbol required."); return; }
        try {
            boolean ok = app.updateService.updateStoredStockData(List.of(symbol));
            System.out.println(ok ? "✅ " + symbol + " data refreshed." : "❌ Refresh failed.");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static void printAsciiGraph(String symbol, List<PriceData> data) {
        int width  = 60;
        int height = 15;
        double min = data.stream().mapToDouble(PriceData::getClose).min().orElse(0);
        double max = data.stream().mapToDouble(PriceData::getClose).max().orElse(1);
        double range = max - min == 0 ? 1 : max - min;

        int step = Math.max(1, data.size() / width);
        double[] sampled = new double[width];
        for (int i = 0; i < width; i++) {
            int idx = Math.min(i * step, data.size() - 1);
            sampled[i] = data.get(idx).getClose();
        }

        System.out.println("\n  " + symbol + " Close Price Chart");
        System.out.printf("  Max: %.2f%n", max);
        char[][] grid = new char[height][width];
        for (char[] row : grid) Arrays.fill(row, ' ');

        for (int x = 0; x < width; x++) {
            int y = (int) ((sampled[x] - min) / range * (height - 1));
            y = height - 1 - y;
            grid[y][x] = '█';
        }

        for (char[] row : grid) {
            System.out.print("  |");
            System.out.println(new String(row));
        }
        System.out.println("  └" + "─".repeat(width));
        System.out.printf("  Min: %.2f%n", min);
        System.out.println("  Period: " + data.get(0).getDate()
                + " → " + data.get(data.size()-1).getDate());
    }

    private static LocalDate readDate(String prompt, LocalDate defaultVal) {
        System.out.print(prompt);
        String input = sc.nextLine().trim();
        if (input.isBlank()) return defaultVal;
        try {
            return LocalDate.parse(input);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date, using default: " + defaultVal);
            return defaultVal;
        }
    }

    private static int readInt() {
        try {
            int val = Integer.parseInt(sc.nextLine().trim());
            return val;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
