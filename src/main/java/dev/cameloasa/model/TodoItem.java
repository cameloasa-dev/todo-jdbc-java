package dev.cameloasa.model;

import java.time.LocalDate;

public class TodoItem {
  private int todo_id;
  private String title;
  private String description;
  private LocalDate deadline;
  private boolean done;
  private int assignee_id;

  public TodoItem() {}

  public TodoItem(
      String title, String description, LocalDate deadline, boolean done, int assignee_id) {
    this.todo_id = todo_id;
    this.title = title;
    this.description = description;
    this.deadline = deadline;
    this.done = done;
    this.assignee_id = assignee_id;
  }

  public TodoItem(String title, String description, LocalDate deadline, int assignee_id) {
    this.title = title;
    this.description = description;
    this.deadline = deadline;
    this.assignee_id = assignee_id;
  }

  // Getters

  public int getTodo_id() {
    return todo_id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getDeadline() {
    return deadline;
  }

  public boolean isDone() {
    return done;
  }

  public int getAssignee_id() {
    return assignee_id;
  }

  // Setters

  public void setTodo_id(int todo_id) {
    this.todo_id = todo_id;
  }

  public void setAssignee_id(int assignee_id) {
    this.assignee_id = assignee_id;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  public boolean isAssignee(int person_id) {
    return this.assignee_id == person_id;
  }

  // ToString

  @Override
  public String toString() {
    return "TodoItem{"
        + "todo_id="
        + todo_id
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
        + ", assignee_id="
        + assignee_id
        + '}';
  }
}
