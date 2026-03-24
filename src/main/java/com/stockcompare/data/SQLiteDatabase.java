package com.stockcompare.data;

import java.sql.*;

public class SQLiteDatabase {

    private static final String URL = "jdbc:sqlite:stockcompare.db";
    private static SQLiteDatabase instance;
    private Connection connection;

    private SQLiteDatabase() throws SQLException {
        connection = DriverManager.getConnection(URL);
        try (Statement s = connection.createStatement()) {
            // Fix 1: WAL mode prevents locking between sessions
            s.execute("PRAGMA journal_mode=WAL");
            // Fix 2: Wait up to 5 seconds if DB is busy instead of failing instantly
            s.execute("PRAGMA busy_timeout=5000");
            // Fix 3: Ensure only one writer at a time
            s.execute("PRAGMA locking_mode=NORMAL");
        }
        createTables();
        System.out.println("[DB] Connected to stockcompare.db");
    }

    public static SQLiteDatabase getInstance() throws SQLException {
        if (instance == null) {
            instance = new SQLiteDatabase();
        } else {
            try {
                if (instance.connection.isClosed()) {
                    instance = new SQLiteDatabase();
                } else {
                    // Test the connection is still alive
                    instance.connection.createStatement().execute("SELECT 1");
                }
            } catch (SQLException e) {
                // Connection is dead — recreate it
                instance = new SQLiteDatabase();
            }
        }
        return instance;
    }

    public Connection getConnection() { return connection; }

    private void createTables() throws SQLException {
        try (Statement s = connection.createStatement()) {
            s.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    user_id       TEXT PRIMARY KEY,
                    username      TEXT UNIQUE NOT NULL,
                    email         TEXT UNIQUE NOT NULL,
                    password_hash TEXT NOT NULL
                )""");
            s.execute("""
                CREATE TABLE IF NOT EXISTS saved_stocks (
                    saved_stock_id TEXT PRIMARY KEY,
                    user_id        TEXT NOT NULL,
                    symbol         TEXT NOT NULL,
                    start_date     TEXT NOT NULL,
                    end_date       TEXT NOT NULL
                )""");
            s.execute("""
                CREATE TABLE IF NOT EXISTS price_data (
                    id      INTEGER PRIMARY KEY AUTOINCREMENT,
                    symbol  TEXT    NOT NULL,
                    date    TEXT    NOT NULL,
                    open    REAL,
                    high    REAL,
                    low     REAL,
                    close   REAL,
                    volume  INTEGER,
                    UNIQUE(symbol, date)
                )""");
        }
        System.out.println("[DB] Tables ready.");
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                instance = null;
                System.out.println("[DB] Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("[DB] Close error: " + e.getMessage());
        }
    }
}
