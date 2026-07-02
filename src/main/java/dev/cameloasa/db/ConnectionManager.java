package dev.cameloasa.db;

import dev.cameloasa.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {

  private ConnectionManager() {
    // prevent instantiation
  }

  public static Connection getConnection() {
    try {
      Connection conn = DriverManager.getConnection(DatabaseConfig.DB_URL);

      try (Statement stmt = conn.createStatement()) {
        stmt.execute("PRAGMA foreign_keys = ON");
      }

      return conn;

    } catch (SQLException e) {
      throw new RuntimeException("Failed to connect to SQLite database", e);
    }
  }
}
