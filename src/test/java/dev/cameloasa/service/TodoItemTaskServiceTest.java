package dev.cameloasa.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.cameloasa.dao.TodoItemTaskDao;
import dev.cameloasa.model.TodoItemTask;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class TodoItemTaskServiceTest {

  @Test
  void createTask_shouldThrowException_whenTitleIsEmpty() {
    TodoItemTaskDao dao = mock(TodoItemTaskDao.class);
    TodoItemTaskService service = new TodoItemTaskService(dao);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          service.createTask("", "desc", LocalDate.now(), 1);
        });
  }

  @Test
  void createTask_shouldThrowException_whenDeadlineIsNull() {
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

    TodoItemTask expected = new TodoItemTask("title", "desc", LocalDate.now(), false, 1);
    when(dao.create(any())).thenReturn(expected);

    TodoItemTask result = service.createTask("title", "desc", LocalDate.now(), 1);

    assertEquals("title", result.getTitle());
    assertEquals("desc", result.getDescription());
    assertEquals(1, result.getTodoItemId());
  }
}
