package dev.cameloasa.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

  private static final String URL = "jdbc:sqlite:todo.db";

  private static Connection connection;

  private ConnectionManager() {
    // prevent instantiation
  }

  public static Connection getConnection() {
    if (connection == null) {
      try {
        connection = DriverManager.getConnection(URL);
      } catch (SQLException e) {
        throw new RuntimeException("Failed to connect to SQLite database", e);
      }
    }
    return connection;
  }

  public static void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
        connection = null;
      } catch (SQLException e) {
        throw new RuntimeException("Failed to close SQLite connection", e);
      }
    }
  }
}
