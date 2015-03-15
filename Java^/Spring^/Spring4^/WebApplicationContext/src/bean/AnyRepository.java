package bean;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * Проверяем инициализацию бинов, отмеченный @Repository.
 */
@Repository
public class AnyRepository {

    @PostConstruct
    public void postConstruct() {
        System.out.println("============= REPOSITORY INSTANTIATED =============");
    }

    public String getRepositoryMessage() {
        return "Welcome to the repo!";
    }
}