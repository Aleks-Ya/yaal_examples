package gptui.ui.view;

import gptui.storage.Answer;
import gptui.storage.AnswerState;
import gptui.storage.AnswerType;
import gptui.storage.Interaction;
import gptui.ui.GptModel;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static gptui.format.ClipboardHelper.putHtmlToClipboard;
import static gptui.storage.AnswerState.NEW;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.QUESTION_CORRECTNESS;
import static gptui.storage.AnswerType.SHORT;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;

public class GptViewModel {
    private static final Logger log = LoggerFactory.getLogger(GptViewModel.class);
    private final GptModel model;
    private final ObjectProperty<Interaction> interactionHistoryValueProperty = new SimpleObjectProperty<>();
    private final ListProperty<Interaction> interactionHistoryItemsProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final StringProperty themeValueProperty = new SimpleStringProperty();
    private final ListProperty<String> themeItemsProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final StringProperty questionProperty = new SimpleStringProperty();
    private final StringProperty questionCorrectnessProperty = new SimpleStringProperty();
    private final StringProperty shortAnswerProperty = new SimpleStringProperty();
    private final StringProperty longAnswerProperty = new SimpleStringProperty();
    private final ObjectProperty<Paint> shortAnswerStatusCircleProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Paint> longAnswerStatusCircleProperty = new SimpleObjectProperty<>();

    public GptViewModel(GptModel gptModel) {
        interactionHistoryValueProperty.addListener((observable, oldValue, newValue) -> {
            log.debug("InteractionHistoryValueProperty event: oldValue='{}', newValue='{}'", oldValue, newValue);
            if (newValue != null) {
                if (oldValue == null || !oldValue.id().equals(newValue.id())) {
                    showInteraction(newValue);
                }
            }
        });
        model = gptModel;
        shortAnswerStatusCircleProperty.set(WHITE);
        longAnswerStatusCircleProperty.set(WHITE);
    }

    ObjectProperty<Interaction> interactionHistoryValueProperty() {
        return interactionHistoryValueProperty;
    }

    ListProperty<Interaction> interactionHistoryItemsProperty() {
        return interactionHistoryItemsProperty;
    }

    StringProperty themeValueProperty() {
        return themeValueProperty;
    }

    ListProperty<String> themeItemsProperty() {
        return themeItemsProperty;
    }

    StringProperty questionProperty() {
        return questionProperty;
    }

    StringProperty questionCorrectnessProperty() {
        return questionCorrectnessProperty;
    }

    StringProperty shortAnswerProperty() {
        return shortAnswerProperty;
    }

    ObjectProperty<Paint> shortAnswerStatusCircleProperty() {
        return shortAnswerStatusCircleProperty;
    }

    ObjectProperty<Paint> longAnswerStatusCircleProperty() {
        return longAnswerStatusCircleProperty;
    }

    StringProperty longAnswerProperty() {
        return longAnswerProperty;
    }

    void sendQuestion() {
        var theme = themeValueProperty.getValue();
        var question = questionProperty.get();
        model.sendQuestion(theme, question);
    }

    void copyAnswerToClipboard(AnswerType answerType) {
        var content = switch (answerType) {
            case QUESTION_CORRECTNESS -> questionCorrectnessProperty.getValue();
            case SHORT -> shortAnswerProperty.getValue();
            case LONG -> longAnswerProperty.getValue();
        };
        putHtmlToClipboard(content);
    }

    public void setAnswer(AnswerType answerType, String answer) {
        switch (answerType) {
            case QUESTION_CORRECTNESS -> questionCorrectnessProperty.set(answer);
            case SHORT -> shortAnswerProperty.set(answer);
            case LONG -> longAnswerProperty.set(answer);
        }
    }

    public void interactionHistoryUpdated(List<Interaction> newInteractionHistoryList, Interaction currentInteraction) {
        var sorted = newInteractionHistoryList.stream()
                .sorted((i1, i2) -> i2.id().id().compareTo(i1.id().id()))
                .toList();
        Platform.runLater(() -> {
            interactionHistoryItemsProperty.setAll(sorted);
            interactionHistoryValueProperty.setValue(currentInteraction);
        });
    }

    public void themeListUpdated(List<String> newThemeList) {
        log.debug("themeListUpdated: {}", newThemeList);
        themeItemsProperty.setAll(newThemeList);
        var currentThemeValue = themeValueProperty.getValue();
        if (newThemeList.contains(currentThemeValue)) {
            themeValueProperty.setValue(currentThemeValue);
        } else if (!newThemeList.isEmpty()) {
            themeValueProperty.setValue(newThemeList.get(0));
        }
    }

    public void setAnswerStatusCircleColor(Interaction interaction) {
        if (interaction.answers().containsKey(SHORT)) {
            shortAnswerStatusCircleProperty.setValue(answerStateToColor(interaction.getAnswer(SHORT).orElseThrow().answerState()));
        }
        if (interaction.answers().containsKey(LONG)) {
            longAnswerStatusCircleProperty.setValue(answerStateToColor(interaction.getAnswer(LONG).orElseThrow().answerState()));
        }
    }

    private Color answerStateToColor(AnswerState answerState) {
        return switch (answerState) {
            case NEW -> WHITE;
            case SENT -> BLUE;
            case SUCCESS -> GREEN;
            case FAIL -> RED;
        };
    }

    private synchronized void showInteraction(Interaction interaction) {
        log.debug("Show interaction: {}", interaction);
        themeValueProperty.setValue(interaction.theme());
        questionProperty.setValue(interaction.question());
        setAnswer(QUESTION_CORRECTNESS, interaction.getAnswer(QUESTION_CORRECTNESS)
                .orElse(new Answer(QUESTION_CORRECTNESS, "", "", "", NEW))
                .answerHtml());
        setAnswer(SHORT, interaction.getAnswer(SHORT)
                .orElse(new Answer(SHORT, "", "", "", NEW))
                .answerHtml());
        setAnswer(LONG, interaction.getAnswer(LONG)
                .orElse(new Answer(LONG, "", "", "", NEW))
                .answerHtml());
        setAnswerStatusCircleColor(interaction);
    }
}