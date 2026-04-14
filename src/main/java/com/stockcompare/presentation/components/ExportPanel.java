package com.stockcompare.presentation.components;

import com.stockcompare.domain.interfaces.IExportService;
import com.stockcompare.domain.interfaces.IStockService;
import com.stockcompare.domain.model.PriceData;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

/**
 * ExportPanel — compound component for UC12 (Export Price Data).
 *
 * Sprint 3 — Compound Component.
 * Adapter pattern: IExportService adapts PriceData → File output (CSV or JSON).
 * SOA: depends on IExportService and IStockService interfaces only.
 */
public class ExportPanel {

    private final IExportService exportService;
    private final IStockService  stockService;
    private final VBox root;

    private final TextField  symbolField = new TextField();
    private final DatePicker startDate   = new DatePicker(LocalDate.now().minusMonths(1));
    private final DatePicker endDate     = new DatePicker(LocalDate.now());
    private final ComboBox<String> formatBox = new ComboBox<>();
    private final Label      statusLabel = new Label();

    public ExportPanel(IExportService exportService, IStockService stockService) {
        this.exportService = exportService;
        this.stockService  = stockService;
        this.root          = buildRoot();
    }

    private VBox buildRoot() {
        VBox container = new VBox(16);
        container.getStyleClass().add("panel");
        container.setPadding(new Insets(24));

        Label title = new Label("Export Data");
        title.getStyleClass().add("panel-title");

        symbolField.setPromptText("Stock symbol (e.g. AAPL)");

        formatBox.getItems().addAll("CSV", "JSON");
        formatBox.setValue("CSV");

        Button exportBtn = new Button("Export");
        exportBtn.getStyleClass().add("primary-button");
        exportBtn.setOnAction(e -> handleExport());

        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(10);
        form.add(new Label("Symbol:"),      0, 0);
        form.add(symbolField,               1, 0);
        form.add(new Label("From:"),        0, 1);
        form.add(startDate,                 1, 1);
        form.add(new Label("To:"),          0, 2);
        form.add(endDate,                   1, 2);
        form.add(new Label("Format:"),      0, 3);
        form.add(formatBox,                 1, 3);
        form.add(exportBtn,                 1, 4);

        container.getChildren().addAll(title, form, statusLabel);
        return container;
    }

    private void handleExport() {
        String symbol = symbolField.getText().trim().toUpperCase();
        String format = formatBox.getValue();

        if (symbol.isEmpty()) {
            statusLabel.setText("Please enter a symbol.");
            return;
        }

        try {
            // Pipes & Filters:
            // Stage 1 — Fetch
            List<PriceData> data = stockService.getPriceData(
                symbol, startDate.getValue(), endDate.getValue()
            );
            if (data.isEmpty()) {
                statusLabel.setText("No data found for " + symbol);
                return;
            }

            // Stage 2 — Adapt (Adapter pattern: IExportService wraps raw data → file)
            File file = exportService.generateExportFile(data, format.toLowerCase());
            if (file == null) {
                statusLabel.setText("Failed to generate file.");
                return;
            }

            // Stage 3 — Output
            boolean success = exportService.exportPriceData(file);
            if (success) {
                statusLabel.setText("Exported " + data.size() + " records to: " + file.getAbsolutePath());
            } else {
                statusLabel.setText("Export failed.");
            }
        } catch (Exception ex) {
            statusLabel.setText("Error: " + ex.getMessage());
        }
    }

    public VBox getRoot() { return root; }
}
