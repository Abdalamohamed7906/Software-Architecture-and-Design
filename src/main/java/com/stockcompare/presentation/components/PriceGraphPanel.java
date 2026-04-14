package com.stockcompare.presentation.components;

import com.stockcompare.domain.interfaces.IStockAnalysisService;
import com.stockcompare.domain.model.PriceData;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.util.List;

/**
 * PriceGraphPanel — compound component for UC8 (View Share Price Graph).
 *
 * Compound Component: contains a date range picker + JavaFX LineChart,
 * all working together as one self-contained reusable unit.
 *
 * Pipes and Filters applied:
 *   Symbol + DateRange → [IStockAnalysisService] → PriceData list
 *   → [Filter: map to chart series] → LineChart
 *
 * Depends only on IStockAnalysisService (Interface Segregation).
 */
public class PriceGraphPanel {

    private final VBox root;
    private final IStockAnalysisService analysisService;

    private String      currentSymbol = null;
    private DatePicker  startPicker;
    private DatePicker  endPicker;
    private LineChart<String, Number> chart;
    private Label       statusLabel;
    private Label       symbolLabel;

    public PriceGraphPanel(IStockAnalysisService analysisService) {
        this.analysisService = analysisService;

        root = new VBox(16);
        root.getStyleClass().add("panel");
        root.setPadding(new Insets(30));
        buildUI();
    }

    private void buildUI() {
        Label title = new Label("Price Graph");
        title.getStyleClass().add("panel-title");

        symbolLabel = new Label("No symbol selected — use Search to pick a stock.");
        symbolLabel.getStyleClass().add("panel-subtitle");

        // ── Date range controls ────────────────────────────────────────────
        HBox controls = new HBox(12);
        controls.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label fromLabel = new Label("From:");
        fromLabel.getStyleClass().add("label");
        startPicker = new DatePicker(LocalDate.now().minusMonths(3));
        startPicker.getStyleClass().add("date-picker");

        Label toLabel = new Label("To:");
        toLabel.getStyleClass().add("label");
        endPicker = new DatePicker(LocalDate.now());
        endPicker.getStyleClass().add("date-picker");

        Button loadBtn = new Button("Load Graph");
        loadBtn.getStyleClass().addAll("btn", "btn-primary");
        loadBtn.setOnAction(e -> loadGraph());

        controls.getChildren().addAll(fromLabel, startPicker, toLabel, endPicker, loadBtn);

        // ── Status label ───────────────────────────────────────────────────
        statusLabel = new Label();
        statusLabel.getStyleClass().add("status-label");

        // ── Line chart ─────────────────────────────────────────────────────
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Price (USD)");

        chart = new LineChart<>(xAxis, yAxis);
        chart.getStyleClass().add("price-chart");
        chart.setTitle("Share Price History");
        chart.setAnimated(false);
        chart.setCreateSymbols(false); // cleaner line without dots on each point
        VBox.setVgrow(chart, Priority.ALWAYS);

        root.getChildren().addAll(title, symbolLabel, controls, statusLabel, chart);
    }

    /**
     * loadSymbol — called by MainWindow when user selects a symbol from Search.
     * Updates the panel's state and auto-loads the graph.
     */
    public void loadSymbol(String symbol) {
        this.currentSymbol = symbol;
        symbolLabel.setText("Symbol: " + symbol);
        loadGraph();
    }

    /**
     * Pipes and Filters — graph loading pipeline:
     * Symbol + dates → service → PriceData → chart series
     */
    private void loadGraph() {
        if (currentSymbol == null) {
            statusLabel.setText("Please select a symbol first.");
            return;
        }

        LocalDate start = startPicker.getValue();
        LocalDate end   = endPicker.getValue();

        if (start == null || end == null || start.isAfter(end)) {
            statusLabel.setText("Invalid date range.");
            return;
        }

        statusLabel.setText("Loading data for " + currentSymbol + "...");

        // Pipe: call service
        List<PriceData> data = analysisService.getPriceDataForGraph(currentSymbol, start, end);

        // Filter: map PriceData to chart series
        chart.getData().clear();

        if (data == null || data.isEmpty()) {
            statusLabel.setText("No data available for this range.");
            return;
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(currentSymbol);

        for (PriceData pd : data) {
            series.getData().add(
                new XYChart.Data<>(pd.getDate().toString(), pd.getClose())
            );
        }

        chart.getData().add(series);
        statusLabel.setText("Showing " + data.size() + " data points.");
    }

    public VBox getRoot() { return root; }
}
