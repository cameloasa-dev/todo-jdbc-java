package dev.cameloasa.daoimpl;

import dev.cameloasa.dao.TodoItemTaskDao;
import dev.cameloasa.db.ConnectionManager;
import dev.cameloasa.model.TodoItemTask;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TodoItemTaskDaoImpl implements TodoItemTaskDao {

  private static final String INSERT_SQL =
      "INSERT INTO todo_item_task (title, description, done, todo_item_id) VALUES (?, ?, ?, ?)";

  private static final String UPDATE_SQL =
      "UPDATE todo_item_task SET title = ?, description = ?, done = ?, todo_item_id = ? WHERE task_id = ?";

  private static final String DELETE_SQL = "DELETE FROM todo_item_task WHERE task_id = ?";

  private static final String FIND_BY_ID_SQL = "SELECT * FROM todo_item_task WHERE task_id = ?";

  private static final String FIND_ALL_SQL = "SELECT * FROM todo_item_task";

  private static final String FIND_BY_TODOITEM_SQL =
      "SELECT * FROM todo_item_task WHERE todo_item_id = ?";

  private static final String FIND_BY_DONE_SQL = "SELECT * FROM todo_item_task WHERE done = ?";

  private static final String FIND_BY_TITLE_SQL = "SELECT * FROM todo_item_task WHERE title LIKE ?";

  private static final String FIND_OVERDUE_SQL =
      "SELECT * FROM todo_item_task WHERE done = 0"; // placeholder dacă adaugi deadline în viitor

  @Override
  public TodoItemTask create(TodoItemTask task) {
    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, task.getTitle());
      ps.setString(2, task.getDescription());
      ps.setBoolean(3, task.isDone());
      ps.setInt(4, task.getTodoItemId());

      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) {
          task.setTaskId(rs.getInt(1));
        }
      }

      return task;

    } catch (SQLException e) {
      throw new RuntimeException("Failed to create TodoItemTask", e);
    }
  }

  @Override
  public boolean update(TodoItemTask task) {
    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

      ps.setString(1, task.getTitle());
      ps.setString(2, task.getDescription());
      ps.setBoolean(3, task.isDone());
      ps.setInt(4, task.getTodoItemId());
      ps.setInt(5, task.getTaskId());

      return ps.executeUpdate() > 0;

    } catch (SQLException e) {
      throw new RuntimeException("Failed to update TodoItemTask", e);
    }
  }

  @Override
  public boolean deleteById(int taskId) {
    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

      ps.setInt(1, taskId);
      return ps.executeUpdate() > 0;

    } catch (SQLException e) {
      throw new RuntimeException("Failed to delete TodoItemTask", e);
    }
  }

  @Override
  public Optional<TodoItemTask> findById(int taskId) {
    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {

      ps.setInt(1, taskId);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapRow(rs));
        }
      }

      return Optional.empty();

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find TodoItemTask by ID", e);
    }
  }

  @Override
  public List<TodoItemTask> findAll() {
    List<TodoItemTask> tasks = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_ALL_SQL);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        tasks.add(mapRow(rs));
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find all TodoItemTasks", e);
    }

    return tasks;
  }

  @Override
  public List<TodoItemTask> findByTodoItemId(int todoItemId) {
    List<TodoItemTask> tasks = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_BY_TODOITEM_SQL)) {

      ps.setInt(1, todoItemId);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          tasks.add(mapRow(rs));
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find tasks by TodoItem ID", e);
    }

    return tasks;
  }

  @Override
  public List<TodoItemTask> findByDoneStatus(boolean done) {
    List<TodoItemTask> tasks = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_BY_DONE_SQL)) {

      ps.setBoolean(1, done);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          tasks.add(mapRow(rs));
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find tasks by done status", e);
    }

    return tasks;
  }

  @Override
  public List<TodoItemTask> findByTitle(String title) {
    List<TodoItemTask> tasks = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_BY_TITLE_SQL)) {

      ps.setString(1, "%" + title + "%");

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          tasks.add(mapRow(rs));
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find tasks by title", e);
    }

    return tasks;
  }

  @Override
  public List<TodoItemTask> findOverdueTasks() {
    List<TodoItemTask> tasks = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_OVERDUE_SQL);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        tasks.add(mapRow(rs));
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find overdue tasks", e);
    }

    return tasks;
  }

  private TodoItemTask mapRow(ResultSet rs) throws SQLException {
    return new TodoItemTask(
        rs.getInt("task_id"),
        rs.getString("title"),
        rs.getString("description"),
        rs.getBoolean("done"),
        rs.getInt("todo_item_id"));
  }
}
