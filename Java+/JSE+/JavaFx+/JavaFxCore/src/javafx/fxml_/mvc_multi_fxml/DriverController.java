package javafx.fxml_.mvc_multi_fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class DriverController implements ModelListener {
    private final MvcMultiModel model;

    public DriverController(MvcMultiModel model) {
        this.model = model;
    }

    @FXML
    private TextField driverNameField;

    @FXML
    private void initialize() {
        model.subscribe(this);
    }

    @FXML
    void changedDriverName(ActionEvent ignoredEvent) {
        model.setDriverName(driverNameField.getText());
        model.modelUpdated();
    }

    @FXML
    void driverNameKeyTyped(KeyEvent ignoredEvent) {
        model.setDriverName(driverNameField.getText());
        model.modelUpdated();
    }

    @Override
    public void modelUpdated(MvcMultiModel model) {
        if (model.getDriverName() != null && !model.getDriverName().equals(driverNameField.getText())) {
            driverNameField.setText(model.getDriverName());
        }
    }
}
