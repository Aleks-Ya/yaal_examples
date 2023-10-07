package javafx.fxml_.mvc_multi_fxml;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MvcMultiModel {
    private final Set<ModelListener> listeners = new HashSet<>();

    private String driverName;
    private Brand brand;
    private List<Brand> brands = new ArrayList<>();

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void subscribe(ModelListener listener) {
        listeners.add(listener);
    }

    public void modelUpdated() {
        listeners.forEach(listener -> listener.modelUpdated(this));
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}
