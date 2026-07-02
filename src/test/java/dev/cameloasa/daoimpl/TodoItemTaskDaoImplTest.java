package dev.cameloasa.daoimpl;

import static org.junit.jupiter.api.Assertions.*;

import dev.cameloasa.TestSetup;
import dev.cameloasa.model.TodoItem;
import dev.cameloasa.model.TodoItemTask;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.*;

class TodoItemTaskDaoImplTest extends TestSetup {

    private TodoItemTaskDaoImpl taskDao;
    private TodoItemDaoImpl itemDao;

    @BeforeEach
    void setup() {
        taskDao = new TodoItemTaskDaoImpl();
        itemDao = new TodoItemDaoImpl();
    }

    private TodoItem createTestItem() {
        return itemDao.create(
            new TodoItem(
                "Test Item",
                "Item description",
                LocalDate.now(),
                false,
                1   // VALID FK
            )
        );
    }

    @Test
    void create_shouldInsertTaskIntoDatabase() {
        TodoItem item = createTestItem();

        TodoItemTask task = new TodoItemTask(
                "Task title",
                "Task description",
                false,
                item.getTodoId()
        );

        TodoItemTask saved = taskDao.create(task);

        assertTrue(saved.getTaskId() > 0);
    }

    @Test
    void findById_shouldReturnTask_whenExists() {
        TodoItem item = createTestItem();

        TodoItemTask task = new TodoItemTask(
                "Find me",
                "Desc",
                false,
                item.getTodoId()
        );

        TodoItemTask saved = taskDao.create(task);

        Optional<TodoItemTask> found = taskDao.findById(saved.getTaskId());

        assertTrue(found.isPresent());
        assertEquals("Find me", found.get().getTitle());
    }

    @Test
    void update_shouldModifyTask() {
        TodoItem item = createTestItem();

        TodoItemTask task = new TodoItemTask(
                "Old title",
                "Old desc",
                false,
                item.getTodoId()
        );

        TodoItemTask saved = taskDao.create(task);

        saved.setTitle("New title");
        saved.setDescription("New desc");
        saved.setDone(true);

        boolean updated = taskDao.update(saved);
        assertTrue(updated);

        Optional<TodoItemTask> found = taskDao.findById(saved.getTaskId());
        assertEquals("New title", found.get().getTitle());
        assertEquals("New desc", found.get().getDescription());
        assertTrue(found.get().isDone());
    }

    @Test
    void delete_shouldRemoveTask() {
        TodoItem item = createTestItem();

        TodoItemTask task = new TodoItemTask(
                "Delete me",
                "Desc",
                false,
                item.getTodoId()
        );

        TodoItemTask saved = taskDao.create(task);

        boolean deleted = taskDao.deleteById(saved.getTaskId());
        assertTrue(deleted);

        assertFalse(taskDao.findById(saved.getTaskId()).isPresent());
    }
}
