package dev.cameloasa.dao;

import dev.cameloasa.model.TodoItemTask;
import java.util.List;
import java.util.Optional;

public interface TodoItemTaskDao {

  TodoItemTask create(TodoItemTask task);

  boolean update(TodoItemTask task);

  boolean deleteById(int taskId);

  Optional<TodoItemTask> findById(int taskId);

  List<TodoItemTask> findAll();

  List<TodoItemTask> findByTodoItemId(int todoItemId);

  List<TodoItemTask> findByDoneStatus(boolean done);

  List<TodoItemTask> findByTitle(String title);

  List<TodoItemTask> findOverdueTasks();
}
