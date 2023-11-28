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
    private String answerGrammarText;
    private String answerShortText;
    private String answerLongText;
    private String answerGcpText;
    private Color answerGrammarCircleColor;
    private Color answerShortCircleColor;
    private Color answerLongCircleColor;
    private Color answerGcpCircleColor;
    private String answerGrammarTemperature;
    private String answerShortTemperature;
    private String answerLongTemperature;
    private String answerGcpTemperature;

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

    public WindowAssertion answerGrammarText(String answerGrammarText) {
        this.answerGrammarText = answerGrammarText;
        return this;
    }

    public WindowAssertion answerShortText(String answerShortText) {
        this.answerShortText = answerShortText;
        return this;
    }

    public WindowAssertion answerLongText(String answerLongText) {
        this.answerLongText = answerLongText;
        return this;
    }

    public WindowAssertion answerGcpText(String answerGcpText) {
        this.answerGcpText = answerGcpText;
        return this;
    }

    public WindowAssertion answerCircleColors(Color answerGrammarCircleColor, Color answerShortCircleColor,
                                              Color answerLongCircleColor, Color answerGcpCircleColor) {
        this.answerGrammarCircleColor = answerGrammarCircleColor;
        this.answerShortCircleColor = answerShortCircleColor;
        this.answerLongCircleColor = answerLongCircleColor;
        this.answerGcpCircleColor = answerGcpCircleColor;
        return this;
    }

    public WindowAssertion answerTemperatures(String grammarTemperature, String shortTemperature,
                                              String longTemperature, String gcpTemperature) {
        this.answerGrammarTemperature = grammarTemperature;
        this.answerShortTemperature = shortTemperature;
        this.answerLongTemperature = longTemperature;
        this.answerGcpTemperature = gcpTemperature;
        return this;
    }

    public WindowAssertion answerTemperaturesAllEmpty() {
        return answerTemperatures("", "", "", "");
    }

    public WindowAssertion answerTemperaturesDefault() {
        return answerTemperatures("50°", "60°", "70°", "30°");
    }

    void assertApp() {
        WaitForAsyncUtils.waitForFxEvents();
        verifyThat(app.getHistoryLabel(), hasText("Question history (" + historyItems.size() + "):"));
        verifyThat(app.getHistoryDeleteButton().getText(), equalTo("Delete"));
        verifyThat(app.getHistoryComboBox(), hasItems(historySize));
        verifyThat(app.getHistoryDeleteButton().isDisabled(), is(historyDeleteButtonDisabled));
        if (historySelectedItem != null) {
            assertThat(app.getHistoryComboBox().getSelectionModel().getSelectedItem())
                    .isEqualTo(app.storage.readInteraction(historySelectedItem.id()).orElseThrow());
        } else {
            assertThat(app.getHistoryComboBox().getSelectionModel().getSelectedItem()).isNull();
        }
        assertThat(app.getHistoryComboBox().getItems()).containsExactlyElementsOf(historyItems);
        if (app.model.getCurrentInteractionId() != null) {
            assertThat(app.model.getCurrentInteractionId()).isEqualTo(historySelectedItem.id());
        } else {
            assertThat(historySelectedItem).isNull();
        }
        assertThat(app.model.getHistory().stream()
                .map(interactionId -> app.storage.readInteraction(interactionId).orElseThrow()).toList())
                .isEqualTo(historyItems);

        verifyThat(app.getThemeLabel(), hasText("Theme (" + themeItems.size() + "):"));
        verifyThat(app.getThemeComboBox(), hasItems(themeSize));
        assertThat(app.getThemeComboBox().getSelectionModel().getSelectedItem()).isEqualTo(themeSelectedItem);
        assertThat(app.getThemeComboBox().getItems()).containsExactlyElementsOf(themeItems);
        assertThat(app.model.getEditedTheme()).isEqualTo(themeSelectedItem);

        verifyThat(app.getQuestionLabel(), hasText("Question:"));
        verifyThat(app.getQuestionSendButton().getText(), equalTo("_Question"));
        verifyThat(app.getDefinitionSendButton().getText(), equalTo("_Definition"));
        verifyThat(app.getGrammarSendButton().getText(), equalTo("_Grammar"));
        verifyThat(app.getFactSendButton().getText(), equalTo("_Fact"));
        verifyThat(app.getRegenerateButton().getText(), equalTo("_Resend"));
        assertThat(app.getQuestionTextArea().getText()).isEqualTo(questionText);
        assertThat(app.model.getEditedQuestion()).isEqualTo(modelEditedQuestion);

        verifyThat(app.getAnswerGrammarLabel(), hasText("Grammar\nanswer:"));
        verifyThat(app.getAnswerGrammarCopyButton().getText(), equalTo("Copy _1"));
        verifyThat(app.getAnswerGrammarRegenerateButton().getText(), equalTo("⟳"));
        app.verifyWebViewBody(app.getAnswerGrammarWebView(), answerGrammarText);
        assertThat(app.getAnswerGrammarCircle().getFill()).isEqualTo(answerGrammarCircleColor);
        assertThat(app.getAnswerGrammarTemperatureText().getText()).isEqualTo(answerGrammarTemperature);

        verifyThat(app.getAnswerShortLabel(), hasText("Short\nanswer:"));
        verifyThat(app.getAnswerShortCopyButton().getText(), equalTo("Copy _2"));
        verifyThat(app.getAnswerShortRegenerateButton().getText(), equalTo("⟳"));
        app.verifyWebViewBody(app.getAnswerShortWebView(), answerShortText);
        assertThat(app.getAnswerShortCircle().getFill()).isEqualTo(answerShortCircleColor);
        assertThat(app.getAnswerShortTemperatureText().getText()).isEqualTo(answerShortTemperature);

        verifyThat(app.getAnswerLongLabel(), hasText("Long\nanswer:"));
        verifyThat(app.getAnswerLongCopyButton().getText(), equalTo("Copy _3"));
        verifyThat(app.getAnswerLongRegenerateButton().getText(), equalTo("⟳"));
        app.verifyWebViewBody(app.getAnswerLongWebView(), answerLongText);
        assertThat(app.getAnswerLongCircle().getFill()).isEqualTo(answerLongCircleColor);
        assertThat(app.getAnswerLongTemperatureText().getText()).isEqualTo(answerLongTemperature);

        verifyThat(app.getAnswerGcpLabel(), hasText("Bard\nanswer:"));
        verifyThat(app.getAnswerGcpCopyButton().getText(), equalTo("Copy _4"));
        verifyThat(app.getAnswerGcpRegenerateButton().getText(), equalTo("⟳"));
        app.verifyWebViewBody(app.getAnswerGcpWebView(), answerGcpText);
        assertThat(app.getAnswerGcpCircle().getFill()).isEqualTo(answerGcpCircleColor);
        assertThat(app.getAnswerGcpTemperatureText().getText()).isEqualTo(answerGcpTemperature);
    }
}

