package javafx.fxml_.reusable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class VBoxReusableController {
    @FXML
    private Button printButton;
    @FXML
    private TextArea text;

    @FXML
    void buttonClicked(ActionEvent event) {
        System.out.println("Your text: " + text.getText());
    }

}
