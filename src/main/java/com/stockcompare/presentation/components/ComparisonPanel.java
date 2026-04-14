package com.stockcompare.presentation.components;

import com.stockcompare.domain.interfaces.IStockAnalysisService;
import com.stockcompare.domain.model.PriceData;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ComparisonPanel — compound component for UC9 (Compare Share Prices).
 *
 * Sprint 3 — Compound Component.
 * Shows both stocks on the same LineChart for direct visual comparison.
 *
 * Architecture:
 *   - Pipes & Filters: symbols input → split → fetch each → combine → display chart + cards
 *   - Adapter: IStockAnalysisService adapts raw price data into comparison format
 * SOA: depends on IStockAnalysisService interface only.
 */
public class ComparisonPanel {

    private final IStockAnalysisService analysisService;
    private final VBox root;

    private final TextField  symbolsField = new TextField();
    private final DatePicker startDate    = new DatePicker(LocalDate.now().minusMonths(3));
    private final DatePicker endDate      = new DatePicker(LocalDate.now());
    private final VBox       cardsBox     = new VBox(12);
    private final Label      statusLabel  = new Label();
    private final LineChart<String, Number> comparisonChart;

    public ComparisonPanel(IStockAnalysisService analysisService) {
        this.analysisService  = analysisService;
        this.comparisonChart  = buildChart();
        this.root             = buildRoot();
    }

    private VBox buildRoot() {
        VBox container = new VBox(16);
        container.getStyleClass().add("panel");
        container.setPadding(new Insets(24));

        Label title = new Label("Compare Stocks");
        title.getStyleClass().add("panel-title");

        symbolsField.setPromptText("Enter symbols separated by commas (e.g. AAPL, TSLA, MSFT)");

        Button compareBtn = new Button("Compare");
        compareBtn.getStyleClass().add("primary-button");
        compareBtn.setOnAction(e -> handleCompare());

        HBox dateRow = new HBox(12,
            new Label("From:"), startDate,
            new Label("To:"),   endDate,
            compareBtn
        );
        dateRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        ScrollPane scroll = new ScrollPane(cardsBox);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(220);

        container.getChildren().addAll(
            title,
            new Label("Symbols to compare:"),
            symbolsField,
            dateRow,
            statusLabel,
            comparisonChart,
            new Label("Summary:"),
            scroll
        );
        return container;
    }

    // ── Chart ────────────────────────────────────────────────────────────────

    private LineChart<String, Number> buildChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Close Price (£)");

        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Stock Price Comparison");
        chart.setPrefHeight(280);
        chart.setAnimated(false);
        chart.setCreateSymbols(false);
        return chart;
    }

    private void updateChart(List<String> symbols, List<List<PriceData>> results) {
        comparisonChart.getData().clear();

        for (int i = 0; i < symbols.size(); i++) {
            String symbol = symbols.get(i);
            List<PriceData> data = (i < results.size()) ? results.get(i) : List.of();
            if (data.isEmpty()) continue;

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(symbol);

            // Sample points to keep chart readable
            int step = Math.max(1, data.size() / 60);
            for (int j = 0; j < data.size(); j += step) {
                PriceData pd = data.get(j);
                series.getData().add(new XYChart.Data<>(
                    pd.getDate().toString(), pd.getClose()
                ));
            }
            comparisonChart.getData().add(series);
        }

        comparisonChart.setTitle("Comparison: " + String.join(" vs ", symbols));
    }

    // ── Compare ──────────────────────────────────────────────────────────────

    private void handleCompare() {
        String input = symbolsField.getText().trim();
        if (input.isEmpty()) {
            statusLabel.setText("Please enter at least two symbols.");
            return;
        }

        // Pipes & Filters — Filter stage: parse and clean symbols
        List<String> symbols = Arrays.stream(input.split(","))
            .map(String::trim)
            .map(String::toUpperCase)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toList());

        if (symbols.size() < 2) {
            statusLabel.setText("Please enter at least two symbols to compare.");
            return;
        }

        try {
            // Pipes & Filters — Process stage
            List<List<PriceData>> results = analysisService.compareShares(
                symbols, startDate.getValue(), endDate.getValue()
            );

            // Pipes & Filters — Output stage 1: chart
            updateChart(symbols, results);

            // Pipes & Filters — Output stage 2: summary cards
            cardsBox.getChildren().clear();
            for (int i = 0; i < symbols.size(); i++) {
                String symbol = symbols.get(i);
                List<PriceData> data = (i < results.size()) ? results.get(i) : List.of();
                cardsBox.getChildren().add(buildStockSummaryCard(symbol, data));
            }

            statusLabel.setText("Comparison complete for: " + String.join(", ", symbols));
        } catch (Exception ex) {
            statusLabel.setText("Comparison failed: " + ex.getMessage());
        }
    }

    // ── Summary Card ─────────────────────────────────────────────────────────

    private VBox buildStockSummaryCard(String symbol, List<PriceData> data) {
        VBox card = new VBox(6);
        card.getStyleClass().add("stock-card");
        card.setPadding(new Insets(14));

        Label symLabel = new Label(symbol);
        symLabel.getStyleClass().add("stock-card-title");

        if (data.isEmpty()) {
            card.getChildren().addAll(symLabel, new Label("No data available."));
            return card;
        }

        double latestClose = data.get(data.size() - 1).getClose();
        double firstClose  = data.get(0).getClose();
        double change      = latestClose - firstClose;
        double changePct   = (change / firstClose) * 100;
        double high        = data.stream().mapToDouble(PriceData::getHigh).max().orElse(0);
        double low         = data.stream().mapToDouble(PriceData::getLow).min().orElse(0);

        Label closeLabel  = new Label(String.format("Latest Close: £%.2f", latestClose));
        Label changeLabel = new Label(String.format("Change: %+.2f (%+.2f%%)", change, changePct));
        changeLabel.getStyleClass().add(change >= 0 ? "positive" : "negative");
        Label highLabel   = new Label(String.format("Period High: £%.2f", high));
        Label lowLabel    = new Label(String.format("Period Low:  £%.2f", low));
        Label countLabel  = new Label("Data points: " + data.size());

        card.getChildren().addAll(symLabel, closeLabel, changeLabel, highLabel, lowLabel, countLabel);
        return card;
    }

    public VBox getRoot() { return root; }
}
