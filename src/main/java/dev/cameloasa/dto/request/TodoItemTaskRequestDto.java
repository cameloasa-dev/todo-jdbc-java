package dev.cameloasa.dto.request;

import java.time.LocalDate;

public class TodoItemTaskRequestDto {
  public String title;
  public String description;
  public boolean done;
  public int todoItemId;
  public LocalDate deadline;
}
