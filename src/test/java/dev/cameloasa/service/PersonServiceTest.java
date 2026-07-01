package dev.cameloasa.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.cameloasa.dao.PersonDao;
import dev.cameloasa.model.Person;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class PersonServiceTest {

  @Test
  void createPerson_shouldThrowException_whenFirstNameIsEmpty() {
    PersonDao dao = mock(PersonDao.class);
    PersonService service = new PersonService(dao);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          service.createPerson("", "Smith");
        });
  }

  @Test
  void createPerson_shouldThrowException_whenLastNameIsEmpty() {
    PersonDao dao = mock(PersonDao.class);
    PersonService service = new PersonService(dao);

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          service.createPerson("John", "");
        });
  }

  @Test
  void createPerson_shouldReturnPerson_whenValidData() {
    PersonDao dao = mock(PersonDao.class);
    PersonService service = new PersonService(dao);

    Person expected = new Person("John", "Smith");
    when(dao.create(any())).thenReturn(expected);

    Person result = service.createPerson("John", "Smith");

    assertEquals("John", result.getFirstName());
    assertEquals("Smith", result.getLastName());
  }

  @Test
  void findPersonById_shouldThrowException_whenNotFound() {
    PersonDao dao = mock(PersonDao.class);
    PersonService service = new PersonService(dao);

    when(dao.findById(99)).thenReturn(Optional.empty());

    assertThrows(
        IllegalArgumentException.class,
        () -> {
          service.findPersonById(99);
        });
  }

  @Test
  void updatePerson_shouldUpdateFieldsCorrectly() {
    PersonDao dao = mock(PersonDao.class);
    PersonService service = new PersonService(dao);

    Person existing = new Person("John", "Smith");
    when(dao.findById(1)).thenReturn(Optional.of(existing));
    when(dao.update(any())).thenReturn(true);

    boolean updated = service.updatePerson(1, "Johnny", "S.");

    assertTrue(updated);
    assertEquals("Johnny", existing.getFirstName());
    assertEquals("S.", existing.getLastName());
  }
}
