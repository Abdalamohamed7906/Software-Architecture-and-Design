package com.stockcompare.presentation.components;

import com.stockcompare.domain.interfaces.IStockAnalysisService;
import com.stockcompare.domain.interfaces.IStockService;
import com.stockcompare.domain.interfaces.ISavedStockService;
import com.stockcompare.domain.model.PriceData;
import com.stockcompare.domain.model.ShareDetail;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDate;
import java.util.List;

/**
 * SearchPanel — compound component for UC3, UC4, UC6, UC8.
 * (Search, Fetch Prices, Save Stock, View Graph)
 *
 * Sprint 3 — Compound Component.
 * Contains: search bar, results table, date range picker,
 *           price data table, save button, and price graph.
 *
 * Architecture:
 *   - Pipes & Filters: input → search → select → fetch → display table + graph
 *   - MVC: View component; delegates to service interfaces
 * SOA: depends on IStockService, IStockAnalysisService, ISavedStockService only.
 */
public class SearchPanel {

    private final IStockService         stockService;
    private final IStockAnalysisService analysisService;
    private final ISavedStockService    savedStockService;
    private final VBox root;

    private String currentUserId = null;

    private final TextField                  searchField  = new TextField();
    private final TableView<ShareDetail>     resultsTable = new TableView<>();
    private final DatePicker                 startDate    = new DatePicker(LocalDate.now().minusMonths(3));
    private final DatePicker                 endDate      = new DatePicker(LocalDate.now());
    private final TableView<PriceData>       priceTable   = new TableView<>();
    private final LineChart<String, Number>  priceChart;
    private final Label                      statusLabel  = new Label();

    private List<PriceData> lastFetchedPrices = List.of();
    private String          lastFetchedSymbol = "";

    public SearchPanel(IStockService stockService,
                       IStockAnalysisService analysisService,
                       ISavedStockService savedStockService) {
        this.stockService      = stockService;
        this.analysisService   = analysisService;
        this.savedStockService = savedStockService;
        this.priceChart        = buildChart();
        this.root              = buildRoot();
    }

    public void setCurrentUserId(String userId) {
        this.currentUserId = userId;
    }

    private VBox buildRoot() {
        VBox container = new VBox(16);
        container.getStyleClass().add("panel");
        container.setPadding(new Insets(24));

        Label title = new Label("Search Stocks");
        title.getStyleClass().add("panel-title");

        container.getChildren().addAll(
            title,
            buildSearchBar(),
            buildResultsTable(),
            buildDateRangeRow(),
            buildPriceTable(),
            buildSaveRow(),
            priceChart,
            statusLabel
        );
        return container;
    }

    // ── Search Bar ───────────────────────────────────────────────────────────

    private HBox buildSearchBar() {
        searchField.setPromptText("Enter symbol or company name (e.g. AAPL)");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        Button searchBtn = new Button("Search");
        searchBtn.getStyleClass().add("primary-button");
        searchBtn.setOnAction(e -> handleSearch());
        searchField.setOnAction(e -> handleSearch());

        return new HBox(8, searchField, searchBtn);
    }

