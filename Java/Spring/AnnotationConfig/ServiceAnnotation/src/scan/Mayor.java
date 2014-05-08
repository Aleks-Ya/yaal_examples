package scan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Мэр.
 */
@Service("mayor")
public class Mayor {
    @Value("Полтавченко")
    private String fio;

    @Override
    public String toString() {
        return fio;
    }
}
