package gptui.ui;

import gptui.storage.Interaction;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import java.util.List;

class GptViewModel {
    private final GptModel model;
    private final ObjectProperty<Interaction> interactionHistoryValueProperty = new SimpleObjectProperty<>();
    private final ListProperty<Interaction> interactionHistoryItemsProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final StringProperty themeValueProperty = new SimpleStringProperty();
    private final ListProperty<String> themeItemsProperty = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final StringProperty questionProperty = new SimpleStringProperty();
    private final StringProperty questionCorrectnessProperty = new SimpleStringProperty();
    private final StringProperty shortAnswerProperty = new SimpleStringProperty();
    private final StringProperty longAnswerProperty = new SimpleStringProperty();

    public GptViewModel() {
        interactionHistoryValueProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showInteraction(newValue);
            } else {
                interactionHistoryValueProperty.setValue(null);
            }
        });
        model = new GptModel(this);
    }

    public ObjectProperty<Interaction> interactionHistoryValueProperty() {
        return interactionHistoryValueProperty;
    }

    public ListProperty<Interaction> interactionHistoryItemsProperty() {
        return interactionHistoryItemsProperty;
    }

    public StringProperty themeValueProperty() {
        return themeValueProperty;
    }

    public ListProperty<String> themeItemsProperty() {
        return themeItemsProperty;
    }

    public StringProperty questionProperty() {
        return questionProperty;
    }

    public StringProperty questionCorrectnessProperty() {
        return questionCorrectnessProperty;
    }

    public StringProperty shortAnswerProperty() {
        return shortAnswerProperty;
    }

    public StringProperty longAnswerProperty() {
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

    public void setQuestionCorrectnessAnswer(String answer) {
        questionCorrectnessProperty.set(answer);
    }

    public void setShortAnswer(String answer) {
        shortAnswerProperty.set(answer);
    }

    public void setLongAnswer(String answer) {
        longAnswerProperty.set(answer);
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

    private void showInteraction(Interaction interaction) {
        setTheme(interaction.theme());
        setQuestion(interaction.question());
        setQuestionCorrectnessAnswer(interaction.questionCorrectnessAnswer());
        setShortAnswer(interaction.shortAnswerHtml());
        setLongAnswer(interaction.longAnswerHtml());
    }
}
