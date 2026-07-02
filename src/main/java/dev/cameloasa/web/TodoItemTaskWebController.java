package dev.cameloasa.web;

import dev.cameloasa.controller.TodoItemTaskController;
import dev.cameloasa.daoimpl.TodoItemTaskDaoImpl;
import dev.cameloasa.dto.request.TodoItemTaskRequestDto;
import dev.cameloasa.dto.response.TodoItemTaskResponseDto;
import dev.cameloasa.model.TodoItemTask;
import dev.cameloasa.service.TodoItemTaskService;
import io.javalin.Javalin;
import java.util.List;

public class TodoItemTaskWebController {

  private static final TodoItemTaskController controller =
      new TodoItemTaskController(new TodoItemTaskService(new TodoItemTaskDaoImpl()));

  public static void registerRoutes(Javalin app) {

    // GET /tasks
    app.get(
        "/tasks",
        ctx -> {
          List<TodoItemTask> tasks = controller.getAllTasks();

          List<TodoItemTaskResponseDto> response =
              tasks.stream()
                  .map(
                      t ->
                          new TodoItemTaskResponseDto(
                              t.getTaskId(),
                              t.getTitle(),
                              t.getDescription(),
                              t.isDone(),
                              t.getTodoItemId(),
                              t.getDeadline()))
                  .toList();

          ctx.json(response);
        });

    // GET /tasks/{id}
    app.get(
        "/tasks/{id}",
        ctx -> {
          int id = Integer.parseInt(ctx.pathParam("id"));
          TodoItemTask task = controller.getTaskById(id);

          TodoItemTaskResponseDto response =
              new TodoItemTaskResponseDto(
                  task.getTaskId(),
                  task.getTitle(),
                  task.getDescription(),
                  task.isDone(),
                  task.getTodoItemId(),
                  task.getDeadline());

          ctx.json(response);
        });

    // POST /tasks
    app.post(
        "/tasks",
        ctx -> {
          TodoItemTaskRequestDto dto = ctx.bodyAsClass(TodoItemTaskRequestDto.class);

          TodoItemTask created =
              controller.createTask(dto.title, dto.description, dto.deadline, dto.todoItemId);

          TodoItemTaskResponseDto response =
              new TodoItemTaskResponseDto(
                  created.getTaskId(),
                  created.getTitle(),
                  created.getDescription(),
                  created.isDone(),
                  created.getTodoItemId(),
                  created.getDeadline());

          ctx.status(201).json(response);
        });

    // PATCH /tasks/{id}
    app.patch(
        "/tasks/{id}",
        ctx -> {
          int id = Integer.parseInt(ctx.pathParam("id"));
          TodoItemTaskRequestDto dto = ctx.bodyAsClass(TodoItemTaskRequestDto.class);

          boolean updated =
              controller.updateTask(
                  id, dto.title, dto.description, dto.deadline, dto.done, dto.todoItemId);

          ctx.json(updated);
        });

    // DELETE /tasks/{id}
    app.delete(
        "/tasks/{id}",
        ctx -> {
          int id = Integer.parseInt(ctx.pathParam("id"));
          boolean deleted = controller.deleteTask(id);
          ctx.json(deleted);
        });

    // GET /tasks/todoitem/{todoItemId}
    app.get(
        "/tasks/todoitem/{todoItemId}",
        ctx -> {
          int todoItemId = Integer.parseInt(ctx.pathParam("todoItemId"));
          List<TodoItemTask> tasks = controller.getTasksByTodoItemId(todoItemId);

          List<TodoItemTaskResponseDto> response =
              tasks.stream()
                  .map(
                      t ->
                          new TodoItemTaskResponseDto(
                              t.getTaskId(),
                              t.getTitle(),
                              t.getDescription(),
                              t.isDone(),
                              t.getTodoItemId(),
                              t.getDeadline()))
                  .toList();

          ctx.json(response);
        });

    // GET /tasks/done/{status}
    app.get(
        "/tasks/done/{status}",
        ctx -> {
          boolean status = Boolean.parseBoolean(ctx.pathParam("status"));
          List<TodoItemTask> tasks = controller.getTasksByDone(status);

          List<TodoItemTaskResponseDto> response =
              tasks.stream()
                  .map(
                      t ->
                          new TodoItemTaskResponseDto(
                              t.getTaskId(),
                              t.getTitle(),
                              t.getDescription(),
                              t.isDone(),
                              t.getTodoItemId(),
                              t.getDeadline()))
                  .toList();

          ctx.json(response);
        });

    // GET /tasks/title/{title}
    app.get(
        "/tasks/title/{title}",
        ctx -> {
          String title = ctx.pathParam("title");
          List<TodoItemTask> tasks = controller.getTasksByTitle(title);

          List<TodoItemTaskResponseDto> response =
              tasks.stream()
                  .map(
                      t ->
                          new TodoItemTaskResponseDto(
                              t.getTaskId(),
                              t.getTitle(),
                              t.getDescription(),
                              t.isDone(),
                              t.getTodoItemId(),
                              t.getDeadline()))
                  .toList();

          ctx.json(response);
        });

    // GET /tasks/overdue
    app.get(
        "/tasks/overdue",
        ctx -> {
          List<TodoItemTask> tasks = controller.getOverdueTasks();

          List<TodoItemTaskResponseDto> response =
              tasks.stream()
                  .map(
                      t ->
                          new TodoItemTaskResponseDto(
                              t.getTaskId(),
                              t.getTitle(),
                              t.getDescription(),
                              t.isDone(),
                              t.getTodoItemId(),
                              t.getDeadline()))
                  .toList();

          ctx.json(response);
        });
  }
}
