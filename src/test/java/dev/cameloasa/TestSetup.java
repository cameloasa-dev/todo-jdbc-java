package dev.cameloasa;

import dev.cameloasa.config.DatabaseConfig;
import dev.cameloasa.daoimpl.PersonDaoImpl;
import dev.cameloasa.db.DatabaseInitializer;
import dev.cameloasa.model.Person;

import java.io.File;
import org.junit.jupiter.api.BeforeAll;

public class TestSetup {

  @BeforeAll
  static void globalSetup() {
    // Use a separate database for tests
    DatabaseConfig.DB_URL = "jdbc:sqlite:test.db";

    // Delete test.db if it exists to ensure a clean state for tests
    File f = new File("test.db");
    if (f.exists()) f.delete();

    // Create database table for test.db
    DatabaseInitializer.initialize();

    // ⭐ Seed test persons (FK fix)
    PersonDaoImpl personDao = new PersonDaoImpl();
    personDao.create(new Person("Test", "Person1")); // id = 1
    personDao.create(new Person("Test", "Person2")); // id = 2
    personDao.create(new Person("Test", "Person3")); // id = 3
  }
}
