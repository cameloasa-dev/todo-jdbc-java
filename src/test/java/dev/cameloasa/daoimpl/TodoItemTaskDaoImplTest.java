package dev.cameloasa.daoimpl;

import static org.junit.jupiter.api.Assertions.*;

import dev.cameloasa.TestSetup;
import dev.cameloasa.model.TodoItemTask;
import java.util.Optional;
import org.junit.jupiter.api.*;

class TodoItemTaskDaoImplTest extends TestSetup {

  private TodoItemTaskDaoImpl dao;

  @BeforeEach
  void setup() {
    dao = new TodoItemTaskDaoImpl();
  }

  @Test
  void create_shouldInsertTaskIntoDatabase() {
    TodoItemTask task =
        new TodoItemTask(
            "Task title", "Task description", false, 0 // fără todo_item_id
            );

    TodoItemTask saved = dao.create(task);

    assertTrue(saved.getTaskId() > 0);
  }

  @Test
  void findById_shouldReturnTask_whenExists() {
    TodoItemTask task = new TodoItemTask("Find me", "Desc", false, 0);

    TodoItemTask saved = dao.create(task);

    Optional<TodoItemTask> found = dao.findById(saved.getTaskId());

    assertTrue(found.isPresent());
    assertEquals("Find me", found.get().getTitle());
  }

  @Test
  void update_shouldModifyTask() {
    TodoItemTask task = new TodoItemTask("Old title", "Old desc", false, 0);

    TodoItemTask saved = dao.create(task);

    saved.setTitle("New title");
    saved.setDescription("New desc");
    saved.setDone(true);

    boolean updated = dao.update(saved);

    assertTrue(updated);

    Optional<TodoItemTask> found = dao.findById(saved.getTaskId());
    assertEquals("New title", found.get().getTitle());
    assertEquals("New desc", found.get().getDescription());
    assertTrue(found.get().isDone());
  }

  @Test
  void delete_shouldRemoveTask() {
    TodoItemTask task = new TodoItemTask("Delete me", "Desc", false, 0);

    TodoItemTask saved = dao.create(task);

    boolean deleted = dao.deleteById(saved.getTaskId());

    assertTrue(deleted);
    assertFalse(dao.findById(saved.getTaskId()).isPresent());
  }
}
