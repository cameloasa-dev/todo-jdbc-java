package dev.cameloasa.dao.daoimpl;

import dev.cameloasa.db.MySQLConnection;
import dev.cameloasa.model.Person;
import dev.cameloasa.model.TodoItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TodoItemDaoImpl implements TodoItemDao {

  private Connection connection;

  // Extract TodoItem from ResultSet
  private TodoItem extractTodoItemFromResultSet(ResultSet resultSet) throws SQLException {
    // get - todo_id, title, description, deadline, done, assignee_id - return TodoItem

    int todo_id = resultSet.getInt("todo_id");
    String title = resultSet.getString("title");
    String description = resultSet.getString("description");
    LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
    boolean done = resultSet.getBoolean("done");
    int assignee_id = resultSet.getInt("assignee_id");
    return new TodoItem(title, description, deadline, done, assignee_id);
  }

  // Constructor
  public TodoItemDaoImpl() {
    this.connection = MySQLConnection.getConnection();
  }

  @Override
  public TodoItem create(TodoItem todoItem) {
    // SQL - INSERT INTO odo_item (title, description, deadline, done, assignee_id) VALUES (?, ?, ?,
    // ?, ?)
    String query =
        "INSERT INTO todo_item (title, description, deadline, done, assignee_id) VALUES (?, ?, ?, ?, ?)";
    // Prepare Statement
    try (PreparedStatement statement =
        connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
      ;
      // set parameters
      statement.setString(1, todoItem.getTitle());
      statement.setString(2, todoItem.getDescription());
      statement.setDate(3, java.sql.Date.valueOf(todoItem.getDeadline()));
      statement.setBoolean(4, todoItem.isDone());
      statement.setInt(5, todoItem.getAssignee_id());
      // Execute
      int affectedRows = statement.executeUpdate();
      // Check
      if (affectedRows == 0) {
        throw new SQLException("Creating todo_item failed, no rows affected.");
      }
      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        ;
        if (generatedKeys.next()) {
          todoItem.setTodo_id(generatedKeys.getInt(1));
        } else {
          throw new SQLException("Creating todo_item failed, no ID obtained.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return todoItem;
  }

  @Override
  public Collection<TodoItem> findAll() {
    // Create a list
    List<TodoItem> todoItems = new ArrayList<>();
    // SQL - SELECT * FROM todo_item
    String query = "SELECT * FROM todo_item";
    // Prepare Statement
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      // Execute
      ResultSet resultSet = statement.executeQuery();
      // Check
      while (resultSet.next()) {
        todoItems.add(extractTodoItemFromResultSet(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return todoItems;
  }

  @Override
  public TodoItem findById(int todo_id) {
    // SQL - SELECT * FROM todo_tem WHERE todo_id = ?
    String query = "SELECT * FROM todo_item WHERE todo_id = ?";
    // Prepare Statement
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      // Set parameters
      statement.setInt(1, todo_id);
      // Execute
      ResultSet resultSet = statement.executeQuery();
      // Check
      if (resultSet.next()) {
        return extractTodoItemFromResultSet(resultSet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Collection<TodoItem> findByDoneStatus(boolean done) {
    // Create a list
    List<TodoItem> todoItems = new ArrayList<>();
    // SQL - SELECT * FROM todo_item WHERE done = ?
    String query = "SELECT * FROM todo_item WHERE done = ?";
    // Prepare Statement
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      // Set parameters
      statement.setBoolean(1, done);
      // Execute
      ResultSet resultSet = statement.executeQuery();
      // Check
      while (resultSet.next()) {
        todoItems.add(extractTodoItemFromResultSet(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return todoItems;
  }

  @Override
  public Collection<TodoItem> findByAssignee(int assignee_id) {
    // Create a list
    List<TodoItem> todoItems = new ArrayList<>();
    // SQL - SELECT * FROM todo_item WHERE assignee_id = ?
    String query = "SELECT * FROM todo_item WHERE assignee_id = ?";
    // Prepare Statement
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      // Set parameters
      statement.setInt(1, assignee_id);
      // Execute
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          TodoItem todoItem = extractTodoItemFromResultSet(resultSet);
          todoItems.add(todoItem); // Add the extracted TodoItem to the list
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return todoItems;
  }

  @Override
  public Collection<TodoItem> findByAssignee(Person assignee) {
    // Create a list
    List<TodoItem> todoItems = new ArrayList<>();
    // SQL - SELECT * FROM todo_item WHERE assignee_id = ?
    String query = "SELECT * FROM todo_item WHERE assignee_id = ?";
    // Prepare Statement
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      // Set parameters
      statement.setInt(1, assignee.getPerson_id());
      // Execute
      ResultSet resultSet = statement.executeQuery();
      // Check
      while (resultSet.next()) {
        extractTodoItemFromResultSet(resultSet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return todoItems;
  }

  @Override
  public Collection<TodoItem> findByUnassignedTodoitems() {
    List<TodoItem> todoItems = new ArrayList<>();
    // SQL - SELECT * FROM todo_item WHERE assignee_id IS NULL
    String query = "SELECT * FROM todo_item WHERE assignee_id IS NULL";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          TodoItem todoItem = extractTodoItemFromResultSet(resultSet);
          todoItems.add(todoItem); // Add the extracted TodoItem to the list
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return todoItems;
  }

  @Override
  public TodoItem update(TodoItem todoItem) {
    // SQL - UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id =
    // ? WHERE todo_id = ?
    String query =
        "UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?";
    // Prepare Statement
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      // Set parameters
      statement.setString(1, todoItem.getTitle());
      statement.setString(2, todoItem.getDescription());
      statement.setDate(3, java.sql.Date.valueOf(todoItem.getDeadline()));
      statement.setBoolean(4, todoItem.isDone());
      statement.setInt(5, todoItem.getAssignee_id());
      statement.setInt(6, todoItem.getTodo_id());
      // Execute
      int affectedRows = statement.executeUpdate();
      // Check
      if (affectedRows == 0) {
        throw new SQLException("Updating todo_item failed, no rows affected.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return todoItem; // Return the updated todoItem
  }

  @Override
  public boolean deleteById(int todo_id) {
    // SQL - DELETE FROM todo_item WHERE todo_id = ?
    String query = "DELETE FROM todo_item WHERE todo_id = ?";
    // Prepare Statement
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      // Set parameters
      statement.setInt(1, todo_id);
      // Execute
      int affectedRows = statement.executeUpdate();
      // Check
      if (affectedRows == 0) {
        throw new SQLException("Deleting todo_item failed, no rows affected.");
      }
      // Return true if the delete was successful
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
    } // Return false if the delete was not successful
    return false;
  }
}
