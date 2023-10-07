package javafx.fxml_.mvc_multi_fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ParentController implements ModelListener {
    private final MvcMultiModel model;

    public ParentController(MvcMultiModel model) {
        this.model = model;
    }

    @FXML
    private Text summaryText;

    @FXML
    private void initialize() {
        model.subscribe(this);
    }

    @FXML
    void clickLoadTestData(ActionEvent ignoredEvent) {
        model.setBrand(Brand.AUDI);
        model.setDriverName("John");
        model.modelUpdated();
    }

    @Override
    public void modelUpdated(MvcMultiModel model) {
        var text = String.format("Driver: %s, Brand: %s", model.getDriverName(), model.getBrand());
        summaryText.setText(text);
    }
}


