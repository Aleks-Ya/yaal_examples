package bean;

import org.springframework.stereotype.Repository;

/**
 * Проверяем инициализацию бинов, отмеченный @Repository.
 */
@Repository
public class AnyRepository {
    public AnyRepository() {
        System.out.println("============= REPOSITORY INSTANTIATED =============");
    }
}