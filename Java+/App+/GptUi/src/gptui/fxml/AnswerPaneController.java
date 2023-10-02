package gptui.fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class AnswerPaneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button;

    @FXML
    private Circle circle;

    @FXML
    private Label label;

    @FXML
    private WebView webView;

    @FXML
    void clickCopyButton(ActionEvent event) {
        System.out.println("Copy button was clicked!");
    }

    @FXML
    void initialize() {
        assert button != null : "fx:id=\"button\" was not injected: check your FXML file 'AnswerPane.fxml'.";
        assert circle != null : "fx:id=\"circle\" was not injected: check your FXML file 'AnswerPane.fxml'.";
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'AnswerPane.fxml'.";
        assert webView != null : "fx:id=\"webView\" was not injected: check your FXML file 'AnswerPane.fxml'.";

    }

}

