package com.stockcompare.presentation.components;

import com.stockcompare.domain.interfaces.IExportService;
import com.stockcompare.domain.interfaces.IStockService;
import com.stockcompare.domain.model.PriceData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

/**
 * ExportPanel — compound component for UC12 (Export Price Data).
 *
 * Sprint 3 — shows a price data table and line graph preview
 * before allowing the user to export to CSV or JSON.
 *
 * Architecture:
 *   - Pipes & Filters: fetch → display table + graph → export
 *   - Adapter: IExportService adapts PriceData → file output
 * SOA: depends on IExportService and IStockService interfaces only.
 */
public class ExportPanel {

    private final IExportService exportService;
    private final IStockService  stockService;
    private final VBox root;

    private final TextField    symbolField = new TextField();
    private final DatePicker   startDate   = new DatePicker(LocalDate.now().minusMonths(1));
    private final DatePicker   endDate     = new DatePicker(LocalDate.now());
    private final ComboBox<String> formatBox = new ComboBox<>();
    private final Label        statusLabel = new Label();

    // Preview table
    private final TableView<PriceData> previewTable = new TableView<>();

    // Preview chart
    private final LineChart<String, Number> previewChart;

    // Last fetched data
    private List<PriceData> lastData = List.of();

    public ExportPanel(IExportService exportService, IStockService stockService) {
        this.exportService = exportService;
        this.stockService  = stockService;
        this.previewChart  = buildChart();
        this.root          = buildRoot();
    }

    private VBox buildRoot() {
        VBox container = new VBox(14);
        container.getStyleClass().add("panel");
        container.setPadding(new Insets(24));

        Label title = new Label("Export Data");
        title.getStyleClass().add("panel-title");

        // ── Form ──────────────────────────────────────────────────────────────
        symbolField.setPromptText("Stock symbol (e.g. AAPL)");
        formatBox.getItems().addAll("CSV", "JSON", "XLSX");
        formatBox.setValue("CSV");

        Button fetchBtn = new Button("Fetch & Preview");
        fetchBtn.getStyleClass().add("primary-button");
        fetchBtn.setOnAction(e -> handleFetch());

        Button exportBtn = new Button("Export");
        exportBtn.getStyleClass().add("secondary-button");
        exportBtn.setOnAction(e -> handleExport());

        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(10);
        form.add(new Label("Symbol:"),  0, 0);
        form.add(symbolField,            1, 0);
        form.add(new Label("From:"),    0, 1);
        form.add(startDate,              1, 1);
        form.add(new Label("To:"),      0, 2);
        form.add(endDate,                1, 2);
        form.add(new Label("Format:"),  0, 3);
        form.add(formatBox,              1, 3);

        HBox btnRow = new HBox(10, fetchBtn, exportBtn);
        btnRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // ── Preview Table ─────────────────────────────────────────────────────
        buildPreviewTable();
        previewTable.setPrefHeight(180);
        previewTable.setPlaceholder(new Label("Enter a symbol and click Fetch & Preview"));

        // ── Preview Chart ─────────────────────────────────────────────────────
        previewChart.setPrefHeight(220);

        statusLabel.getStyleClass().add("status-label");

        container.getChildren().addAll(
            title, form, btnRow,
            new Label("Price Data Preview:"),
            previewTable,
            new Label("Price Graph:"),
            previewChart,
            statusLabel
        );
        return container;
    }

    // ── Preview Table ─────────────────────────────────────────────────────────

    private void buildPreviewTable() {
        TableColumn<PriceData, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDate().toString()));
        dateCol.setPrefWidth(100);

        TableColumn<PriceData, String> openCol = new TableColumn<>("Open");
        openCol.setCellValueFactory(d -> new SimpleStringProperty(String.format("%.2f", d.getValue().getOpen())));

        TableColumn<PriceData, String> highCol = new TableColumn<>("High");
        highCol.setCellValueFactory(d -> new SimpleStringProperty(String.format("%.2f", d.getValue().getHigh())));

        TableColumn<PriceData, String> lowCol = new TableColumn<>("Low");
        lowCol.setCellValueFactory(d -> new SimpleStringProperty(String.format("%.2f", d.getValue().getLow())));

        TableColumn<PriceData, String> closeCol = new TableColumn<>("Close");
        closeCol.setCellValueFactory(d -> new SimpleStringProperty(String.format("%.2f", d.getValue().getClose())));

        TableColumn<PriceData, String> volCol = new TableColumn<>("Volume");
        volCol.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getVolume())));

        previewTable.getColumns().addAll(dateCol, openCol, highCol, lowCol, closeCol, volCol);
    }

    // ── Preview Chart ─────────────────────────────────────────────────────────

    private LineChart<String, Number> buildChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Close Price");

        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setAnimated(false);
        chart.setCreateSymbols(false);
        chart.setTitle("Price History");
        return chart;
    }

    private void updateChart(String symbol, List<PriceData> data) {
        previewChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(symbol + " Close Price");

        int step = Math.max(1, data.size() / 60);
        for (int i = 0; i < data.size(); i += step) {
            PriceData pd = data.get(i);
            series.getData().add(new XYChart.Data<>(pd.getDate().toString(), pd.getClose()));
        }

        previewChart.getData().add(series);
        previewChart.setTitle(symbol + " — Price History");
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    private void handleFetch() {
        String symbol = symbolField.getText().trim().toUpperCase();
        if (symbol.isEmpty()) {
            statusLabel.setText("Please enter a symbol.");
            return;
        }
        try {
            List<PriceData> data = stockService.getPriceData(symbol, startDate.getValue(), endDate.getValue());
            if (data.isEmpty()) {
                statusLabel.setText("No data found for " + symbol);
                return;
            }
            lastData = data;
            previewTable.setItems(FXCollections.observableArrayList(data));
            updateChart(symbol, data);
            statusLabel.setText("Loaded " + data.size() + " records for " + symbol + ". Ready to export.");
        } catch (Exception ex) {
            statusLabel.setText("Error: " + ex.getMessage());
        }
    }

    private void handleExport() {
        if (lastData.isEmpty()) {
            statusLabel.setText("Fetch data first before exporting.");
            return;
        }
        String format = formatBox.getValue();
        try {
            File file = exportService.generateExportFile(lastData, format.toLowerCase());
            if (file == null) {
                statusLabel.setText("Failed to generate file.");
                return;
            }
            boolean success = exportService.exportPriceData(file);
            if (success) {
                statusLabel.setText("Exported " + lastData.size() + " records to: " + file.getAbsolutePath());
            } else {
                statusLabel.setText("Export failed.");
            }
        } catch (Exception ex) {
            statusLabel.setText("Error: " + ex.getMessage());
        }
    }

    public VBox getRoot() { return root; }
}
