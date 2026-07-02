package dev.cameloasa.dto.request;

import java.time.LocalDate;

public class TodoItemRequestDto {
  public String title;
  public String description;
  public LocalDate deadline;
  public Boolean done;
  public Integer assigneeId;
}
