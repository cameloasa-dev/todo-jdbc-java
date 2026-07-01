package dev.cameloasa.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.cameloasa.dao.TodoItemDao;
import dev.cameloasa.model.TodoItem;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TodoItemServiceTest {

  @Test
  void createItem_shouldThrowException_whenTitleIsEmpty() {
    TodoItemDao dao = mock(TodoItemDao.class);
    TodoItemService service = new TodoItemService(dao);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          service.createItem("", "desc", LocalDate.now(), 1);
        });
  }

  @Test
  void createItem_shouldThrowException_whenDeadlineIsNull() {
    TodoItemDao dao = mock(TodoItemDao.class);
    TodoItemService service = new TodoItemService(dao);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          service.createItem("title", "desc", null, 1);
        });
  }

  @Test
  void createItem_shouldReturnItem_whenValidData() {
    TodoItemDao dao = mock(TodoItemDao.class);
    TodoItemService service = new TodoItemService(dao);

    TodoItem expected = new TodoItem("title", "desc", LocalDate.now(), false, 1);
    when(dao.create(any())).thenReturn(expected);

    TodoItem result = service.createItem("title", "desc", LocalDate.now(), 1);

    assertEquals("title", result.getTitle());
    assertEquals("desc", result.getDescription());
  }

  @Test
  void findItemById_shouldThrowException_whenNotFound() {
    TodoItemDao dao = mock(TodoItemDao.class);
    TodoItemService service = new TodoItemService(dao);

    when(dao.findById(99)).thenReturn(Optional.empty());

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          service.findItemById(99);
        });
  }

  @Test
  void updateItem_shouldUpdateFieldsCorrectly() {
    TodoItemDao dao = mock(TodoItemDao.class);
    TodoItemService service = new TodoItemService(dao);

    TodoItem existing = new TodoItem("Old", "Desc", LocalDate.now(), false, 1);
    when(dao.findById(1)).thenReturn(Optional.of(existing));
    when(dao.update(any())).thenReturn(true);

    boolean updated = service.updateItem(1, "New", "NewDesc", LocalDate.now().plusDays(1), true, 2);

    assertTrue(updated);
    assertEquals("New", existing.getTitle());
    assertEquals("NewDesc", existing.getDescription());
    assertEquals(2, existing.getAssigneeId());
    assertTrue(existing.isDone());
  }
}
