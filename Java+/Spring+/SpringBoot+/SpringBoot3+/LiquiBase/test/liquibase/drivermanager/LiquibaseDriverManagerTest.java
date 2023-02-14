package liquibase.drivermanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.config.location=classpath:liquibase/drivermanager/application.yaml")
class LiquibaseDriverManagerTest {

    @Value("${h2.file}")
    private String h2File;

    @Test
    void select() throws SQLException {
        System.out.println("InitDatabaseTest h2.file: " + h2File);
        var personList = new ArrayList<String>();
        try (var conn = DriverManager.getConnection("jdbc:h2:" + h2File);
             var statement = conn.createStatement()) {
            var rs = statement.executeQuery("SELECT * FROM person");
            while (rs.next()) {
                var id = rs.getLong("id");
                var name = rs.getString("name");
                var age = rs.getInt("age");
                personList.add(id + "-" + name + "-" + age);
            }
        }
        assertThat(personList).containsExactly("1-John-30", "2-Mary-25");
    }
}