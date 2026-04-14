package com.stockcompare.presentation.components;

import com.stockcompare.domain.interfaces.ISavedStockService;
import com.stockcompare.domain.model.PriceData;
import com.stockcompare.domain.model.SavedStock;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.List;

/**
 * SavedStocksPanel — compound component for UC6, UC7, UC10.
 * (Save, Load, Delete saved stocks)
 *
 * Sprint 3 — Compound Component.
 * Architecture: MVC View; delegates to ISavedStockService.
 * SOA: depends on ISavedStockService interface only.
 */
public class SavedStocksPanel {

    private final ISavedStockService savedStockService;
    private final VBox root;

    private String userId = null;
    private final ListView<String>   savedList  = new ListView<>();
    private final TableView<PriceData> dataTable = new TableView<>();
    private final Label statusLabel = new Label();

    private List<SavedStock> currentSavedStocks = List.of();

    public SavedStocksPanel(ISavedStockService savedStockService) {
        this.savedStockService = savedStockService;
        this.root = buildRoot();
    }

    private VBox buildRoot() {
        VBox container = new VBox(16);
        container.getStyleClass().add("panel");
        container.setPadding(new Insets(24));

        Label title = new Label("Saved Stocks");
        title.getStyleClass().add("panel-title");

        // Saved stocks list
        savedList.setPrefHeight(180);
        savedList.setPlaceholder(new Label("No saved stocks yet."));
        savedList.getSelectionModel().selectedIndexProperty()
            .addListener((obs, oldVal, newVal) -> loadSelectedStock(newVal.intValue()));

        // Action buttons
        Button refreshBtn = new Button("Refresh");
        refreshBtn.getStyleClass().add("secondary-button");
        refreshBtn.setOnAction(e -> loadSavedStocks());

        Button deleteBtn = new Button("Delete Selected");
        deleteBtn.getStyleClass().add("danger-button");
        deleteBtn.setOnAction(e -> deleteSelected());

        HBox btnRow = new HBox(8, refreshBtn, deleteBtn);

        // Price data table for selected saved stock
        buildDataTable();

        container.getChildren().addAll(
            title,
            new Label("Your saved stocks:"),
            savedList,
            btnRow,
            new Label("Price data for selected stock:"),
            dataTable,
            statusLabel
        );
        return container;
    }

    private void buildDataTable() {
        TableColumn<PriceData, String> dateCol  = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("date"));

        TableColumn<PriceData, Double> closeCol = new TableColumn<>("Close");
        closeCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("close"));

        TableColumn<PriceData, Double> highCol  = new TableColumn<>("High");
        highCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("high"));

        TableColumn<PriceData, Double> lowCol   = new TableColumn<>("Low");
        lowCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("low"));

        dataTable.getColumns().addAll(dateCol, closeCol, highCol, lowCol);
        dataTable.setPrefHeight(200);
        dataTable.setPlaceholder(new Label("Select a saved stock to view data"));
    }

    public void setUserId(String userId) {
        this.userId = userId;
        loadSavedStocks();
    }

    private void loadSavedStocks() {
        if (userId == null) {
            statusLabel.setText("Please log in to view saved stocks.");
            return;
        }
        try {
            currentSavedStocks = savedStockService.getSavedStocks(userId);
            List<String> labels = currentSavedStocks.stream()
                .map(s -> s.getSymbol() + "  [" + s.getSavedStockId() + "]")
                .toList();
            savedList.setItems(FXCollections.observableArrayList(labels));
            statusLabel.setText(currentSavedStocks.size() + " saved stock(s) loaded.");
        } catch (Exception ex) {
            statusLabel.setText("Error loading saved stocks: " + ex.getMessage());
        }
    }

    private void loadSelectedStock(int index) {
        if (index < 0 || index >= currentSavedStocks.size()) return;
        SavedStock selected = currentSavedStocks.get(index);
        try {
            List<PriceData> data = savedStockService.loadStockData(selected.getSavedStockId());
            dataTable.setItems(FXCollections.observableArrayList(data));
            statusLabel.setText("Loaded " + data.size() + " records for " + selected.getSymbol());
        } catch (Exception ex) {
            statusLabel.setText("Error loading data: " + ex.getMessage());
        }
    }

    private void deleteSelected() {
        int index = savedList.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            statusLabel.setText("Please select a stock to delete.");
            return;
        }
        SavedStock selected = currentSavedStocks.get(index);
        try {
            boolean deleted = savedStockService.deleteStock(selected.getSavedStockId());
            if (deleted) {
                statusLabel.setText("Deleted: " + selected.getSymbol());
                loadSavedStocks();
            }
        } catch (Exception ex) {
            statusLabel.setText("Error deleting: " + ex.getMessage());
        }
    }

    public VBox getRoot() { return root; }
}
