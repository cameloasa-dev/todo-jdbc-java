package dev.cameloasa.controller;

import dev.cameloasa.model.TodoItemTask;
import dev.cameloasa.service.TodoItemTaskService;
import java.util.List;

public class TodoItemTaskController {

  private final TodoItemTaskService taskService;

  public TodoItemTaskController(TodoItemTaskService taskService) {
    this.taskService = taskService;
  }

  public TodoItemTask createTask(
      String title, String description, Boolean done, Integer todoItemId) {
    return taskService.createTask(title, description, done, todoItemId);
  }

  public TodoItemTask getTaskById(int id) {
    return taskService.findTaskById(id);
  }

  public List<TodoItemTask> getAllTasks() {
    return taskService.findAllTasks();
  }

  public List<TodoItemTask> getTasksByTodoItemId(int todoItemId) {
    return taskService.findTasksByTodoItemId(todoItemId);
  }

  public List<TodoItemTask> getTasksByDone(boolean done) {
    return taskService.findTasksByDone(done);
  }

  public List<TodoItemTask> getTasksByTitle(String title) {
    return taskService.findTasksByTitle(title);
  }

  public List<TodoItemTask> getOverdueTasks() {
    return taskService.findOverdueTasks();
  }

  public boolean updateTask(
      int id, String newTitle, String newDescription, Boolean newDone, Integer newTodoItemId) {
    return taskService.updateTask(id, newTitle, newDescription, newDone, newTodoItemId);
  }

  public boolean deleteTask(int id) {
    return taskService.deleteTask(id);
  }
}
