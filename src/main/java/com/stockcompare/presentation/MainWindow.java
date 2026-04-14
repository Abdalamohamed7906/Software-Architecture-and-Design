package com.stockcompare.presentation;

import com.stockcompare.domain.model.UserDetail;
import com.stockcompare.presentation.components.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * MainWindow — root compound component.
 *
 * Sprint 3 — assembles sidebar + content area.
 * Updated: wires UserProfilePanel, updated SearchPanel (with save),
 *          and passes userId to panels that need it.
 *
 * Architecture: Layered — Presentation Layer only.
 */
public class MainWindow {

    private final AppContainer app;
    private final BorderPane   root;
    private final StackPane    contentArea;
    private UserDetail currentUser = null;

    // Compound component panels
    private final LoginPanel        loginPanel;
    private final SearchPanel       searchPanel;
    private final ComparisonPanel   comparisonPanel;
    private final SavedStocksPanel  savedStocksPanel;
    private final ExportPanel       exportPanel;
    private final AdminPanel        adminPanel;
    private final UserProfilePanel  userProfilePanel;

    private VBox sidebar;
    private Label headerUserLabel;

    public MainWindow(AppContainer app) {
        this.app = app;

        // Instantiate all compound components
        this.loginPanel       = new LoginPanel(app.userService, this::onLogin);
        this.searchPanel      = new SearchPanel(app.priceService, app.analysisService, app.savedStockService);
        this.comparisonPanel  = new ComparisonPanel(app.analysisService);
        this.savedStocksPanel = new SavedStocksPanel(app.savedStockService);
        this.exportPanel      = new ExportPanel(app.exportService, app.priceService);
        this.adminPanel       = new AdminPanel(app.adminService);
        this.userProfilePanel = new UserProfilePanel(app.userService);

        this.contentArea = new StackPane();
        this.contentArea.getStyleClass().add("content-area");

        this.root = new BorderPane();
        this.root.getStyleClass().add("main-window");

        buildHeader();
        buildSidebar();
        root.setCenter(contentArea);

        showPanel(loginPanel.getRoot());
    }

    // ── Header ───────────────────────────────────────────────────────────────

    private void buildHeader() {
        HBox header = new HBox();
        header.getStyleClass().add("header");
        header.setPadding(new Insets(0, 20, 0, 20));

        Label title = new Label("📈 StockCompare");
        title.getStyleClass().add("header-title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        headerUserLabel = new Label("Not logged in");
        headerUserLabel.getStyleClass().add("header-user");

        // Clicking the username label goes to profile
        headerUserLabel.setOnMouseClicked(e -> {
            if (currentUser != null) showPanel(userProfilePanel.getRoot());
        });
        headerUserLabel.setStyle("-fx-cursor: hand;");

        header.getChildren().addAll(title, spacer, headerUserLabel);
        root.setTop(header);
    }

    // ── Sidebar ──────────────────────────────────────────────────────────────

    private void buildSidebar() {
        sidebar = new VBox(8);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPadding(new Insets(20, 10, 20, 10));

        addNavButton("🔍  Search Stocks",   () -> showPanel(searchPanel.getRoot()));
        addNavButton("📊  Compare Stocks",  () -> showPanel(comparisonPanel.getRoot()));
        addNavButton("💾  Saved Stocks",    () -> {
            savedStocksPanel.setUserId(currentUser != null ? currentUser.userId : null);
            showPanel(savedStocksPanel.getRoot());
        });
        addNavButton("📤  Export Data",     () -> showPanel(exportPanel.getRoot()));
        addNavButton("👤  My Profile",      () -> showPanel(userProfilePanel.getRoot()));
        addNavButton("🛡  Admin Panel",     () -> showPanel(adminPanel.getRoot()));
        addNavButton("🔓  Logout",          this::logout);

        root.setLeft(sidebar);
        sidebar.setVisible(false);
    }

    private void addNavButton(String text, Runnable action) {
        Button btn = new Button(text);
        btn.getStyleClass().add("nav-button");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setOnAction(e -> action.run());
        sidebar.getChildren().add(btn);
    }

    // ── Navigation ───────────────────────────────────────────────────────────

    private void showPanel(javafx.scene.Node panel) {
        contentArea.getChildren().setAll(panel);
    }

    // ── Auth callbacks ───────────────────────────────────────────────────────

    private void onLogin(UserDetail user) {
        this.currentUser = user;
        sidebar.setVisible(true);

        // Update header
        headerUserLabel.setText("👤 " + user.username);

        // Pass userId to panels that need it
        searchPanel.setCurrentUserId(user.userId);
        userProfilePanel.setUser(user);

        showPanel(searchPanel.getRoot());
    }

    private void logout() {
        this.currentUser = null;
        sidebar.setVisible(false);
        headerUserLabel.setText("Not logged in");
        showPanel(loginPanel.getRoot());
    }

    public BorderPane getRoot() { return root; }
}
