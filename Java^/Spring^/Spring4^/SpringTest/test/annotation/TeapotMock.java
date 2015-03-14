package annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Бин для инициализации Spring'ом через аннотации.
 * Фейк для тестирования.
 */
@Component
public class TeapotMock implements ITeapot {

    @Value("TestSpot")
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}