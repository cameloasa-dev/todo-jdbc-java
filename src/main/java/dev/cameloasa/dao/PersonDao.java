package dev.cameloasa.dao;

import dev.cameloasa.model.Person;
import java.util.List;
import java.util.Optional;

public interface PersonDao {

  Person create(Person person);

  boolean update(Person person);

  boolean delete(int personId);

  Optional<Person> findById(int personId);

  List<Person> findAll();

  List<Person> findByFirstName(String firstName);

  List<Person> findByLastName(String lastName);
}
