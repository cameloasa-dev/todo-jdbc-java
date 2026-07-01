package dev.cameloasa.model;

import java.time.LocalDate;

public class TodoItem {

  private int todoId;
  private String title;
  private String description;
  private LocalDate deadline;
  private boolean done;
  private int assigneeId;

  public TodoItem() {}

  public TodoItem(
      String title, String description, LocalDate deadline, boolean done, int assigneeId) {
    this.title = title;
    this.description = description;
    this.deadline = deadline;
    this.done = done;
    this.assigneeId = assigneeId;
  }

  public TodoItem(String title, String description, LocalDate deadline, int assigneeId) {
    this.title = title;
    this.description = description;
    this.deadline = deadline;
    this.assigneeId = assigneeId;
  }

  public TodoItem(
      int todoId,
      String title,
      String description,
      LocalDate deadline,
      boolean done,
      int assigneeId) {
    this.todoId = todoId;
    this.title = title;
    this.description = description;
    this.deadline = deadline;
    this.done = done;
    this.assigneeId = assigneeId;
  }

  public int getTodoId() {
    return todoId;
  }

  public void setTodoId(int todoId) {
    this.todoId = todoId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getDeadline() {
    return deadline;
  }

  public void setDeadline(LocalDate deadline) {
    this.deadline = deadline;
  }

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  public int getAssigneeId() {
    return assigneeId;
  }

  public void setAssigneeId(int assigneeId) {
    this.assigneeId = assigneeId;
  }

  public boolean isAssignee(int personId) {
    return this.assigneeId == personId;
  }

  @Override
  public String toString() {
    return "TodoItem{"
        + "todoId="
        + todoId
        + ", title='"
        + title
        + '\''
        + ", description='"
        + description
        + '\''
        + ", deadline="
        + deadline
        + ", done="
        + done
        + ", assigneeId="
        + assigneeId
        + '}';
  }
}
