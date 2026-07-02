package dev.cameloasa.dto.response;

import java.time.LocalDate;

public class TodoItemTaskResponseDto {
  public int taskId;
  public String title;
  public String description;
  public boolean done;
  public int todoItemId;
  public LocalDate deadline;

  public TodoItemTaskResponseDto(
      int taskId,
      String title,
      String description,
      boolean done,
      int todoItemId,
      LocalDate deadline) {
    this.taskId = taskId;
    this.title = title;
    this.description = description;
    this.done = done;
    this.todoItemId = todoItemId;
    this.deadline = deadline;
  }
}
