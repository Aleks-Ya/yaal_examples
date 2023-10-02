package gptui.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import javax.inject.Inject;

public class GptUiController {
    @Inject
    private ModelView modelView;

    @FXML
    private HBox questionHistoryHBox;
    private ComboBox interactionHistoryComboBox;

    @FXML
    void sendQuestion(ActionEvent event) {
        System.out.println("Button was clicked!");
    }

    @FXML
    void initialize() {
        interactionHistoryComboBox = (ComboBox) questionHistoryHBox.lookup("#questionHistoryComboBox");
        assert interactionHistoryComboBox != null;
//        textArea.textProperty().bindBidirectional(viewModel.getTextProperty());
    }


}