package dev.cameloasa.model;

public class TodoItemTask {

  private int taskId;
  private String title;
  private String description;
  private boolean done;
  private int todoItemId;

  public TodoItemTask() {}

  public TodoItemTask(String title, String description, boolean done, int todoItemId) {
    this.title = title;
    this.description = description;
    this.done = done;
    this.todoItemId = todoItemId;
  }

  public TodoItemTask(String title, String description, int todoItemId) {
    this.title = title;
    this.description = description;
    this.todoItemId = todoItemId;
  }

  public TodoItemTask(int taskId, String title, String description, boolean done, int todoItemId) {
    this.taskId = taskId;
    this.title = title;
    this.description = description;
    this.done = done;
    this.todoItemId = todoItemId;
  }

  public int getTaskId() {
    return taskId;
  }

  public void setTaskId(int taskId) {
    this.taskId = taskId;
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

  public boolean isDone() {
    return done;
  }

  public void setDone(boolean done) {
    this.done = done;
  }

  public int getTodoItemId() {
    return todoItemId;
  }

  public void setTodoItemId(int todoItemId) {
    this.todoItemId = todoItemId;
  }

  @Override
  public String toString() {
    return "TodoItemTask{"
        + "taskId="
        + taskId
        + ", title='"
        + title
        + '\''
        + ", description='"
        + description
        + '\''
        + ", done="
        + done
        + ", todoItemId="
        + todoItemId
        + '}';
  }
}
