package gptui.ui.controller;

import gptui.Mdc;
import gptui.gpt.QuestionApi;
import gptui.storage.AnswerState;
import gptui.storage.AnswerType;
import gptui.storage.GptStorage;
import gptui.ui.EventSource;
import gptui.ui.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static gptui.storage.AnswerState.NEW;
import static gptui.storage.AnswerType.GCP;
import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;
import static java.math.RoundingMode.HALF_UP;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;

public class AnswerController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);
    private static final Map<AnswerType, Integer> hotkeyDigitMap =
            Map.of(GRAMMAR, 1, SHORT, 2, LONG, 3, GCP, 4);
    private static final Map<AnswerType, String> labelTextMap = Map.of(
            GRAMMAR, "Grammar\nanswer:",
            SHORT, "Short\nanswer:",
            LONG, "Long\nanswer:",
            GCP, "Bard\nanswer:");
    private static final BigDecimal hundred = BigDecimal.valueOf(100);
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
    private AnswerType answerType;
    @Inject
    private ClipboardHelper clipboardHelper;
    @Inject
    private GptStorage storage;
    @Inject
    private QuestionApi questionApi;

    @Override
    protected void initializeChild() {
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
        temperatureSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            var temperature = BigDecimal.valueOf(newValue).setScale(1, HALF_UP).divide(hundred, HALF_UP);
            model.setTemperature(answerType, temperature);
        });
    }

    @FXML
    void clickCopyButton(ActionEvent ignoredEvent) {
        Mdc.run(answerType, () -> {
            log.trace("clickCopyButton");
            copyWebViewContentToClipboard();
        });
    }

    @FXML
    void onRegenerateButtonClick(ActionEvent ignoredEvent) {
        log.trace("onRegenerateButtonClick");
        questionApi.requestAnswer(model.getCurrentInteractionId(), answerType);
    }

    @Override
    public void stageWasShowed(Model model, EventSource source) {
        Mdc.run(answerType, () -> {
            log.trace("stageWasShowed");
            answerLabel.setText(labelTextMap.get(answerType));
            copyButton.setText(copyButton.getText() + " _" + hotkeyDigitMap.get(answerType));
        });
    }

    @Override
    public void modelChanged(Model model, EventSource source) {
        Mdc.run(answerType, () -> {
            log.trace("modelChanged later");
            Optional.ofNullable(model.getCurrentInteractionId())
                    .map(storage::readInteraction)
                    .map(Optional::orElseThrow)
                    .map(interaction -> interaction.getAnswer(answerType))
                    .ifPresentOrElse(answerOpt -> {
                        var html = answerOpt.isPresent() ? answerOpt.get().answerHtml() : "";
                        var state = answerOpt.isPresent() ? answerOpt.get().answerState() : NEW;
                        webView.getEngine().loadContent(html);
                        statusCircle.setFill(answerStateToColor(state));
                        var temperature = answerOpt.map(answer -> answer.temperature()
                                                                          .multiply(hundred)
                                                                          .setScale(0, HALF_UP) + "°")
                                .orElse("");
                        temperatureText.setText(temperature);
                        answerOpt.ifPresent(answer -> temperatureSpinner.getValueFactory()
                                .setValue(answer.temperature().multiply(hundred).intValue()));

                    }, () -> {
                        webView.getEngine().loadContent("");
                        statusCircle.setFill(WHITE);
                        temperatureText.setText("");
                        temperatureSpinner.getValueFactory()
                                .setValue(model.getTemperatures().getTemperature(answerType).intValue());
                    });
        });
    }

    public void setAnswerType(AnswerType answerType) {
        this.answerType = answerType;
    }

    private void copyWebViewContentToClipboard() {
        var content = (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
        clipboardHelper.putHtmlToClipboard(content);
    }

    private Color answerStateToColor(AnswerState answerState) {
        return switch (answerState) {
            case NEW -> WHITE;
            case SENT -> BLUE;
            case SUCCESS -> GREEN;
            case FAIL -> RED;
        };
    }
}
