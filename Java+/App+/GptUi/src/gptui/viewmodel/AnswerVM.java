package gptui.viewmodel;

import gptui.Mdc;
import gptui.model.clipboard.ClipboardModel;
import gptui.model.question.QuestionModel;
import gptui.model.state.StateModel;
import gptui.model.storage.AnswerState;
import gptui.model.storage.AnswerType;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static gptui.model.storage.AnswerState.NEW;
import static gptui.model.storage.AnswerType.GCP;
import static gptui.model.storage.AnswerType.GRAMMAR;
import static gptui.model.storage.AnswerType.LONG;
import static gptui.model.storage.AnswerType.SHORT;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;

public class AnswerVM {
    public AnswerVM(AnswerType answerType) {
        this.answerType = answerType;
    }

    private static final Logger log = LoggerFactory.getLogger(AnswerVM.class);
    public final Properties properties = new Properties();
    @Inject
    private StateModel stateModel;
    @Inject
    private QuestionModel questionModel;
    @Inject
    private ClipboardModel clipboardModel;
    @Inject
    private ViewModelMediator mediator;
    private String currentWebViewContent = "";
    private final AnswerType answerType;
    private static final Map<AnswerType, Integer> hotkeyDigitMap =
            Map.of(GRAMMAR, 1, SHORT, 2, LONG, 3, GCP, 4);
    private static final Map<AnswerType, String> labelTextMap = Map.of(
            GRAMMAR, "Grammar\nanswer:",
            SHORT, "Short\nanswer:",
            LONG, "Long\nanswer:",
            GCP, "GCP\nanswer:");

    public void clickCopyButton() {
        Mdc.run(answerType, () -> {
            log.trace("clickCopyButton");
            var content = properties.webViewContent.get();
            clipboardModel.putHtmlToClipboard(content);
        });
    }

    public void onRegenerateButtonClick() {
        log.trace("onRegenerateButtonClick");
        questionModel.requestAnswer(stateModel.getCurrentInteractionId(), answerType, () -> mediator.answerUpdated(answerType));
    }

    void displayCurrentAnswer() {
        Mdc.run(answerType, () -> {
            log.trace("displayCurrentAnswer");
            stateModel.getCurrentInteractionOpt()
                    .map(interaction -> interaction.getAnswer(answerType))
                    .ifPresentOrElse(answerOpt -> {
                        var html = answerOpt.isPresent() ? answerOpt.get().answerHtml() : "";
                        var state = answerOpt.isPresent() ? answerOpt.get().answerState() : NEW;
                        if (!currentWebViewContent.equals(html)) {
                            properties.webViewContent.set(html);
                            currentWebViewContent = html;
                        }
                        properties.statusCircleFill.setValue(answerStateToColor(state));
                        var temperature = answerOpt.map(answer -> answer.temperature() + "°").orElse("");
                        properties.temperatureText.setValue(temperature);
                        properties.temperatureSpinner.setValue(stateModel.getTemperature(answerType));
                    }, () -> {
                        properties.webViewContent.set("");
                        properties.statusCircleFill.setValue(WHITE);
                        properties.temperatureText.setValue("");
                        properties.temperatureSpinner.setValue(stateModel.getTemperature(answerType));
                    });
        });
    }

    void initialize() {
        properties.temperatureSpinner.addListener((obs, oldValue, newValue) -> stateModel.setTemperature(answerType, newValue));
        Mdc.run(answerType, () -> {
            log.trace("displayInitialState");
            properties.answerLabelText.setValue(labelTextMap.get(answerType));
            properties.copyButtonText.setValue(properties.copyButtonText.getValue() + " _" + hotkeyDigitMap.get(answerType));
        });
    }

    private Color answerStateToColor(AnswerState answerState) {
        return switch (answerState) {
            case NEW -> WHITE;
            case SENT -> BLUE;
            case SUCCESS -> GREEN;
            case FAIL -> RED;
        };
    }

    public static class Properties {
        public final StringProperty webViewContent = new SimpleStringProperty();
        public final StringProperty temperatureText = new SimpleStringProperty();
        public final StringProperty answerLabelText = new SimpleStringProperty();
        public final StringProperty copyButtonText = new SimpleStringProperty();
        public final ObjectProperty<Integer> temperatureSpinner = new SimpleObjectProperty<>();
        public final ObjectProperty<Paint> statusCircleFill = new SimpleObjectProperty<>();
    }
}

