package dev.cameloasa.controller;

import dev.cameloasa.model.Person;
import dev.cameloasa.service.PersonService;
import java.util.List;

public class PersonController {

  private final PersonService personService;

  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  public Person createPerson(String firstName, String lastName) {
    return personService.createPerson(firstName, lastName);
  }

  public Person getPersonById(int id) {
    return personService.findPersonById(id);
  }

  public List<Person> getAllPeople() {
    return personService.findAllPeople();
  }

  public List<Person> getPeopleByFirstName(String firstName) {
    return personService.findByFirstName(firstName);
  }

  public List<Person> getPeopleByLastName(String lastName) {
    return personService.findByLastName(lastName);
  }

  public boolean updatePerson(int id, String newFirstName, String newLastName) {
    return personService.updatePerson(id, newFirstName, newLastName);
  }

  public boolean deletePerson(int id) {
    return personService.deletePerson(id);
  }
}
