package ignite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javax.inject.Inject;

public class IgniteController {
    @Inject
    private NumberService numberService;
    @FXML
    private Button button1;

    @FXML
    void clickOnButton(ActionEvent event) {
        System.out.println("Button was clicked! Your number is " + numberService.getSeven());
    }
}
