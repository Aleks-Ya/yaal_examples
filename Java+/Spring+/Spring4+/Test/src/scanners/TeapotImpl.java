package scanners;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Бин для инициализации Spring'ом через аннотации.
 * Версия для боевого сервера.
 */
@Component
public class TeapotImpl implements ITeapot {

    @Value("Spot")
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}