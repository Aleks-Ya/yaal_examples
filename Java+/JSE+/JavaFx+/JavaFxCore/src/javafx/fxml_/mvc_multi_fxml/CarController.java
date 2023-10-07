package javafx.fxml_.mvc_multi_fxml;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.Arrays;

public class CarController implements ModelListener {
    private final MvcMultiModel model;

    public CarController(MvcMultiModel model) {
        this.model = model;
    }

    @FXML
    private ComboBox<Brand> brandComboBox;

    @FXML
    void chosenBrand(ActionEvent ignoredEvent) {
        model.setBrand(brandComboBox.getValue());
        model.modelUpdated();
    }

    @FXML
    private void initialize() {
        model.subscribe(this);
        model.setBrands(Arrays.asList(Brand.values()));
        model.setBrand(Brand.MERCEDES);
        model.modelUpdated();
    }

    @Override
    public void modelUpdated(MvcMultiModel model) {
        var newBrand = model.getBrand();
        if (brandComboBox.getValue() != newBrand) {
            brandComboBox.setValue(newBrand);
        }
        var newItems = FXCollections.observableArrayList(model.getBrands());
        if (!brandComboBox.getItems().equals(newItems)) {
            brandComboBox.setItems(newItems);
        }
    }
}
