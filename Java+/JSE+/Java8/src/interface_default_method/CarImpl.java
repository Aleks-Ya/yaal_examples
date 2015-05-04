package interface_default_method;

/**
 * Реализация интерфейса с default-методом.
 */
public class CarImpl implements Car {
    private String model;
    private int maxSpeed;

    public CarImpl(String model, int maxSpeed) {
        this.model = model;
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public int getMaxSpeed() {
        return maxSpeed;
    }
}
