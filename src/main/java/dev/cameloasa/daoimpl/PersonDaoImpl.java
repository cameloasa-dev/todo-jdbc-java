package dev.cameloasa.daoimpl;

import dev.cameloasa.dao.PersonDao;
import dev.cameloasa.db.ConnectionManager;
import dev.cameloasa.model.Person;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonDaoImpl implements PersonDao {

  private static final String INSERT_SQL =
      "INSERT INTO Person (first_name, last_name) VALUES (?, ?)";

  private static final String UPDATE_SQL =
      "UPDATE Person SET first_name = ?, last_name = ? WHERE person_id = ?";

  private static final String DELETE_SQL = "DELETE FROM Person WHERE person_id = ?";

  private static final String FIND_BY_ID_SQL = "SELECT * FROM Person WHERE person_id = ?";

  private static final String FIND_ALL_SQL = "SELECT * FROM Person";

  private static final String FIND_BY_FIRSTNAME_SQL = "SELECT * FROM Person WHERE first_name = ?";

  private static final String FIND_BY_LASTNAME_SQL = "SELECT * FROM Person WHERE last_name = ?";

  @Override
  public Person create(Person person) {
    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, person.getFirstName());
      ps.setString(2, person.getLastName());
      ps.executeUpdate();

      try (ResultSet rs = ps.getGeneratedKeys()) {
        if (rs.next()) {
          person.setPersonId(rs.getInt(1));
        }
      }

      return person;

    } catch (SQLException e) {
      throw new RuntimeException("Failed to create Person", e);
    }
  }

  @Override
  public Optional<Person> findById(int personId) {
    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL)) {

      ps.setInt(1, personId);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapRow(rs));
        }
      }

      return Optional.empty();

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find Person by ID", e);
    }
  }

  @Override
  public List<Person> findAll() {
    List<Person> people = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_ALL_SQL);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        people.add(mapRow(rs));
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find all Persons", e);
    }

    return people;
  }

  @Override
  public List<Person> findByFirstName(String firstName) {
    List<Person> people = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_BY_FIRSTNAME_SQL)) {

      ps.setString(1, firstName);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          people.add(mapRow(rs));
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find Persons by first name", e);
    }

    return people;
  }

  @Override
  public List<Person> findByLastName(String lastName) {
    List<Person> people = new ArrayList<>();

    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(FIND_BY_LASTNAME_SQL)) {

      ps.setString(1, lastName);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          people.add(mapRow(rs));
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("Failed to find Persons by last name", e);
    }

    return people;
  }

  @Override
  public boolean update(Person person) {
    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

      ps.setString(1, person.getFirstName());
      ps.setString(2, person.getLastName());
      ps.setInt(3, person.getPersonId());

      return ps.executeUpdate() > 0;

    } catch (SQLException e) {
      throw new RuntimeException("Failed to update Person", e);
    }
  }

  @Override
  public boolean delete(int personId) {
    try (Connection conn = ConnectionManager.getConnection();
        PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

      ps.setInt(1, personId);
      return ps.executeUpdate() > 0;

    } catch (SQLException e) {
      throw new RuntimeException("Failed to delete Person", e);
    }
  }

  private Person mapRow(ResultSet rs) throws SQLException {
    return new Person(
        rs.getInt("person_id"), rs.getString("first_name"), rs.getString("last_name"));
  }
}
