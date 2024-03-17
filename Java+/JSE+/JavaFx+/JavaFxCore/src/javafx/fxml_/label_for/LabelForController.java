package javafx.fxml_.label_for;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LabelForController {
    @FXML
    private Label label1;
    @FXML
    private TextField textField1;

    @FXML
    private void initialize() {
        // "labelFor" in FXML leads to: "IllegalArgumentException: Unable to coerce #textField1 to class javafx.scene.Node."
        label1.setLabelFor(textField1);
    }
}