    private void handleSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) return;
        try {
            List<ShareDetail> results = stockService.searchShareSymbol(query);
            resultsTable.setItems(FXCollections.observableArrayList(results));
            statusLabel.setText(results.size() + " result(s) found.");
        } catch (Exception ex) {
            statusLabel.setText("Search failed: " + ex.getMessage());
        }
    }

    // ── Results Table ────────────────────────────────────────────────────────

    private TableView<ShareDetail> buildResultsTable() {
        TableColumn<ShareDetail, String> symCol = new TableColumn<>("Symbol");
        symCol.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().symbol));
        symCol.setPrefWidth(100);

        TableColumn<ShareDetail, String> nameCol = new TableColumn<>("Company Name");
        nameCol.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().companyName));
        nameCol.setPrefWidth(300);

        TableColumn<ShareDetail, String> exchCol = new TableColumn<>("Exchange");
        exchCol.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().exchange));
        exchCol.setPrefWidth(120);

        resultsTable.getColumns().addAll(symCol, nameCol, exchCol);
        resultsTable.setPrefHeight(150);
        resultsTable.setPlaceholder(new Label("Search for a stock above"));
        return resultsTable;
    }

    // ── Date Range + Fetch ───────────────────────────────────────────────────

    private HBox buildDateRangeRow() {
        Button fetchBtn = new Button("Fetch Price Data");
        fetchBtn.getStyleClass().add("primary-button");
        fetchBtn.setOnAction(e -> handleFetchPrices());

        HBox row = new HBox(12,
            new Label("From:"), startDate,
            new Label("To:"),   endDate,
            fetchBtn
        );
        row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        return row;
    }

    private void handleFetchPrices() {
        ShareDetail selected = resultsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            statusLabel.setText("Please select a stock from the results first.");
            return;
        }
        try {
            List<PriceData> prices = stockService.getPriceData(
                selected.symbol, startDate.getValue(), endDate.getValue()
            );
            lastFetchedPrices = prices;
            lastFetchedSymbol = selected.symbol;

            priceTable.setItems(FXCollections.observableArrayList(prices));
            updateChart(selected.symbol, prices);

            statusLabel.setText("Loaded " + prices.size() + " price records for " + selected.symbol);
        } catch (Exception ex) {
            statusLabel.setText("Error fetching prices: " + ex.getMessage());
        }
    }

    // ── Price Table ──────────────────────────────────────────────────────────

    private TableView<PriceData> buildPriceTable() {
        TableColumn<PriceData, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(data ->
            new SimpleStringProperty(data.getValue().getDate().toString()));
        dateCol.setPrefWidth(100);

        TableColumn<PriceData, String> openCol = new TableColumn<>("Open");
        openCol.setCellValueFactory(data ->
            new SimpleStringProperty(String.format("%.2f", data.getValue().getOpen())));

        TableColumn<PriceData, String> highCol = new TableColumn<>("High");
        highCol.setCellValueFactory(data ->
            new SimpleStringProperty(String.format("%.2f", data.getValue().getHigh())));

        TableColumn<PriceData, String> lowCol = new TableColumn<>("Low");
        lowCol.setCellValueFactory(data ->
            new SimpleStringProperty(String.format("%.2f", data.getValue().getLow())));

        TableColumn<PriceData, String> closeCol = new TableColumn<>("Close");
        closeCol.setCellValueFactory(data ->
            new SimpleStringProperty(String.format("%.2f", data.getValue().getClose())));

        TableColumn<PriceData, String> volCol = new TableColumn<>("Volume");
        volCol.setCellValueFactory(data ->
            new SimpleStringProperty(String.valueOf(data.getValue().getVolume())));

        priceTable.getColumns().addAll(dateCol, openCol, highCol, lowCol, closeCol, volCol);
        priceTable.setPrefHeight(180);
        priceTable.setPlaceholder(new Label("Select a stock and date range, then click Fetch"));
        return priceTable;
    }

    // ── Save Stock Button ────────────────────────────────────────────────────

    private HBox buildSaveRow() {
        Button saveBtn = new Button("Save This Stock");
        saveBtn.getStyleClass().add("secondary-button");
        saveBtn.setOnAction(e -> handleSave());

        HBox row = new HBox(saveBtn);
        row.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        return row;
    }

    private void handleSave() {
        if (lastFetchedPrices.isEmpty()) {
            statusLabel.setText("Fetch price data first before saving.");
            return;
        }
        if (currentUserId == null || currentUserId.isBlank()) {
            statusLabel.setText("Please log in to save stocks.");
            return;
        }
        try {
            savedStockService.saveStockData(
                currentUserId,
                lastFetchedSymbol,
                startDate.getValue(),
                endDate.getValue(),
                lastFetchedPrices
            );
            statusLabel.setText("✅ " + lastFetchedSymbol + " saved successfully!");
        } catch (Exception ex) {
            statusLabel.setText("Save failed: " + ex.getMessage());
        }
    }

    // ── Price Graph ──────────────────────────────────────────────────────────

    private LineChart<String, Number> buildChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Close Price (£)");

        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Share Price History");
        chart.setPrefHeight(250);
        chart.setAnimated(false);
        chart.setCreateSymbols(false);
        return chart;
    }

    private void updateChart(String symbol, List<PriceData> prices) {
        priceChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(symbol + " Close Price");

        int step = Math.max(1, prices.size() / 60);
        for (int i = 0; i < prices.size(); i += step) {
            PriceData pd = prices.get(i);
            series.getData().add(new XYChart.Data<>(
                pd.getDate().toString(), pd.getClose()
            ));
        }

        priceChart.getData().add(series);
        priceChart.setTitle(symbol + " — Price History");
    }

    public VBox getRoot() { return root; }
}
