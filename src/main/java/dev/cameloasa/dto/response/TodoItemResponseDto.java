package dev.cameloasa.dto.response;

import java.time.LocalDate;


public class TodoItemResponseDto {
  public int todoId;
  public String title;
  public String description;
  public LocalDate deadline;
  public boolean done;
  public int assigneeId;
  


  public TodoItemResponseDto(
      int todoId,
      String title,
      String description,
      LocalDate deadline,
      boolean done,
      int assigneeId
  ) {
    this.todoId = todoId;
    this.title = title;
    this.description = description;
    this.deadline = deadline;
    this.done = done;
    this.assigneeId = assigneeId;

  }
}


