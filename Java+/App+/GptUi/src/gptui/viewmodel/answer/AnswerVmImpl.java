package gptui.viewmodel.answer;

import gptui.Mdc;
import gptui.model.storage.Answer;
import gptui.model.storage.AnswerState;
import gptui.model.storage.AnswerType;
import gptui.viewmodel.mediator.AnswerMediator;
import jakarta.inject.Inject;
import javafx.scene.paint.Color;
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

class AnswerVmImpl implements AnswerVmController, AnswerVmMediator {
    public AnswerVmImpl(AnswerType answerType) {
        this.answerType = answerType;
    }

    private static final Logger log = LoggerFactory.getLogger(AnswerVmImpl.class);
    public final AnswerVmProperties properties = new AnswerVmProperties();
    @Inject
    private AnswerMediator mediator;
    private String currentWebViewContent = "";
    private final AnswerType answerType;
    private static final Map<AnswerType, Integer> hotkeyDigitMap = Map.of(GRAMMAR, 1, SHORT, 2, LONG, 3, GCP, 4);
    private static final Map<AnswerType, String> labelTextMap = Map.of(GRAMMAR, "Grammar\nanswer:", SHORT, "Short\nanswer:", LONG, "Long\nanswer:", GCP, "GCP\nanswer:");

    @Override
    public void onCopyButtonClick() {
        Mdc.run(answerType, () -> {
            log.trace("onCopyButtonClick");
            var content = properties.webViewContent.get();
            mediator.putHtmlToClipboard(content);
        });
    }

    @Override
    public void onRegenerateButtonClick() {
        log.trace("onRegenerateButtonClick");
        mediator.requestAnswer(mediator.getCurrentInteractionId(), answerType);
    }

    @Override
    public AnswerVmProperties properties() {
        return properties;
    }

    @Override
    public void displayCurrentAnswer() {
        Mdc.run(answerType, () -> {
            log.trace("displayCurrentAnswer");
            mediator.getCurrentInteractionOpt().map(interaction -> interaction.getAnswer(answerType)).ifPresentOrElse(answerOpt -> {
                log.trace("Display answer: {}", answerOpt.map(Answer::toShortString));
                var html = answerOpt.isPresent() ? answerOpt.get().answerHtml() : "";
                var state = answerOpt.isPresent() ? answerOpt.get().answerState() : NEW;
                if (!currentWebViewContent.equals(html)) {
                    properties.webViewContent.set(html);
                    currentWebViewContent = html;
                }
                properties.statusCircleFill.setValue(answerStateToColor(state));
                var temperature = answerOpt.map(answer -> answer.temperature() + "°").orElse("");
                properties.temperatureText.setValue(temperature);
                properties.temperatureSpinner.setValue(mediator.getTemperature(answerType));
            }, () -> {
                log.trace("Display empty answer");
                currentWebViewContent = "";
                properties.webViewContent.set("");
                properties.statusCircleFill.setValue(WHITE);
                properties.temperatureText.setValue("");
                properties.temperatureSpinner.setValue(mediator.getTemperature(answerType));
            });
        });
    }

    @Override
    public void initialize() {
        properties.temperatureSpinner.addListener((obs, oldValue, newValue) -> mediator.setTemperature(answerType, newValue));
        Mdc.run(answerType, () -> {
            log.trace("displayInitialState");
            properties.answerLabelText.setValue(labelTextMap.get(answerType));
            properties.copyButtonText.setValue(properties.copyButtonText.getValue() + " _" + hotkeyDigitMap.get(answerType));
        });
    }

    @Override
    public void ctrlAltUpHotkeyPressed() {
        mediator.selectNextHistoryItem();
    }

    @Override
    public void ctrlAltDownHotkeyPressed() {
        mediator.selectNextHistoryItem();
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

