package dev.cameloasa.dao;

import dev.cameloasa.model.Person;
import dev.cameloasa.model.TodoItem;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoItemDao {

  TodoItem create(TodoItem todoItem);

  boolean update(TodoItem todoItem);

  boolean deleteById(int todoId);

  Optional<TodoItem> findById(int todoId);

  List<TodoItem> findAll();

  List<TodoItem> findByDoneStatus(boolean done);

  List<TodoItem> findByAssigneeId(int assigneeId);

  List<TodoItem> findByAssignee(Person assignee);

  List<TodoItem> findUnassigned();

  List<TodoItem> findByDeadline(LocalDate deadline);

  List<TodoItem> findOverdue();

  List<TodoItem> findByDeadlineRange(LocalDate from, LocalDate to);

  List<TodoItem> findByTitle(String title);
}
