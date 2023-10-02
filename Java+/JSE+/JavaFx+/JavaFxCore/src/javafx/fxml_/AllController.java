package javafx.fxml_;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AllController {

    @FXML
    private Button button1;

    @FXML
    private VBox vbox1;

    @FXML
    void buttonOnAction(ActionEvent event) {
        System.out.println("Button was clicked!");
    }

    @FXML
    void initialize() {
        assert button1 != null : "fx:id=\"button1\" was not injected: check your FXML file 'all.fxml'.";
        assert vbox1 != null : "fx:id=\"vbox1\" was not injected: check your FXML file 'all.fxml'.";
    }

}


