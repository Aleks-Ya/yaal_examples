package bean;

import org.springframework.stereotype.Service;

/**
 * Вывод из конструктора этого бина означает, что Spring инициализировался.
 */
@Service
public class AnyService {
    public AnyService() {
        System.out.println("I'M INSTANTIATED!!!");
    }
}