package dev.cameloasa.dto.response;

public class TodoItemTaskResponseDto {
  public int taskId;
  public String title;
  public String description;
  public boolean done;
  public int todoItemId;

  public TodoItemTaskResponseDto(
      int taskId, String title, String description, boolean done, int todoItemId) {
    this.taskId = taskId;
    this.title = title;
    this.description = description;
    this.done = done;
    this.todoItemId = todoItemId;
  }
}
