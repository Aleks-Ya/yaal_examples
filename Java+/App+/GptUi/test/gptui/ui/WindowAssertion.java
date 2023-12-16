package gptui.ui;

import gptui.storage.Interaction;
import javafx.scene.paint.Color;
import org.assertj.core.api.SoftAssertions;
import org.testfx.util.WaitForAsyncUtils;

import java.util.Arrays;
import java.util.List;

public class WindowAssertion {
    private BaseGptUiTest app;
    private int historySizeFiltered;
    private int historySizeFull;
    private boolean historyDeleteButtonDisabled;
    private Interaction historySelectedItem;
    private List<Interaction> historyItems;
    private int themeSize;
    private String themeSelectedItem;
    private List<String> themeItems;
    private Boolean filterHistorySelected;
    private String questionText;
    private Boolean isEnteringNewQuestion;
    private String modelEditedQuestion;
    private final AnswerInfo grammarAnswer = new AnswerInfo();
    private final AnswerInfo shortAnswer = new AnswerInfo();
    private final AnswerInfo longAnswer = new AnswerInfo();
    private final AnswerInfo gcpAnswer = new AnswerInfo();

    public class AnswerInfo {
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

    public WindowAssertion historySize(int historySizeFiltered, int historySizeFull) {
        this.historySizeFiltered = historySizeFiltered;
        this.historySizeFull = historySizeFull;
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

    public WindowAssertion themeFilterHistorySelected(Boolean filterHistorySelected) {
        this.filterHistorySelected = filterHistorySelected;
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

    public WindowAssertion modelIsEnteringNewQuestion(Boolean isEnteringNewQuestion) {
        this.isEnteringNewQuestion = isEnteringNewQuestion;
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

    public void assertApp() {
        WaitForAsyncUtils.waitForFxEvents();
        var soft = new SoftAssertions();
        {
            var history = app.history();
            soft.assertThat(history.label().getText()).as("History/Label/Text")
                    .isEqualTo("Question history (" + historySizeFiltered + "/" + historySizeFull + "):");
            soft.assertThat(history.deleteButton().getText()).as("History/DeleteButton/Text").isEqualTo("Delete");
            soft.assertThat(history.comboBox().getItems()).as("History/ComboBox/Items").hasSize(historySizeFiltered);
            soft.assertThat(history.deleteButton().isDisabled()).as("History/DeleteButton/Disabled").isEqualTo(historyDeleteButtonDisabled);
            if (historySelectedItem != null) {
                soft.assertThat(history.comboBox().getSelectionModel().getSelectedItem()).as("History/ComboBox/SelectedItem")
                        .isEqualTo(app.storage.readInteraction(historySelectedItem.id()).orElseThrow());
            } else {
                soft.assertThat(history.comboBox().getSelectionModel().getSelectedItem())
                        .as("History/ComboBox/SelectedItem").isNull();
            }
            soft.assertThat(history.comboBox().getItems()).as("History/ComboBox/Items").containsExactlyElementsOf(historyItems);
            if (app.model.getCurrentInteractionId() != null) {
                soft.assertThat(app.model.getCurrentInteractionId()).as("Model/CurrentInteractionId").isEqualTo(historySelectedItem.id());
            } else {
                soft.assertThat(historySelectedItem).as("History/ComboBox/SelectedItem").isNull();
            }
            soft.assertThat(app.model.getFilteredHistory()).as("Model/History/Items").isEqualTo(historyItems);
        }

        {
            var theme = app.theme();
            soft.assertThat(theme.label().getText()).as("Theme/Label/Text").isEqualTo("Theme (" + themeItems.size() + "):");
            soft.assertThat(theme.comboBox().getItems()).as("Theme/ComboBox/ItemsSize").hasSize(themeSize);
            soft.assertThat(theme.comboBox().getSelectionModel().getSelectedItem()).as("Theme/ComboBox/SelectedItem").isEqualTo(themeSelectedItem);
            soft.assertThat(theme.comboBox().getItems()).as("Theme/ComboBox/Items").containsExactlyElementsOf(themeItems);
            soft.assertThat(theme.filterHistoryCheckBox().isSelected()).as("Theme/Label/Text").isEqualTo(filterHistorySelected);
            soft.assertThat(app.model.getCurrentTheme()).as("Theme/Model/CurrentTheme").isEqualTo(themeSelectedItem);
        }

        {
            var question = app.question();
            soft.assertThat(question.label().getText()).as("Question/Label/Text").isEqualTo("Question:");
            soft.assertThat(question.questionButton().getText()).as("Question/Button/Text").isEqualTo("_Question");
            soft.assertThat(question.definitionButton().getText()).as("Definition/Button/Text").isEqualTo("_Definition");
            soft.assertThat(question.grammarButton().getText()).as("Grammar/Button/Text").isEqualTo("_Grammar");
            soft.assertThat(question.factButton().getText()).as("Fact/Button/Text").isEqualTo("_Fact");
            soft.assertThat(question.regenerateButton().getText()).as("Regenerate/Button/Text").isEqualTo("_Resend");
            soft.assertThat(question.textArea().getText()).as("Question/TextArea/Text").isEqualTo(questionText);
            soft.assertThat(app.model.getEditedQuestion()).as("Question/Model/Text").isEqualTo(modelEditedQuestion);
            soft.assertThat(app.model.isEnteringNewQuestion()).as("Question/Model/isEnteringNewQuestion").isEqualTo(isEnteringNewQuestion);
        }

        {
            var answer = app.grammarAnswer();
            soft.assertThat(answer.label().getText()).as("Answer/Grammar/Label/Text").isEqualTo("Grammar\nanswer:");
            soft.assertThat(answer.copyButton().getText()).as("Answer/Grammar/CopyButton/Text").isEqualTo("Copy _1");
            soft.assertThat(answer.regenerateButton().getText()).as("Answer/Grammar/RegenerateButton/Text").isEqualTo("⟳");
            app.verifyWebViewBody(soft, "Answer/Grammar/WebView/Body", answer.webView(), grammarAnswer.text);
            soft.assertThat(answer.circle().getFill()).as("Answer/Grammar/Circle/Fill").isEqualTo(grammarAnswer.circleColor);
            soft.assertThat(answer.temperatureText().getText()).as("Answer/Grammar/Temperature/Text")
                    .isEqualTo(temperatureToString(grammarA().temperatureText));
            soft.assertThat(answer.temperatureSpinner().getValue()).as("Answer/Grammar/TemperatureSpinner/Value")
                    .isEqualTo(temperatureToInteger(grammarA().temperatureSpinner));
        }

        {
            var answer = app.shortAnswer();
            soft.assertThat(answer.label().getText()).as("Answer/Short/Label/Text").isEqualTo("Short\nanswer:");
            soft.assertThat(answer.copyButton().getText()).as("Answer/Short/CopyButton/Text").isEqualTo("Copy _2");
            soft.assertThat(answer.regenerateButton().getText()).as("Answer/Short/RegenerateButton/Text").isEqualTo("⟳");
            app.verifyWebViewBody(soft, "Answer/Short/WebView/Body", answer.webView(), shortA().text);
            soft.assertThat(answer.circle().getFill()).as("Answer/Short/Circle/Fill").isEqualTo(shortA().circleColor);
            soft.assertThat(answer.temperatureText().getText()).as("Answer/Short/Temperature/Text")
                    .isEqualTo(temperatureToString(shortA().temperatureText));
            soft.assertThat(answer.temperatureSpinner().getValue()).as("Answer/Short/TemperatureSpinner/Value")
                    .isEqualTo(temperatureToInteger(shortA().temperatureSpinner));
        }

        {
            var answer = app.longAnswer();
            soft.assertThat(answer.label().getText()).as("Answer/Long/Label/Text").isEqualTo("Long\nanswer:");
            soft.assertThat(answer.copyButton().getText()).as("Answer/Long/CopyButton/Text").isEqualTo("Copy _3");
            soft.assertThat(answer.regenerateButton().getText()).as("Answer/Long/RegenerateButton/Text").isEqualTo("⟳");
            app.verifyWebViewBody(soft, "Answer/Long/WebView/Body", answer.webView(), longA().text);
            soft.assertThat(answer.circle().getFill()).as("Answer/Long/Circle/Fill").isEqualTo(longA().circleColor);
            soft.assertThat(answer.temperatureText().getText()).as("Answer/Long/Temperature/Text")
                    .isEqualTo(temperatureToString(longA().temperatureText));
            soft.assertThat(answer.temperatureSpinner().getValue()).as("Answer/Long/TemperatureSpinner/Value")
                    .isEqualTo(temperatureToInteger(longA().temperatureSpinner));
        }

        {
            var answer = app.gcpAnswer();
            soft.assertThat(answer.label().getText()).as("Answer/GCP/Label/Text").isEqualTo("Bard\nanswer:");
            soft.assertThat(answer.copyButton().getText()).as("Answer/GCP/CopyButton/Text").isEqualTo("Copy _4");
            soft.assertThat(answer.regenerateButton().getText()).as("Answer/GCP/RegenerateButton/Text").isEqualTo("⟳");
            app.verifyWebViewBody(soft, "Answer/GCP/WebView/Body", answer.webView(), gcpA().text);
            soft.assertThat(answer.circle().getFill()).as("Answer/GCP/Circle/Fill").isEqualTo(gcpA().circleColor);
            soft.assertThat(answer.temperatureText().getText()).as("Answer/GCP/Temperature/Text")
                    .isEqualTo(temperatureToString(gcpA().temperatureText));
            soft.assertThat(answer.temperatureSpinner().getValue()).as("Answer/GCP/TemperatureSpinner/Value")
                    .isEqualTo(temperatureToInteger(gcpA().temperatureSpinner));
        }
        soft.assertAll();
    }

    private String temperatureToString(Integer temperature) {
        return temperature != null ? temperature + "°" : "";
    }

    private Integer temperatureToInteger(Integer temperature) {
        return temperature != null ? temperature : 0;
    }
}

