package dev.cameloasa.web;

import dev.cameloasa.controller.TodoItemController;
import dev.cameloasa.daoimpl.TodoItemDaoImpl;
import dev.cameloasa.dto.request.TodoItemRequestDto;
import dev.cameloasa.dto.response.TodoItemResponseDto;
import dev.cameloasa.model.TodoItem;
import dev.cameloasa.service.TodoItemService;
import io.javalin.Javalin;
import java.time.LocalDate;
import java.util.List;

public class TodoItemWebController {

  private static final TodoItemController controller =
      new TodoItemController(new TodoItemService(new TodoItemDaoImpl()));

  public static void registerRoutes(Javalin app) {

    // GET /items
    app.get(
        "/items",
        ctx -> {
          List<TodoItem> items = controller.getAllItems();

          List<TodoItemResponseDto> response =
              items.stream()
                  .map(
                      i ->
                          new TodoItemResponseDto(
                              i.getTodoId(),
                              i.getTitle(),
                              i.getDescription(),
                              i.getDeadline(),
                              i.isDone(),
                              i.getAssigneeId()))
                  .toList();

          ctx.json(response);
        });

    // GET /items/{id}
    app.get(
        "/items/{id}",
        ctx -> {
          int id = Integer.parseInt(ctx.pathParam("id"));
          TodoItem item = controller.getItemById(id);

          TodoItemResponseDto response =
              new TodoItemResponseDto(
                  item.getTodoId(),
                  item.getTitle(),
                  item.getDescription(),
                  item.getDeadline(),
                  item.isDone(),
                  item.getAssigneeId());

          ctx.json(response);
        });

    // POST /items
    app.post(
        "/items",
        ctx -> {
          TodoItemRequestDto dto = ctx.bodyAsClass(TodoItemRequestDto.class);

          TodoItem created =
              controller.createItem(dto.title, dto.description, dto.deadline, dto.assigneeId);

          TodoItemResponseDto response =
              new TodoItemResponseDto(
                  created.getTodoId(),
                  created.getTitle(),
                  created.getDescription(),
                  created.getDeadline(),
                  created.isDone(),
                  created.getAssigneeId());

          ctx.json(response);
        });

    // PATCH /items/{id}
    app.patch(
        "/items/{id}",
        ctx -> {
          int id = Integer.parseInt(ctx.pathParam("id"));
          TodoItemRequestDto dto = ctx.bodyAsClass(TodoItemRequestDto.class);

          boolean updated =
              controller.updateItem(
                  id, dto.title, dto.description, dto.deadline, dto.done, dto.assigneeId);

          ctx.json(updated);
        });

    // DELETE /items/{id}
    app.delete(
        "/items/{id}",
        ctx -> {
          int id = Integer.parseInt(ctx.pathParam("id"));
          boolean deleted = controller.deleteItem(id);
          ctx.json(deleted);
        });

    // GET /items/done/{status}
    app.get(
        "/items/done/{status}",
        ctx -> {
          boolean status = Boolean.parseBoolean(ctx.pathParam("status"));
          List<TodoItem> items = controller.getItemsByDone(status);

          List<TodoItemResponseDto> response =
              items.stream()
                  .map(
                      i ->
                          new TodoItemResponseDto(
                              i.getTodoId(),
                              i.getTitle(),
                              i.getDescription(),
                              i.getDeadline(),
                              i.isDone(),
                              i.getAssigneeId()))
                  .toList();

          ctx.json(response);
        });

    // GET /items/unassigned
    app.get(
        "/items/unassigned",
        ctx -> {
          List<TodoItem> items = controller.getUnassignedItems();

          List<TodoItemResponseDto> response =
              items.stream()
                  .map(
                      i ->
                          new TodoItemResponseDto(
                              i.getTodoId(),
                              i.getTitle(),
                              i.getDescription(),
                              i.getDeadline(),
                              i.isDone(),
                              i.getAssigneeId()))
                  .toList();

          ctx.json(response);
        });

    // GET /items/overdue
    app.get(
        "/items/overdue",
        ctx -> {
          List<TodoItem> items = controller.getOverdueItems();

          List<TodoItemResponseDto> response =
              items.stream()
                  .map(
                      i ->
                          new TodoItemResponseDto(
                              i.getTodoId(),
                              i.getTitle(),
                              i.getDescription(),
                              i.getDeadline(),
                              i.isDone(),
                              i.getAssigneeId()))
                  .toList();

          ctx.json(response);
        });

    // GET /items/deadline/{date}
    app.get(
        "/items/deadline/{date}",
        ctx -> {
          LocalDate date = LocalDate.parse(ctx.pathParam("date"));
          List<TodoItem> items = controller.getItemsByDeadline(date);

          List<TodoItemResponseDto> response =
              items.stream()
                  .map(
                      i ->
                          new TodoItemResponseDto(
                              i.getTodoId(),
                              i.getTitle(),
                              i.getDescription(),
                              i.getDeadline(),
                              i.isDone(),
                              i.getAssigneeId()))
                  .toList();

          ctx.json(response);
        });

    // GET /items/title/{title}
    app.get(
        "/items/title/{title}",
        ctx -> {
          String title = ctx.pathParam("title");
          List<TodoItem> items = controller.getItemsByTitle(title);

          List<TodoItemResponseDto> response =
              items.stream()
                  .map(
                      i ->
                          new TodoItemResponseDto(
                              i.getTodoId(),
                              i.getTitle(),
                              i.getDescription(),
                              i.getDeadline(),
                              i.isDone(),
                              i.getAssigneeId()))
                  .toList();

          ctx.json(response);
        });
  }
}
