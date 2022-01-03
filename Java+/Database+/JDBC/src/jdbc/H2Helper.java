package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

public class H2Helper {
    public static String randomTableName() {
        return "Person_" + UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String createPersonTable(String table) {
        return format("CREATE TABLE %s(id INTEGER, name VARCHAR)", table);
    }

    public static String insertJohn(String table) {
        return format("INSERT INTO %s(id, name) VALUES (1, 'John')", table);
    }

    public static String insertMary(String table) {
        return format("INSERT INTO %s(id, name) VALUES (2, 'Mary')", table);
    }

    public static String selectAll(String table) {
        return format("SELECT id, name FROM %s", table);
    }

    public static List<String> rsToList(ResultSet resultSet) {
        try (resultSet) {
            List<String> persons = new ArrayList<>();
            while (resultSet.next()) {
                persons.add(resultSet.getInt(1) + " - " + resultSet.getString(2));
            }
            return persons;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
