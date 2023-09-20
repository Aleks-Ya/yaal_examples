package gptui.ui.view;

import gptui.storage.Answer;
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

import java.util.List;

import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.QUESTION_CORRECTNESS;
import static gptui.storage.AnswerType.SHORT;

public class GptViewModel {
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

    public GptViewModel() {
        interactionHistoryValueProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                interactionHistoryValueProperty.setValue(null);
            } else if (oldValue == null || !oldValue.id().equals(newValue.id())) {
                showInteraction(newValue);
            }
        });
        model = new GptModel(this);
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

    public void sendQuestion() {
        var theme = themeValueProperty.getValue();
        var question = questionProperty.get();
        model.sendQuestion(theme, question);
    }

    public void setTheme(String theme) {
        themeValueProperty.setValue(theme);
    }

    public void setQuestion(String question) {
        questionProperty.setValue(question);
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
        themeItemsProperty.setAll(newThemeList);
        var currentThemeValue = themeValueProperty.getValue();
        if (newThemeList.contains(currentThemeValue)) {
            themeValueProperty.setValue(currentThemeValue);
        } else if (!newThemeList.isEmpty()) {
            themeValueProperty.setValue(newThemeList.get(0));
        }
    }

    public void setAnswerStatusCircleColor(AnswerType answerType, Color color) {
        switch (answerType) {
            case SHORT -> shortAnswerStatusCircleProperty.setValue(color);
            case LONG -> longAnswerStatusCircleProperty.setValue(color);
        }
    }

    private void showInteraction(Interaction interaction) {
        setTheme(interaction.theme());
        setQuestion(interaction.question());
        setAnswer(QUESTION_CORRECTNESS, interaction.getAnswer(QUESTION_CORRECTNESS)
                .orElse(new Answer(QUESTION_CORRECTNESS, "", "", ""))
                .answerHtml());
        setAnswer(SHORT, interaction.getAnswer(SHORT)
                .orElse(new Answer(SHORT, "", "", ""))
                .answerHtml());
        setAnswer(LONG, interaction.getAnswer(LONG)
                .orElse(new Answer(LONG, "", "", ""))
                .answerHtml());
    }
}
