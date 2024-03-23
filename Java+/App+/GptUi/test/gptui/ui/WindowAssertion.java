package gptui.ui;

import gptui.model.storage.Interaction;
import gptui.model.storage.Theme;
import gptui.viewmodel.InteractionItem;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.assertj.core.api.SoftAssertions;
import org.testfx.util.WaitForAsyncUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;

public class WindowAssertion {
    private BaseGptUiTest app;
    private Node focus;
    private int historySizeFiltered;
    private int historySizeFull;
    private boolean historyDeleteButtonDisabled;
    private Interaction historySelectedItem;
    private List<Interaction> historyItems;
    private int themeSize;
    private Theme themeSelectedItem;
    private List<Theme> themeItems;
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

    public WindowAssertion focus(Node focus) {
        this.focus = focus;
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

    public WindowAssertion themeSelectedItem(Theme themeSelectedItem) {
        this.themeSelectedItem = themeSelectedItem;
        return this;
    }

    public WindowAssertion themeItems(Theme... themeItems) {
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
        return answerTextTemperatures(50, 60, 70, 90);
    }

    public WindowAssertion answerSpinnerTemperaturesDefault() {
        return answerSpinnerTemperatures(50, 60, 70, 90);
    }

    private static String nodeFullId(Node node) {
        var ids = new ArrayList<String>();
        while (node != null) {
            ids.add(node.getId() != null ? node.getId() : "null");
            node = node.getParent();
        }
        return String.join("/", ids);
    }

    public void assertApp() {
        WaitForAsyncUtils.waitForFxEvents();
        var soft = new SoftAssertions();
        {
            soft.assertThat(app.scene().getFocusOwner()).as("Focus")
                    .withRepresentation(node -> node != null ?
                            format("%s[ids=%s]", node.getClass().getSimpleName(), nodeFullId((Node) node)) : "null")
                    .isEqualTo(focus);
        }
        {
            var history = app.history();
            soft.assertThat(history.label().getText()).as("History/Label/Text")
                    .isEqualTo("Question history (" + historySizeFiltered + "/" + historySizeFull + "):");
            soft.assertThat(history.deleteButton().getText()).as("History/DeleteButton/Text").isEqualTo("Delete");
            soft.assertThat(history.comboBox().getItems()).as("History/ComboBox/Items").hasSize(historySizeFiltered);
            soft.assertThat(history.deleteButton().isDisabled()).as("History/DeleteButton/Disabled").isEqualTo(historyDeleteButtonDisabled);
            var historySelectedItemId = historySelectedItem != null ? historySelectedItem.id() : null;
            var cbSelectedItem = history.comboBox().getSelectionModel().getSelectedItem();
            var cbSelectedItemStr = cbSelectedItem != null ? cbSelectedItem.interaction() != null ? cbSelectedItem.interaction().toString() : null : null;
            soft.assertThat(cbSelectedItemStr).as("History/ComboBox/SelectedItem")
                    .isEqualTo(app.storage.readInteraction(historySelectedItemId).map(Interaction::toString).orElse(null));
            soft.assertThat(history.comboBox().getItems().stream().map(InteractionItem::interaction)).as("History/ComboBox/Items")
                    .containsExactlyElementsOf(historyItems);
            soft.assertThat(app.stateModel.getCurrentInteractionId()).as("Model/CurrentInteractionId").isEqualTo(historySelectedItemId);
            soft.assertThat(app.stateModel.getFilteredHistory()).as("Model/History/Items").containsExactlyElementsOf(historyItems);
        }

        {
            var theme = app.theme();
            soft.assertThat(theme.label().getText()).as("Theme/Label/Text").isEqualTo("_Theme (" + themeItems.size() + "):");
            soft.assertThat(theme.comboBox().getItems()).as("Theme/ComboBox/ItemsSize").hasSize(themeSize);
            var themeSelectedItemTitle = themeSelectedItem != null ? themeSelectedItem.title() : null;
            soft.assertThat(theme.comboBox().getSelectionModel().getSelectedItem()).as("Theme/ComboBox/SelectedItem")
                    .isEqualTo(themeSelectedItem);
            soft.assertThat(theme.comboBox().getItems()).as("Theme/ComboBox/Items").containsExactlyElementsOf(themeItems);
            soft.assertThat(theme.filterHistoryCheckBox().isSelected()).as("Theme/Label/Text").isEqualTo(filterHistorySelected);
            var themeTitle = app.stateModel.getCurrentTheme() != null ? app.stateModel.getCurrentTheme().title() : null;
            soft.assertThat(themeTitle).as("Theme/Model/CurrentTheme").isEqualTo(themeSelectedItemTitle);
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
            soft.assertThat(app.stateModel.getEditedQuestion()).as("Question/Model/Text").isEqualTo(modelEditedQuestion);
            soft.assertThat(app.stateModel.isEnteringNewQuestion()).as("Question/Model/isEnteringNewQuestion").isEqualTo(isEnteringNewQuestion);
        }

        {
            var answer = app.grammarAnswer();
            soft.assertThat(answer.label().getText()).as("Answer/Grammar/Label/Text").isEqualTo("Grammar\nanswer:");
            soft.assertThat(answer.copyButton().getText()).as("Answer/Grammar/CopyButton/Text").isEqualTo("Copy _1");
            soft.assertThat(answer.regenerateButton().getText()).as("Answer/Grammar/RegenerateButton/Text").isEqualTo("⟳");
            app.verifyWebViewBody(soft, "Answer/Grammar/WebView/Body", answer.webView(), grammarAnswer.text);
            soft.assertThat(colorToString(answer.circle().getFill())).as("Answer/Grammar/Circle/Fill").isEqualTo(colorToString(grammarAnswer.circleColor));
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
            soft.assertThat(colorToString(answer.circle().getFill())).as("Answer/Short/Circle/Fill").isEqualTo(colorToString(shortA().circleColor));
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
            soft.assertThat(colorToString(answer.circle().getFill())).as("Answer/Long/Circle/Fill").isEqualTo(colorToString(longA().circleColor));
            soft.assertThat(answer.temperatureText().getText()).as("Answer/Long/Temperature/Text")
                    .isEqualTo(temperatureToString(longA().temperatureText));
            soft.assertThat(answer.temperatureSpinner().getValue()).as("Answer/Long/TemperatureSpinner/Value")
                    .isEqualTo(temperatureToInteger(longA().temperatureSpinner));
        }

        {
            var answer = app.gcpAnswer();
            soft.assertThat(answer.label().getText()).as("Answer/GCP/Label/Text").isEqualTo("GCP\nanswer:");
            soft.assertThat(answer.copyButton().getText()).as("Answer/GCP/CopyButton/Text").isEqualTo("Copy _4");
            soft.assertThat(answer.regenerateButton().getText()).as("Answer/GCP/RegenerateButton/Text").isEqualTo("⟳");
            app.verifyWebViewBody(soft, "Answer/GCP/WebView/Body", answer.webView(), gcpA().text);
            soft.assertThat(colorToString(answer.circle().getFill())).as("Answer/GCP/Circle/Fill").isEqualTo(colorToString(gcpA().circleColor));
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

    private final Map<Paint, String> colors = Map.of(
            RED, "RED",
            BLUE, "BLUE",
            GREEN, "GREEN",
            WHITE, "WHITE"
    );

    private String colorToString(Paint color) {
        return colors.getOrDefault(color, color.toString());
    }
}

