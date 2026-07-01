package dev.cameloasa;

import dev.cameloasa.config.DatabaseConfig;
import dev.cameloasa.db.DatabaseInitializer;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;

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
    }
}
