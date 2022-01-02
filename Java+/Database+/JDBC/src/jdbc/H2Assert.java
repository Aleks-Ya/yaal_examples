package jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class H2Assert {
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
