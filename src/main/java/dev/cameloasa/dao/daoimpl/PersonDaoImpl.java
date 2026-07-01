package dev.cameloasa.dao.daoimpl;

import dev.cameloasa.db.MySQLConnection;
import dev.cameloasa.model.Person;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersonDaoImpl implements PersonDao {

  private Connection connection;

  // Extract Person from ResultSet
  private Person extractPersonFromResultSet(ResultSet resultSet) throws SQLException {
    // get - id, first_name, last_name - return Person
    int person_id = resultSet.getInt("person_id");
    String first_name = resultSet.getString("first_name");
    String last_name = resultSet.getString("last_name");
    return new Person(person_id, first_name, last_name);
  }

  // Constructor
  public PersonDaoImpl() {
    this.connection = MySQLConnection.getConnection();
  }

  @Override
  public Person create(Person person) {
    // SQL - INSERT INTO Person (first_ame, last_name) VALUES (?, ?)
    String query = "INSERT INTO Person (first_name, last_name) VALUES (?, ?)";
    // Prepare statement
    try (PreparedStatement statement =
        connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
      // Set parameters
      statement.setString(1, person.getFirst_name());
      statement.setString(2, person.getLast_name());
      // Execute
      int affectedRows = statement.executeUpdate();
      // Check
      if (affectedRows == 0) {
        throw new SQLException("Creating person failed, no rows affected.");
      }
      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          person.setId(generatedKeys.getInt(1));
        } else {
          throw new SQLException("Creating person failed, no ID obtained.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return person;
  }

  @Override
  public Person findById(int person_id) {
    // SQL - SELECT * FROM Person WHERE person_id = ?
    String query = "SELECT * FROM Person WHERE person_id = ?";
    try {
      // Prepare statement
      PreparedStatement statement = connection.prepareStatement(query);
      // Set parameters
      statement.setInt(1, person_id);
      // Execute
      ResultSet resultSet = statement.executeQuery();
      // Check
      if (resultSet.next()) {
        return extractPersonFromResultSet(resultSet);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Collection<Person> findAll() {
    // Create a list
    List<Person> people = new ArrayList<>();
    // SQL - SELECT * FROM Person
    String query = "SELECT * FROM Person";
    try {
      // Prepare statement
      PreparedStatement statement = connection.prepareStatement(query);
      // Execute
      ResultSet resultSet = statement.executeQuery();
      // Check
      while (resultSet.next()) {
        // Add
        people.add(extractPersonFromResultSet(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return people;
  }

  @Override
  public Collection<Person> findByFirst_name(String first_name) {
    // Create a list
    List<Person> people = new ArrayList<>();
    // SQL - SELECT * FROM Person WHERE first_name = ?
    String query = "SELECT * FROM Person WHERE first_name = ?";
    // Prepare statement
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      // Set parameters
      statement.setString(1, first_name);
      // Execute
      ResultSet resultSet = statement.executeQuery();
      // Check
      while (resultSet.next()) {
        people.add(extractPersonFromResultSet(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return people;
  }

  @Override
  public Collection<Person> findByLast_name(String last_name) {
    // Create a list
    List<Person> people = new ArrayList<>();
    // SQL - SELECT * FROM Person WHERE last_name = ?
    String query = "SELECT * FROM Person WHERE last_name = ?";
    // Prepare statement
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      // Set parameters
      statement.setString(1, last_name);
      // Execute
      ResultSet resultSet = statement.executeQuery();
      // Check
      while (resultSet.next()) {
        people.add(extractPersonFromResultSet(resultSet));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return people;
  }

  @Override
  public Person update(Person person) {
    // SQL - UPDATE Person SET first_ame = ?, last_name = ? WHERE person_id = ?
    String query = "UPDATE Person SET first_name = ?, last_name = ? WHERE person_id = ?";
    // Prepare statement
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      // Set parameters
      statement.setString(1, person.getFirst_name());
      statement.setString(2, person.getLast_name());
      statement.setInt(3, person.getPerson_id());
      // Execute
      int affectedRows = statement.executeUpdate();
      // Check
      if (affectedRows == 0) {
        throw new SQLException("Updating person failed, no rows affected.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public boolean delete(int person_id) {
    // SQL - DELETE FROM Person WHERE person_id = ?
    String query = "DELETE FROM Person WHERE person_id = ?";
    // Prepare statement
    try (PreparedStatement statement = connection.prepareStatement(query)) {
      // Set parameters
      statement.setInt(1, person_id);
      // Execute
      int affectedRows = statement.executeUpdate();
      // Check
      if (affectedRows == 0) {
        throw new SQLException("Deleting person failed, no rows affected.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}
