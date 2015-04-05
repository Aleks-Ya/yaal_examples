package scan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Внедрение зависимостей через конструктор.
 */
@Service
public class Airport {
    private String name;

    @Autowired
    public Airport(@Value("Пулково") String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}