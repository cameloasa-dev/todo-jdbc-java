package dev.cameloasa.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.cameloasa.dao.TodoItemTaskDao;
import dev.cameloasa.model.TodoItemTask;
import org.junit.jupiter.api.Test;

class TodoItemTaskServiceTest {

  @Test
  void createTask_shouldThrowException_whenTitleIsEmpty() {
    TodoItemTaskDao dao = mock(TodoItemTaskDao.class);
    TodoItemTaskService service = new TodoItemTaskService(dao);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          service.createTask("", "desc", false, 1);
        });
  }

  @Test
  void createTask_shouldThrowException_whenDoneIsNull() {
    TodoItemTaskDao dao = mock(TodoItemTaskDao.class);
    TodoItemTaskService service = new TodoItemTaskService(dao);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          service.createTask("title", "desc", null, 1);
        });
  }

  @Test
  void createTask_shouldReturnTask_whenValidData() {
    TodoItemTaskDao dao = mock(TodoItemTaskDao.class);
    TodoItemTaskService service = new TodoItemTaskService(dao);

    TodoItemTask expected = new TodoItemTask("title", "desc", false, 1);
    when(dao.create(any())).thenReturn(expected);

    TodoItemTask result = service.createTask("title", "desc", false, 1);

    assertEquals("title", result.getTitle());
    assertEquals("desc", result.getDescription());
    assertEquals(1, result.getTodoItemId());
  }
}
