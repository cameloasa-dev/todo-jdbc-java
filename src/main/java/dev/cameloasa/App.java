package dev.cameloasa;

import dev.cameloasa.dao.daoimpl.PersonDao;
import dev.cameloasa.dao.daoimpl.PersonDaoImpl;
import dev.cameloasa.dao.daoimpl.TodoItemDao;
import dev.cameloasa.dao.daoimpl.TodoItemDaoImpl;
import dev.cameloasa.model.Person;
import dev.cameloasa.model.TodoItem;
import java.time.LocalDate;
import java.util.Collection;

public class App {

  public static void main(String[] args) {
    // Create an instance of PersonDaoImpl
    PersonDao personDao = new PersonDaoImpl();

    // Create a new person
    Person newPerson = new Person("John", "Doe");

    // Add the new person to the database
    Person createdPerson = personDao.create(newPerson);
    System.out.println("Created person: " + createdPerson);

    // Find a person by ID
    Person foundById = personDao.findById(createdPerson.getPerson_id());
    System.out.println("Found person by ID: " + foundById);

    // Find all people
    Collection<Person> allPeople = personDao.findAll();
    System.out.println("All people: " + allPeople);

    // Find people by first name
    Collection<Person> byFirstName = personDao.findByFirst_name("John");
    System.out.println("People with first name John: " + byFirstName);

    // Find people by last name
    Collection<Person> byLastName = personDao.findByLast_name("Doe");
    System.out.println("People with last name Doe: " + byLastName);
    // Update the person
    createdPerson.setFirst_name("Jane");
    createdPerson.setLast_name("Smith");
    Person updatedPerson = personDao.update(createdPerson);
    System.out.println("Updated person: " + updatedPerson);

    // Delete the person
    boolean deleted = personDao.delete(createdPerson.getPerson_id());
    System.out.println("Person deleted: " + deleted);

    // Create a new todoItem
    TodoItem newTodoItem =
        new TodoItem("Buy milk", "Go to the grocery store", LocalDate.now(), false, 1);

    // Create an instance of todoItemDao
    TodoItemDao todoItemDao = new TodoItemDaoImpl();

    // Add the new todoItem to the database
    TodoItem createdTodoItem = todoItemDao.create(newTodoItem);
    System.out.println("Created todoItem: " + createdTodoItem);

    // Find a todoItem by ID
    TodoItem findById = todoItemDao.findById(createdTodoItem.getTodo_id());
    System.out.println("Found todoItem by ID: " + findById);

    // Find all todoItems
    Collection<TodoItem> allTodoItems = todoItemDao.findAll();
    System.out.println("All todoItems: " + allTodoItems);

    // Find todoItems by done status
    Collection<TodoItem> byDoneStatus = todoItemDao.findByDoneStatus(false);
    System.out.println("TodoItems with done status false: " + byDoneStatus);

    // Find todoItems by assignee
    Collection<TodoItem> byAssignee = todoItemDao.findByAssignee(1);
    System.out.println("TodoItems with assignee: " + byAssignee);

    // Update the todoItem
    createdTodoItem.setAssignee_id(2);
    createdTodoItem.setDone(true);
    TodoItem updatedTodoItem = todoItemDao.update(createdTodoItem);
    System.out.println("Updated todoItem: " + updatedTodoItem);

    // Delete the todoItem
    boolean deletedTodoItem = todoItemDao.deleteById(createdTodoItem.getTodo_id());
    System.out.println("TodoItem deleted: " + deletedTodoItem);
  }
}
