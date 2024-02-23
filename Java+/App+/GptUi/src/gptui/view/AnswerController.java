package gptui.view;

import gptui.viewmodel.AnswerVM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnswerController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);
    @FXML
    private Label answerLabel;
    @FXML
    private Circle statusCircle;
    @FXML
    private WebView webView;
    @FXML
    private Button copyButton;
    @FXML
    private Text temperatureText;
    @FXML
    public Spinner<Integer> temperatureSpinner;
    private AnswerVM vm;

    @FXML
    void clickCopyButton(ActionEvent ignoredEvent) {
        log.trace("clickCopyButton");
        vm.onCopyButtonClick();
    }

    @FXML
    void onRegenerateButtonClick(ActionEvent ignoredEvent) {
        log.trace("onRegenerateButtonClick");
        vm.onRegenerateButtonClick();
    }

    void initializeController(AnswerVM vm) {
        log.trace("initializeController");
        this.vm = vm;
        temperatureSpinner.getValueFactory().setConverter(new StringConverter<>() {
            @Override
            public String toString(Integer number) {
                return number + "°";
            }

            @Override
            public Integer fromString(String string) {
                return Integer.valueOf(string.replace("°", ""));
            }
        });
        webView.getEngine().documentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.vm.properties.webViewContent
                        .set((String) webView.getEngine().executeScript("document.documentElement.outerHTML"));
            }
        });
        this.vm.properties.webViewContent.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                webView.getEngine().loadContent(newValue);
            }
        });
        this.vm.properties.temperatureText.bindBidirectional(temperatureText.textProperty());
        this.vm.properties.temperatureSpinner.bindBidirectional(temperatureSpinner.getValueFactory().valueProperty());
        this.vm.properties.statusCircleFill.bindBidirectional(statusCircle.fillProperty());
        this.vm.properties.answerLabelText.bindBidirectional(answerLabel.textProperty());
        this.vm.properties.copyButtonText.bindBidirectional(copyButton.textProperty());
    }

    @Override
    protected void initialize() {
    }

}
