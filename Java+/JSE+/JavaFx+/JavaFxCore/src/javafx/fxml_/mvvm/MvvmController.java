package javafx.fxml_.mvvm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class MvvmController {
    private final MvvmViewModel viewModel = new MvvmViewModel();

    @FXML
    private Button button1;

    @FXML
    private TextArea textArea;

    @FXML
    void buttonOnAction(ActionEvent event) {
        System.out.println("Reset the text!");
        viewModel.resetText();
    }

    @FXML
    void initialize() {
        textArea.textProperty().bindBidirectional(viewModel.getTextProperty());
    }

}


