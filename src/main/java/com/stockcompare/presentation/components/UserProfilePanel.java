package com.stockcompare.presentation.components;

import com.stockcompare.domain.interfaces.IAccountService;
import com.stockcompare.domain.model.UserDetail;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * UserProfilePanel — compound component for UC2 (Manage Account).
 *
 * Sprint 3 — Compound Component.
 * Allows the logged-in user to view and update their account details.
 *
 * Architecture: MVC — View component; delegates to IAccountService.
 * SOA: depends on IAccountService interface only.
 */
public class UserProfilePanel {

    private final IAccountService accountService;
    private final VBox root;

    private UserDetail currentUser = null;

    private final TextField usernameField = new TextField();
    private final TextField emailField    = new TextField();
    private final Label     userIdLabel   = new Label();
    private final Label     statusLabel   = new Label();

    public UserProfilePanel(IAccountService accountService) {
        this.accountService = accountService;
        this.root           = buildRoot();
    }

    private VBox buildRoot() {
        VBox container = new VBox(16);
        container.getStyleClass().add("panel");
        container.setPadding(new Insets(24));

        Label title = new Label("My Profile");
        title.getStyleClass().add("panel-title");

        Label subtitle = new Label("View and update your account details.");
        subtitle.getStyleClass().add("subtitle-label");

        // User ID (read-only)
        Label idLabel = new Label("User ID:");
        userIdLabel.getStyleClass().add("subtitle-label");

        // Username field
        usernameField.setPromptText("Username");

        // Email field
        emailField.setPromptText("Email address");

        // Save button
        Button saveBtn = new Button("Update Details");
        saveBtn.getStyleClass().add("primary-button");
        saveBtn.setOnAction(e -> handleUpdate());

        // Status
        statusLabel.getStyleClass().add("status-label");

        GridPane form = new GridPane();
        form.setHgap(12);
        form.setVgap(12);
        form.setMaxWidth(450);

        form.add(new Label("User ID:"),   0, 0);
        form.add(userIdLabel,             1, 0);
        form.add(new Label("Username:"),  0, 1);
        form.add(usernameField,           1, 1);
        form.add(new Label("Email:"),     0, 2);
        form.add(emailField,              1, 2);
        form.add(saveBtn,                 1, 3);

        GridPane.setHgrow(usernameField, Priority.ALWAYS);
        GridPane.setHgrow(emailField,    Priority.ALWAYS);

        container.getChildren().addAll(title, subtitle, form, statusLabel);
        return container;
    }

    /**
     * Called by MainWindow after login to populate the panel with user data.
     */
    public void setUser(UserDetail user) {
        this.currentUser = user;
        if (user != null) {
            userIdLabel.setText(user.userId);
            usernameField.setText(user.username);
            emailField.setText(user.email);
            statusLabel.setText("");
        }
    }

    private void handleUpdate() {
        if (currentUser == null) {
            statusLabel.setText("No user loaded.");
            return;
        }

        String newUsername = usernameField.getText().trim();
        String newEmail    = emailField.getText().trim();

        if (newUsername.isEmpty() || newEmail.isEmpty()) {
            showStatus("Please fill in all fields.", true);
            return;
        }

        if (!newEmail.contains("@")) {
            showStatus("Please enter a valid email address.", true);
            return;
        }

        try {
            UserDetail updated = new UserDetail(
                currentUser.userId,
                newUsername,
                newEmail,
                currentUser.passwordHash
            );

            boolean success = accountService.updateAccountDetails(updated);
            if (success) {
                currentUser = updated;
                showStatus("✅ Profile updated successfully!", false);
            } else {
                showStatus("Update failed. Please try again.", true);
            }
        } catch (Exception ex) {
            showStatus("Error: " + ex.getMessage(), true);
        }
    }

    private void showStatus(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.getStyleClass().removeAll("error-label", "success-label");
        statusLabel.getStyleClass().add(isError ? "error-label" : "success-label");
    }

    public VBox getRoot() { return root; }
}
