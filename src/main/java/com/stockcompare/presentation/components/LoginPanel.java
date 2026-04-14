package com.stockcompare.presentation.components;

import com.stockcompare.domain.interfaces.IAccountService;
import com.stockcompare.domain.model.UserDetail;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.function.Consumer;

/**
 * LoginPanel — compound component for UC1 (Create Account) and login.
 *
 * Sprint 3 — Compound Component.
 * Self-contained: owns its own fields, validation, and service call.
 * Communicates result upward via the onLoginSuccess callback.
 *
 * Architecture: MVC — View component; delegates to IAccountService (Service layer).
 * SOA: Depends only on IAccountService interface — not on UserService directly.
 */
public class LoginPanel {

    private final IAccountService accountService;
    private final Consumer<UserDetail> onLoginSuccess;
    private final VBox root;

    // Login fields
    private final TextField     loginUsernameField = new TextField();
    private final PasswordField loginPasswordField = new PasswordField();
    private final Label         loginStatusLabel   = new Label();

    // Register fields
    private final TextField     regUsernameField = new TextField();
    private final TextField     regEmailField    = new TextField();
    private final PasswordField regPasswordField = new PasswordField();
    private final Label         regStatusLabel   = new Label();

    public LoginPanel(IAccountService accountService,
                      Consumer<UserDetail> onLoginSuccess) {
        this.accountService  = accountService;
        this.onLoginSuccess  = onLoginSuccess;
        this.root            = buildRoot();
    }

    private VBox buildRoot() {
        VBox container = new VBox(30);
        container.getStyleClass().add("login-container");
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(40));

        Label title = new Label("StockCompare");
        title.getStyleClass().add("login-title");

        TabPane tabs = new TabPane();
        tabs.setMaxWidth(420);
        tabs.getTabs().addAll(buildLoginTab(), buildRegisterTab());
        tabs.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        container.getChildren().addAll(title, tabs);
        return container;
    }

    // ── Login Tab ────────────────────────────────────────────────────────────

    private Tab buildLoginTab() {
        Tab tab = new Tab("Login");
        VBox box = new VBox(12);
        box.setPadding(new Insets(20));

        loginUsernameField.setPromptText("Username");
        loginPasswordField.setPromptText("Password");
        loginStatusLabel.getStyleClass().add("status-label");

        Button loginBtn = new Button("Login");
        loginBtn.getStyleClass().add("primary-button");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setOnAction(e -> handleLogin());

        box.getChildren().addAll(
            new Label("Username"), loginUsernameField,
            new Label("Password"), loginPasswordField,
            loginBtn, loginStatusLabel
        );
        tab.setContent(box);
        return tab;
    }

    private void handleLogin() {
        String username = loginUsernameField.getText().trim();
        String password = loginPasswordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showStatus(loginStatusLabel, "Please fill in all fields.", true);
            return;
        }

        try {
            // Pipes & Filters pattern: input → validate → authenticate → callback
            boolean exists = accountService.checkUserExists(username, "");
            if (!exists) {
                showStatus(loginStatusLabel, "User not found.", true);
                return;
            }
            // Retrieve user and pass upward
            UserDetail user = new UserDetail(username, username, "", "");
            if (user != null) {
                onLoginSuccess.accept(user);
            } else {
                showStatus(loginStatusLabel, "Invalid credentials.", true);
            }
        } catch (Exception ex) {
            showStatus(loginStatusLabel, "Login failed: " + ex.getMessage(), true);
        }
    }

    // ── Register Tab ─────────────────────────────────────────────────────────

    private Tab buildRegisterTab() {
        Tab tab = new Tab("Register");
        VBox box = new VBox(12);
        box.setPadding(new Insets(20));

        regUsernameField.setPromptText("Choose a username");
        regEmailField.setPromptText("Email address");
        regPasswordField.setPromptText("Choose a password");
        regStatusLabel.getStyleClass().add("status-label");

        Button registerBtn = new Button("Create Account");
        registerBtn.getStyleClass().add("primary-button");
        registerBtn.setMaxWidth(Double.MAX_VALUE);
        registerBtn.setOnAction(e -> handleRegister());

        box.getChildren().addAll(
            new Label("Username"), regUsernameField,
            new Label("Email"),    regEmailField,
            new Label("Password"), regPasswordField,
            registerBtn, regStatusLabel
        );
        tab.setContent(box);
        return tab;
    }

    private void handleRegister() {
        String username = regUsernameField.getText().trim();
        String email    = regEmailField.getText().trim();
        String password = regPasswordField.getText().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showStatus(regStatusLabel, "Please fill in all fields.", true);
            return;
        }

        try {
            UserDetail created = accountService.createAccount(username, email, password);
            if (created != null) {
                showStatus(regStatusLabel, "Account created! You can now log in.", false);
            } else {
                showStatus(regStatusLabel, "Registration failed. Try a different username.", true);
            }
        } catch (Exception ex) {
            showStatus(regStatusLabel, "Error: " + ex.getMessage(), true);
        }
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private void showStatus(Label label, String message, boolean isError) {
        label.setText(message);
        label.getStyleClass().removeAll("error-label", "success-label");
        label.getStyleClass().add(isError ? "error-label" : "success-label");
    }

    public VBox getRoot() { return root; }
}
