package javafx.fxml_.reusable;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class ReusableController implements InitializeAfterLoadingFxml {
    @FXML
    private VBox reusable;
    @FXML
    @SuppressWarnings("unused")
    private Button printButton;
    @FXML
    private TextArea text;

    @FXML
    void buttonClicked() {
        System.out.println("Your text: " + text.getText());
        System.out.println("Your id: " + reusable.getId());
    }

    @Override
    public void initFinally() {
        text.setText("Actual id: " + reusable.getId());
    }
}
