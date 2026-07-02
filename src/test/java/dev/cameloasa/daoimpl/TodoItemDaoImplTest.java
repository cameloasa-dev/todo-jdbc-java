package dev.cameloasa.daoimpl;

import static org.junit.jupiter.api.Assertions.*;

import dev.cameloasa.TestSetup;
import dev.cameloasa.model.TodoItem;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.*;

class TodoItemDaoImplTest extends TestSetup {

    private TodoItemDaoImpl dao;

    @BeforeEach
    void setup() {
        dao = new TodoItemDaoImpl();
    }

    private TodoItem createTestItem() {
        return dao.create(
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
    void create_shouldInsertTodoItemIntoDatabase() {
        TodoItem saved = createTestItem();
        assertTrue(saved.getTodoId() > 0);
    }

    @Test
    void findById_shouldReturnTodoItem_whenExists() {
        TodoItem saved = createTestItem();

        Optional<TodoItem> found = dao.findById(saved.getTodoId());

        assertTrue(found.isPresent());
        assertEquals("Test Item", found.get().getTitle());
    }

    @Test
    void update_shouldModifyTodoItem() {
        TodoItem saved = createTestItem();

        saved.setTitle("New title");
        saved.setDescription("New desc");
        saved.setDone(true);

        boolean updated = dao.update(saved);
        assertTrue(updated);

        Optional<TodoItem> found = dao.findById(saved.getTodoId());
        assertEquals("New title", found.get().getTitle());
        assertEquals("New desc", found.get().getDescription());
        assertTrue(found.get().isDone());
    }

    @Test
    void delete_shouldRemoveTodoItem() {
        TodoItem saved = createTestItem();

        boolean deleted = dao.deleteById(saved.getTodoId());
        assertTrue(deleted);

        assertFalse(dao.findById(saved.getTodoId()).isPresent());
    }
}
