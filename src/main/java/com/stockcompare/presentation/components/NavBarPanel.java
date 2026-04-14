package com.stockcompare.presentation.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

/**
 * NavBarPanel — reusable sidebar navigation compound component.
 *
 * Domain-independent: this component knows nothing about stocks or users.
 * It only emits a string event when a nav button is clicked.
 * The parent (MainWindow) decides what to do with that event.
 *
 * Compound Component: self-contained — owns its own layout, styling,
 * and state (which button is active).
 */
public class NavBarPanel {

    private final VBox root;
    private final Consumer<String> onNavigate;
    private Button activeButton = null;

    public NavBarPanel(Consumer<String> onNavigate) {
        this.onNavigate = onNavigate;

        root = new VBox(8);
        root.getStyleClass().add("nav-bar");
        root.setPadding(new Insets(20, 12, 20, 12));
        root.setPrefWidth(160);
        root.setAlignment(Pos.TOP_CENTER);
    }

    /**
     * configure — builds nav buttons based on user role.
     * Admin users get an extra Admin Panel button.
     */
    public void configure(boolean isAdmin) {
        root.getChildren().clear();

        addNavButton("🔍  Search",      "search");
        addNavButton("📊  Price Graph", "graph");
        addNavButton("⚖️  Compare",     "compare");
        addNavButton("💾  Saved Stocks","saved");

        if (isAdmin) {
            addNavButton("🛠  Admin",    "admin");
        }
    }

    private void addNavButton(String label, String target) {
        Button btn = new Button(label);
        btn.getStyleClass().add("nav-button");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setOnAction(e -> {
            setActive(btn);
            onNavigate.accept(target);
        });
        root.getChildren().add(btn);
    }

    private void setActive(Button btn) {
        if (activeButton != null) {
            activeButton.getStyleClass().remove("nav-button-active");
        }
        btn.getStyleClass().add("nav-button-active");
        activeButton = btn;
    }

    public VBox getRoot() { return root; }

    public void setVisible(boolean visible) { root.setVisible(visible); }
}
