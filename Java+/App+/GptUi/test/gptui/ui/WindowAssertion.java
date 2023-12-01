package gptui.ui;

import gptui.storage.Interaction;
import javafx.scene.paint.Color;
import org.testfx.util.WaitForAsyncUtils;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class WindowAssertion {
    private BaseGptUiTest app;
    private int historySize;
    private boolean historyDeleteButtonDisabled;
    private Interaction historySelectedItem;
    private List<Interaction> historyItems;
    private int themeSize;
    private String themeSelectedItem;
    private List<String> themeItems;
    private String questionText;
    private String modelEditedQuestion;
    private final AnswerInfo grammarAnswer = new AnswerInfo();
    private final AnswerInfo shortAnswer = new AnswerInfo();
    private final AnswerInfo longAnswer = new AnswerInfo();
    private final AnswerInfo gcpAnswer = new AnswerInfo();

    class AnswerInfo {
        private String text;
        private Color circleColor;
        private Integer temperatureText;
        private Integer temperatureSpinner;

        public WindowAssertion text(String text) {
            this.text = text;
            return WindowAssertion.this;
        }

        public void circleColor(Color circleColor) {
            this.circleColor = circleColor;
        }

        public void temperatureText(Integer temperature) {
            this.temperatureText = temperature;
        }

        public void temperatureSpinner(Integer temperature) {
            this.temperatureSpinner = temperature;
        }
    }

    public static WindowAssertion builder() {
        return new WindowAssertion();
    }

    public WindowAssertion app(BaseGptUiTest app) {
        this.app = app;
        return this;
    }

    public WindowAssertion historySize(int historySize) {
        this.historySize = historySize;
        return this;
    }

    public WindowAssertion historyDeleteButtonDisabled(boolean historyDeleteButtonDisabled) {
        this.historyDeleteButtonDisabled = historyDeleteButtonDisabled;
        return this;
    }

    public WindowAssertion historySelectedItem(Interaction historySelectedItem) {
        this.historySelectedItem = historySelectedItem;
        return this;
    }

    public WindowAssertion historyItems(List<Interaction> historyItems) {
        this.historyItems = historyItems;
        return this;
    }

    public WindowAssertion historyItems(Interaction... historyItems) {
        this.historyItems = Arrays.asList(historyItems);
        return this;
    }

    public WindowAssertion themeSize(int themeSize) {
        this.themeSize = themeSize;
        return this;
    }

    public WindowAssertion themeSelectedItem(String themeSelectedItem) {
        this.themeSelectedItem = themeSelectedItem;
        return this;
    }

    public WindowAssertion themeItems(String... themeItems) {
        this.themeItems = Arrays.asList(themeItems);
        return this;
    }

    public WindowAssertion questionText(String questionText) {
        this.questionText = questionText;
        return this;
    }

    public WindowAssertion modelEditedQuestion(String modelEditedQuestion) {
        this.modelEditedQuestion = modelEditedQuestion;
        return this;
    }

    public AnswerInfo grammarA() {
        return grammarAnswer;
    }

    public AnswerInfo shortA() {
        return shortAnswer;
    }

    public AnswerInfo longA() {
        return longAnswer;
    }

    public AnswerInfo gcpA() {
        return gcpAnswer;
    }

    public WindowAssertion answerCircleColors(Color answerGrammarCircleColor, Color answerShortCircleColor,
                                              Color answerLongCircleColor, Color answerGcpCircleColor) {
        grammarA().circleColor(answerGrammarCircleColor);
        shortA().circleColor(answerShortCircleColor);
        longA().circleColor(answerLongCircleColor);
        gcpA().circleColor(answerGcpCircleColor);
        return this;
    }

    public WindowAssertion answerTextTemperatures(Integer grammarTemperature, Integer shortTemperature,
                                                  Integer longTemperature, Integer gcpTemperature) {
        grammarA().temperatureText(grammarTemperature);
        shortA().temperatureText(shortTemperature);
        longA().temperatureText(longTemperature);
        gcpA().temperatureText(gcpTemperature);
        return this;
    }

    public WindowAssertion answerSpinnerTemperatures(Integer grammarTemperature, Integer shortTemperature,
                                                     Integer longTemperature, Integer gcpTemperature) {
        grammarA().temperatureSpinner(grammarTemperature);
        shortA().temperatureSpinner(shortTemperature);
        longA().temperatureSpinner(longTemperature);
        gcpA().temperatureSpinner(gcpTemperature);
        return this;
    }

    public WindowAssertion answerTextTemperaturesAllEmpty() {
        return answerTextTemperatures(null,
                null, null, null);
    }

    public WindowAssertion answerTextTemperaturesDefault() {
        return answerTextTemperatures(50, 60, 70, 30);
    }

    public WindowAssertion answerSpinnerTemperaturesDefault() {
        return answerSpinnerTemperatures(50, 60, 70, 30);
    }

    void assertApp() {
        WaitForAsyncUtils.waitForFxEvents();

        {
            var history = app.history();
            verifyThat(history.label(), hasText("Question history (" + historyItems.size() + "):"));
            verifyThat(history.deleteButton().getText(), equalTo("Delete"));
            verifyThat(history.comboBox(), hasItems(historySize));
            verifyThat(history.deleteButton().isDisabled(), is(historyDeleteButtonDisabled));
            if (historySelectedItem != null) {
                assertThat(history.comboBox().getSelectionModel().getSelectedItem())
                        .isEqualTo(app.storage.readInteraction(historySelectedItem.id()).orElseThrow());
            } else {
                assertThat(history.comboBox().getSelectionModel().getSelectedItem()).isNull();
            }
            assertThat(history.comboBox().getItems()).containsExactlyElementsOf(historyItems);
            if (app.model.getCurrentInteractionId() != null) {
                assertThat(app.model.getCurrentInteractionId()).isEqualTo(historySelectedItem.id());
            } else {
                assertThat(historySelectedItem).isNull();
            }
            assertThat(app.model.getHistory().stream()
                    .map(interactionId -> app.storage.readInteraction(interactionId).orElseThrow()).toList())
                    .isEqualTo(historyItems);
        }

        {
            var theme = app.theme();
            verifyThat(theme.label(), hasText("Theme (" + themeItems.size() + "):"));
            verifyThat(theme.comboBox(), hasItems(themeSize));
            assertThat(theme.comboBox().getSelectionModel().getSelectedItem()).isEqualTo(themeSelectedItem);
            assertThat(theme.comboBox().getItems()).containsExactlyElementsOf(themeItems);
            assertThat(app.model.getEditedTheme()).isEqualTo(themeSelectedItem);
        }

        {
            var question = app.question();
            verifyThat(question.label(), hasText("Question:"));
            verifyThat(question.questionButton().getText(), equalTo("_Question"));
            verifyThat(question.definitionButton().getText(), equalTo("_Definition"));
            verifyThat(question.grammarButton().getText(), equalTo("_Grammar"));
            verifyThat(question.factButton().getText(), equalTo("_Fact"));
            verifyThat(question.regenerateButton().getText(), equalTo("_Resend"));
            assertThat(question.textArea().getText()).isEqualTo(questionText);
            assertThat(app.model.getEditedQuestion()).isEqualTo(modelEditedQuestion);
        }

        {
            var answer = app.grammarAnswer();
            verifyThat(answer.label(), hasText("Grammar\nanswer:"));
            verifyThat(answer.copyButton().getText(), equalTo("Copy _1"));
            verifyThat(answer.regenerateButton().getText(), equalTo("⟳"));
            app.verifyWebViewBody(answer.webView(), grammarAnswer.text);
            assertThat(answer.circle().getFill()).isEqualTo(grammarAnswer.circleColor);
            assertThat(answer.temperatureText().getText()).isEqualTo(temperatureToString(grammarA().temperatureText));
            assertThat(answer.temperatureSpinner().getValue()).isEqualTo(temperatureToInteger(grammarA().temperatureSpinner));
        }

        {
            var answer = app.shortAnswer();
            verifyThat(answer.label(), hasText("Short\nanswer:"));
            verifyThat(answer.copyButton().getText(), equalTo("Copy _2"));
            verifyThat(answer.regenerateButton().getText(), equalTo("⟳"));
            app.verifyWebViewBody(answer.webView(), shortA().text);
            assertThat(answer.circle().getFill()).isEqualTo(shortA().circleColor);
            assertThat(answer.temperatureText().getText()).isEqualTo(temperatureToString(shortA().temperatureText));
            assertThat(answer.temperatureSpinner().getValue()).isEqualTo(temperatureToInteger(shortA().temperatureSpinner));
        }

        {
            var answer = app.longAnswer();
            verifyThat(answer.label(), hasText("Long\nanswer:"));
            verifyThat(answer.copyButton().getText(), equalTo("Copy _3"));
            verifyThat(answer.regenerateButton().getText(), equalTo("⟳"));
            app.verifyWebViewBody(answer.webView(), longA().text);
            assertThat(answer.circle().getFill()).isEqualTo(longA().circleColor);
            assertThat(answer.temperatureText().getText()).isEqualTo(temperatureToString(longA().temperatureText));
            assertThat(answer.temperatureSpinner().getValue()).isEqualTo(temperatureToInteger(longA().temperatureSpinner));
        }

        {
            var answer = app.gcpAnswer();
            verifyThat(answer.label(), hasText("Bard\nanswer:"));
            verifyThat(answer.copyButton().getText(), equalTo("Copy _4"));
            verifyThat(answer.regenerateButton().getText(), equalTo("⟳"));
            app.verifyWebViewBody(answer.webView(), gcpA().text);
            assertThat(answer.circle().getFill()).isEqualTo(gcpA().circleColor);
            assertThat(answer.temperatureText().getText()).isEqualTo(temperatureToString(gcpA().temperatureText));
            assertThat(answer.temperatureSpinner().getValue()).isEqualTo(temperatureToInteger(gcpA().temperatureSpinner));
        }
    }

    private String temperatureToString(Integer temperature) {
        return temperature != null ? temperature + "°" : "";
    }

    private Integer temperatureToInteger(Integer temperature) {
        return temperature != null ? temperature : 0;
    }
}

