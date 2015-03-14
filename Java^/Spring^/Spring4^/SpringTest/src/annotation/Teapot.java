package annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Бин для инициализации Spring'ом через аннотации.
 */
@Component
public class Teapot {

    @Value("Spot")
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
