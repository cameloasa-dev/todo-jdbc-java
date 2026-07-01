package dev.cameloasa;

import dev.cameloasa.controller.PersonController;
import dev.cameloasa.daoimpl.PersonDaoImpl;
import dev.cameloasa.model.Person;
import dev.cameloasa.service.PersonService;

public class App {

  public static void main(String[] args) {

    PersonController controller = new PersonController(new PersonService(new PersonDaoImpl()));

    System.out.println("=== TEST RUNNER ===");

    Person p = controller.createPerson("Test", "User");
    System.out.println("Created: " + p);

    System.out.println("All people: " + controller.getAllPeople());
  }
}
