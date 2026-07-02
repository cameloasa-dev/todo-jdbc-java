package dev.cameloasa.daoimpl;

import dev.cameloasa.dao.TodoItemDao;
import dev.cameloasa.db.ConnectionManager;
import dev.cameloasa.model.Person;
import dev.cameloasa.model.TodoItem;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TodoItemDaoImpl implements TodoItemDao {

  private static final String INSERT_SQL =
      "INSERT INTO todo_item (title, description, deadline, done, assignee_id) VALUES (?, ?, ?, ?, ?)";

  private static final String UPDATE_SQL =
      "UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?";

  private static final String DELETE_SQL = "DELETE FROM todo_item WHERE todo_id = ?";

  private static final String FIND_BY_ID_SQL = "SELECT * FROM todo_item WHERE todo_id = ?";

  private static final String FIND_ALL_SQL = "SELECT * FROM todo_item";

  private static final String FIND_BY_DONE_SQL = "SELECT * FROM todo_item WHERE done = ?";

  private static final String FIND_BY_ASSIGNEE_ID_SQL =
      "SELECT * FROM todo_item WHERE assignee_id = ?";

  private static final String FIND_UNASSIGNED_SQL =
      "SELECT * FROM todo_item WHERE assignee_id IS NULL";

  private static final String FIND_BY_DEADLINE_SQL = "SELECT * FROM todo_item WHERE deadline = ?";

  private static final String FIND_OVERDUE_SQL =
      "SELECT * FROM todo_item WHERE done = 0 AND deadline < DATE('now')";

  private static final String FIND_BY_DEADLINE_RANGE_SQL =
      "SELECT * FROM todo_item WHERE deadline BETWEEN ? AND ?";

  private static final String FIND_BY_TITLE_SQL = "SELECT * FROM todo_item WHERE title LIKE ?";

  private TodoItem mapRow(ResultSet rs) throws SQLException {
    return new TodoItem(
        rs.getInt("todo_id"),
        rs.getString("title"),
        rs.getString("description"),
        LocalDate.parse(rs.getString("deadline")),
        rs.getBoolean("done"),
        rs.getInt("assignee_id"));
  }

  @Override
  public TodoItem create(TodoItem item) {
    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, item.getTitle());
      ps.setString(2, item.getDescription());
      ps.setString(3, item.getDeadline().toString());
      ps.setBoolean(4, item.isDone());
      ps.setInt(5, item.getAssigneeId());

      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) {
          item.setTodoId(rs.getInt(1));
        }
      }

      return item;

    } catch (SQLException e) {
      throw new RuntimeException("Failed to create TodoItem", e);
    }
  }

  @Override
  public boolean update(TodoItem item) {
    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

      ps.setString(1, item.getTitle());
      ps.setString(2, item.getDescription());
      ps.setString(3, item.getDeadline().toString());
      ps.setBoolean(4, item.isDone());
      ps.setInt(5, item.getAssigneeId());
      ps.setInt(6, item.getTodoId());

      return ps.executeUpdate() > 0;

    } catch (SQLException e) {
      throw new RuntimeException("Failed to update TodoItem", e);
    }
  }

  @Override
  public boolean deleteById(int todoId) {
    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

      ps.setInt(1, todoId);
      return ps.executeUpdate() > 0;

    } catch (SQLException e) {
      throw new RuntimeException("Failed to delete TodoItem", e);
    }
  }

  @Override
  public Optional<TodoItem> findById(int todoId) {
    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {

      ps.setInt(1, todoId);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapRow(rs));
        }
      }

      return Optional.empty();

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find TodoItem by ID", e);
    }
  }

  @Override
  public List<TodoItem> findAll() {
    List<TodoItem> items = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_ALL_SQL);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        items.add(mapRow(rs));
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find all TodoItems", e);
    }

    return items;
  }

  @Override
  public List<TodoItem> findByDoneStatus(boolean done) {
    List<TodoItem> items = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_BY_DONE_SQL)) {

      ps.setBoolean(1, done);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          items.add(mapRow(rs));
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find TodoItems by done status", e);
    }

    return items;
  }

  @Override
  public List<TodoItem> findByAssigneeId(int assigneeId) {
    List<TodoItem> items = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_BY_ASSIGNEE_ID_SQL)) {

      ps.setInt(1, assigneeId);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          items.add(mapRow(rs));
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find TodoItems by assignee ID", e);
    }

    return items;
  }

  @Override
  public List<TodoItem> findByAssignee(Person assignee) {
    return findByAssigneeId(assignee.getPersonId());
  }

  @Override
  public List<TodoItem> findUnassigned() {
    List<TodoItem> items = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_UNASSIGNED_SQL);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        items.add(mapRow(rs));
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find unassigned TodoItems", e);
    }

    return items;
  }

  @Override
  public List<TodoItem> findByDeadline(LocalDate deadline) {
    List<TodoItem> items = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_BY_DEADLINE_SQL)) {

      ps.setDate(1, Date.valueOf(deadline));

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          items.add(mapRow(rs));
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find TodoItems by deadline", e);
    }

    return items;
  }

  @Override
  public List<TodoItem> findOverdue() {
    List<TodoItem> items = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_OVERDUE_SQL);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        items.add(mapRow(rs));
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find overdue TodoItems", e);
    }

    return items;
  }

  @Override
  public List<TodoItem> findByDeadlineRange(LocalDate from, LocalDate to) {
    List<TodoItem> items = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_BY_DEADLINE_RANGE_SQL)) {

      ps.setString(1, from.toString());
      ps.setString(2, to.toString());

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          items.add(mapRow(rs));
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find TodoItems by deadline range", e);
    }

    return items;
  }

  @Override
  public List<TodoItem> findByTitle(String title) {
    List<TodoItem> items = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_BY_TITLE_SQL)) {

      ps.setString(1, "%" + title + "%");

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          items.add(mapRow(rs));
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find TodoItems by title", e);
    }

    return items;
  }
}
