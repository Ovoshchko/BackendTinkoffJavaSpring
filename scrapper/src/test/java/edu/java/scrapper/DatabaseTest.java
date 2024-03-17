package edu.java.scrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.annotation.Rollback;
import static edu.java.scrapper.IntegrationTest.POSTGRES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class DatabaseTest {

    //Мы шпионы, никто не узнает, что мы селектили)
    @ParameterizedTest
    @ValueSource(strings = {"users", "links", "userlink"})
    public void isTablesPresent(String table) {
        try (Connection connection = POSTGRES.createConnection("")) {
            PreparedStatement statement =
                connection.prepareStatement(
                    "SELECT table_name FROM information_schema.tables WHERE table_schema = 'linkviewer' AND table_name = ?");
            statement.setString(1, table);
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
            assertEquals(table, resultSet.getString("table_name"));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Rollback
    public void simpleTest() {
        try (Connection connection = POSTGRES.createConnection("")) {
            String query = "INSERT INTO linkviewer.users (tg_id, created_at) VALUES  (3456, now());";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                System.out.println(statement.execute());
            }
            String selectQuery = "SELECT * FROM linkviewer.users;";
            try (ResultSet result = connection.prepareStatement(selectQuery).executeQuery()) {
                assertTrue(result.next());
                assertEquals(1L, result.getLong("id"));
                assertEquals(3456L, result.getLong("tg_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
