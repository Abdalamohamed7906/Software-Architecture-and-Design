package com.stockcompare.presentation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * StockCompareApp — JavaFX entry point.
 *
 * Sprint 3 — replaces the console Main.java with a GUI.
 * All service wiring stays in AppContainer (unchanged from Sprint 2).
 *
 * Architecture: Layered — this is the Presentation Layer only.
 * It delegates all logic to the Service Layer via AppContainer.
 */
public class StockCompareApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppContainer app = new AppContainer();

        MainWindow mainWindow = new MainWindow(app);

        Scene scene = new Scene(mainWindow.getRoot(), 1100, 700);
        scene.getStylesheets().add(
            getClass().getResource("/styles/app.css").toExternalForm()
        );

        primaryStage.setTitle("StockCompare — Sprint 3");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> app.shutdown());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
