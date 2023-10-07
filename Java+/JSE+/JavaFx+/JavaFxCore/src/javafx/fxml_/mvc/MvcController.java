package javafx.fxml_.mvc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;

public class MvcController {
    private final MvcModel model;

    public MvcController(MvcModel model) {
        this.model = model;
    }

    @FXML
    private TextArea textArea;
    @FXML
    private WebView webView;

    @FXML
    void buttonOnAction(ActionEvent ignoredEvent) {
        model.setText("Original text");
        modelUpdated();
    }

    @FXML
    void keyTypedTextArea(KeyEvent ignoredEvent) {
        model.setText(textArea.getText());
        modelUpdated();
    }

    private void modelUpdated() {
        if (textArea.getText() != null && !textArea.getText().equals(model.getText())) {
            textArea.setText(model.getText());
        }
        webView.getEngine().loadContent(textArea.getText());
    }
}


