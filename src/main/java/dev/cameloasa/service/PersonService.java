package dev.cameloasa.service;

import dev.cameloasa.dao.PersonDao;
import dev.cameloasa.model.Person;
import java.util.List;

public class PersonService {

  private final PersonDao personDao;

  public PersonService(PersonDao personDao) {
    this.personDao = personDao;
  }

  public Person createPerson(String firstName, String lastName) {

    if (firstName == null || firstName.isBlank()) {
      throw new IllegalArgumentException("First name cannot be empty");
    }

    if (lastName == null || lastName.isBlank()) {
      throw new IllegalArgumentException("Last name cannot be empty");
    }

    Person person = new Person(firstName, lastName);
    return personDao.create(person);
  }

  public Person findPersonById(int id) {
    return personDao
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Person not found: " + id));
  }

  public List<Person> findAllPeople() {
    return personDao.findAll();
  }

  public List<Person> findByFirstName(String firstName) {
    return personDao.findByFirstName(firstName);
  }

  public List<Person> findByLastName(String lastName) {
    return personDao.findByLastName(lastName);
  }

  public boolean updatePerson(int id, String newFirstName, String newLastName) {

    Person person = findPersonById(id);

    if (newFirstName != null && !newFirstName.isBlank()) {
      person.setFirstName(newFirstName);
    }

    if (newLastName != null && !newLastName.isBlank()) {
      person.setLastName(newLastName);
    }

    return personDao.update(person);
  }

  public boolean deletePerson(int id) {
    return personDao.delete(id);
  }
}
