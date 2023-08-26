package gptui.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class GptViewModel {
    private final StringProperty themeProperty = new SimpleStringProperty();
    private final StringProperty questionProperty = new SimpleStringProperty();
    private final StringProperty questionCorrectnessProperty = new SimpleStringProperty();
    private final StringProperty shortAnswerProperty = new SimpleStringProperty();
    private final StringProperty longAnswerProperty = new SimpleStringProperty();
    private final GptModel gptModel = new GptModel(this);

    public StringProperty themeProperty() {
        return themeProperty;
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
        var gptRequest = new GptRequest(themeProperty.get(), questionProperty.get());
        gptModel.sendQuestion(gptRequest);
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
}
