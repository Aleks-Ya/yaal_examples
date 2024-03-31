package gptui.view;

import gptui.viewmodel.answer.AnswerVmController;
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

import static gptui.LogUtils.shorten;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;

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
    private AnswerVmController vm;

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

    void initializeController(AnswerVmController vm) {
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
                var currentContent = vm.properties().webViewContent.getValue();
                var newContent = (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
                if (!newContent.equals(currentContent)) {
                    log.trace("Set value to webViewContent from WebView Engine: {}", shorten(newContent));
                    vm.properties().webViewContent.set(newContent);
                }
            }
        });
        vm.properties().webViewContent.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                log.trace("Load content to WebView Engine: {}", shorten(newValue));
                webView.getEngine().loadContent(newValue);
            }
        });
        vm.properties().temperatureText.bindBidirectional(temperatureText.textProperty());
        vm.properties().temperatureSpinner.bindBidirectional(temperatureSpinner.getValueFactory().valueProperty());
        vm.properties().statusCircleFill.bindBidirectional(statusCircle.fillProperty());
        vm.properties().answerLabelText.bindBidirectional(answerLabel.textProperty());
        vm.properties().copyButtonText.bindBidirectional(copyButton.textProperty());
        webView.addEventFilter(KEY_PRESSED, event -> {
            if (event.isControlDown() && event.isAltDown()) {
                if (event.getCode() == DOWN) {
                    event.consume();
                    vm.ctrlAltDownHotkeyPressed();
                }
                if (event.getCode() == UP) {
                    event.consume();
                    vm.ctrlAltUpHotkeyPressed();
                }
            }
        });
    }

    @Override
    protected void initialize() {
    }

}
