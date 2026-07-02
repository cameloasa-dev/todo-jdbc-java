package dev.cameloasa.daoimpl;

import static org.junit.jupiter.api.Assertions.*;

import dev.cameloasa.TestSetup;
import dev.cameloasa.model.Person;
import java.util.Optional;
import org.junit.jupiter.api.*;

class PersonDaoImplTest extends TestSetup {

    private PersonDaoImpl dao;

    @BeforeEach
    void setup() {
        dao = new PersonDaoImpl();
    }

    @Test
    void create_shouldInsertPersonIntoDatabase() {
        Person p = new Person("John", "Smith");
        Person saved = dao.create(p);

        assertTrue(saved.getPersonId() > 0);
    }

    @Test
    void findById_shouldReturnPerson_whenExists() {
        Person p = new Person("Anna", "Brown");
        Person saved = dao.create(p);

        Optional<Person> found = dao.findById(saved.getPersonId());

        assertTrue(found.isPresent());
        assertEquals("Anna", found.get().getFirstName());
    }

    @Test
    void update_shouldModifyPerson() {
        Person p = new Person("Old", "Name");
        Person saved = dao.create(p);

        saved.setFirstName("New");
        saved.setLastName("Name2");

        boolean updated = dao.update(saved);
        assertTrue(updated);

        Optional<Person> found = dao.findById(saved.getPersonId());
        assertEquals("New", found.get().getFirstName());
        assertEquals("Name2", found.get().getLastName());
    }

    @Test
    void delete_shouldRemovePerson() {
        Person p = new Person("Delete", "Me");
        Person saved = dao.create(p);

        boolean deleted = dao.delete(saved.getPersonId());
        assertTrue(deleted);

        assertFalse(dao.findById(saved.getPersonId()).isPresent());
    }
}
