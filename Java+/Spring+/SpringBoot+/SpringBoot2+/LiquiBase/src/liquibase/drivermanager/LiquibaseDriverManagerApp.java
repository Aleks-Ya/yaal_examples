package liquibase.drivermanager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import util.ResourceUtil;

import javax.annotation.PostConstruct;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring Boot app which initializes H2 database by Liquibase and selects rows from it.
 */
@SpringBootApplication
public class LiquibaseDriverManagerApp {

    public static void main(String[] args) {
        System.setProperty("spring.config.location", ResourceUtil.resourceToStrPath(LiquibaseDriverManagerApp.class, "application.yaml"));
        SpringApplication.run(LiquibaseDriverManagerApp.class, args);
    }

    @Component
    static class WorkWithTable {
        @Value("${h2.file}")
        private String h2File;

        @PostConstruct
        void readTable() throws SQLException {
            System.out.println("Reading the table...");
            System.out.println("WorkWithTable h2.file: " + h2File);
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
            if (!personList.equals(List.of("1-John-30", "2-Mary-25"))) {
                throw new AssertionError("Person list: " + personList);
            }
            System.out.println("Finished reading the table.");
        }
    }
}
