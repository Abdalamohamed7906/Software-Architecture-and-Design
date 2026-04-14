package com.stockcompare.presentation.components;

import com.stockcompare.domain.interfaces.IAdminService;
import com.stockcompare.domain.model.UserDetail;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.List;

public class AdminPanel {

    private final IAdminService adminService;
    private final VBox root;
    private final TableView<UserDetail> usersTable = new TableView<>();
    private final TextField searchField = new TextField();
    private final Label statsLabel = new Label();
    private final Label statusLabel = new Label();

    public AdminPanel(IAdminService adminService) {
        this.adminService = adminService;
        this.root = buildRoot();
        loadUsers();
    }

    private VBox buildRoot() {
        VBox container = new VBox(16);
        container.getStyleClass().add("panel");
        container.setPadding(new Insets(24));

        Label title = new Label("Admin Panel");
        title.getStyleClass().add("panel-title");

        Label subtitle = new Label("Manage registered users and view system statistics.");
        subtitle.getStyleClass().add("subtitle-label");

        statsLabel.getStyleClass().add("subtitle-label");

        searchField.setPromptText("Search by username...");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        Button searchBtn = new Button("Search");
        searchBtn.getStyleClass().add("primary-button");
        searchBtn.setOnAction(e -> handleSearch());
        searchField.setOnAction(e -> handleSearch());

        Button refreshBtn = new Button("Show All");
        refreshBtn.getStyleClass().add("secondary-button");
        refreshBtn.setOnAction(e -> loadUsers());

        HBox searchRow = new HBox(8, searchField, searchBtn, refreshBtn);

        buildUsersTable();

        Button deleteBtn = new Button("Delete Selected User");
        deleteBtn.getStyleClass().add("danger-button");
        deleteBtn.setOnAction(e -> handleDelete());

        statusLabel.getStyleClass().add("status-label");

        container.getChildren().addAll(title, subtitle, statsLabel, searchRow, usersTable, deleteBtn, statusLabel);
        return container;
    }

    private void buildUsersTable() {
        TableColumn<UserDetail, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().username));
        usernameCol.setPrefWidth(160);

        TableColumn<UserDetail, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().email));
        emailCol.setPrefWidth(260);

        TableColumn<UserDetail, String> idCol = new TableColumn<>("User ID");
        idCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().userId));
        idCol.setPrefWidth(280);

        usersTable.getColumns().addAll(usernameCol, emailCol, idCol);
        usersTable.setPrefHeight(280);
        usersTable.setPlaceholder(new Label("No users found."));
    }

    private void loadUsers() {
        try {
            List<UserDetail> users = adminService.getAllUsers();
            usersTable.setItems(FXCollections.observableArrayList(users));
            statsLabel.setText("Total registered users: " + users.size());
            statusLabel.setText("");
        } catch (Exception ex) {
            statusLabel.setText("Error loading users: " + ex.getMessage());
        }
    }

    private void handleSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) { loadUsers(); return; }
        try {
            UserDetail user = adminService.getUserByUsername(query);
            usersTable.setItems(FXCollections.observableArrayList(user));
            statusLabel.setText("Found: " + user.username);
        } catch (Exception ex) {
            statusLabel.setText("User not found: " + query);
            usersTable.setItems(FXCollections.observableArrayList());
        }
    }

    private void handleDelete() {
        UserDetail selected = usersTable.getSelectionModel().getSelectedItem();
        if (selected == null) { statusLabel.setText("Please select a user to delete."); return; }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete User");
        confirm.setHeaderText("Delete " + selected.username + "?");
        confirm.setContentText("This action cannot be undone.");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    boolean deleted = adminService.deleteUser(selected.userId);
                    if (deleted) { statusLabel.setText("Deleted: " + selected.username); loadUsers(); }
                    else statusLabel.setText("Delete failed.");
                } catch (Exception ex) { statusLabel.setText("Error: " + ex.getMessage()); }
            }
        });
    }

    public VBox getRoot() { return root; }
}
