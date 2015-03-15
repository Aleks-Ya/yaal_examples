package bean;

import org.springframework.stereotype.Service;

/**
 * Проверяем инициализацию бинов, отмеченный @Service.
 */
@Service
public class AnyService {
    public AnyService() {
        System.out.println("============= SERVICE INSTANTIATED =============");
    }
}