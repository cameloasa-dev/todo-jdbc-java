package dev.cameloasa.db;

import dev.cameloasa.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

  private ConnectionManager() {
    // prevent instantiation
  }

  public static Connection getConnection() {
    try {
      // Create a connection to the SQLite database using the URL from DatabaseConfig
      return DriverManager.getConnection(DatabaseConfig.DB_URL);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to connect to SQLite database", e);
    }
  }
}
