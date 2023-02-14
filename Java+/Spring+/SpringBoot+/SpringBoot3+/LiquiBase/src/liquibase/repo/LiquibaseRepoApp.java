package liquibase.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import util.CollectionUtil;

import jakarta.annotation.PostConstruct;
import java.util.List;

import static util.ResourceUtil.resourceToPath;

/**
 * Spring Boot app which initializes H2 database by Liquibase and selects rows from it.
 */
@SpringBootApplication
public class LiquibaseRepoApp {

    public static void main(String[] args) {
        System.setProperty("spring.config.location", resourceToPath(LiquibaseRepoApp.class, "SpringBootTestTest.yaml"));
        SpringApplication.run(LiquibaseRepoApp.class, args);
    }

    @Component
    static class WorkWithTable {
        @Autowired
        private PersonRepository repo;

        @PostConstruct
        void readTable() {
            System.out.println("Reading the table...");
            var actList = CollectionUtil.iterableToList(repo.findAll());
            var expList = List.of(new Person(1L, "John", 30), new Person(2L, "Mary", 25));
            if (!actList.equals(expList)) {
                throw new AssertionError("Person list: " + actList);
            }
            System.out.println("Finished reading the table.");
        }
    }
}
