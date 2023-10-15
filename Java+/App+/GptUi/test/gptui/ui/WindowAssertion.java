package gptui.ui;

import gptui.storage.Interaction;
import javafx.scene.paint.Color;

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
    private Color answerGrammarCircleColor;
    private Color answerShortCircleColor;
    private Color answerLongCircleColor;

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

    public WindowAssertion answerCircleColors(Color grammarCircleColor, Color shortCircleColor, Color longCircleColor) {
        this.answerGrammarCircleColor = grammarCircleColor;
        this.answerShortCircleColor = shortCircleColor;
        this.answerLongCircleColor = longCircleColor;
        return this;
    }

    void assertApp() {
        verifyThat(app.getHistoryLabel(), hasText("Question history (" + historyItems.size() + "):"));
        verifyThat(app.getHistoryDeleteButton().getText(), equalTo("Delete"));
        verifyThat(app.getHistoryComboBox(), hasItems(historySize));
        verifyThat(app.getHistoryDeleteButton().isDisabled(), is(historyDeleteButtonDisabled));
        assertThat(app.getHistoryComboBox().getSelectionModel().getSelectedItem()).isEqualTo(historySelectedItem);
        assertThat(app.getHistoryComboBox().getItems()).containsExactlyElementsOf(historyItems);
        assertThat(app.model.getCurrentInteraction()).isEqualTo(historySelectedItem);
        assertThat(app.model.getHistory()).isEqualTo(historyItems);

        verifyThat(app.getThemeLabel(), hasText("Theme (" + themeItems.size() + "):"));
        verifyThat(app.getThemeComboBox(), hasItems(themeSize));
        assertThat(app.getThemeComboBox().getSelectionModel().getSelectedItem()).isEqualTo(themeSelectedItem);
        assertThat(app.getThemeComboBox().getItems()).containsExactlyElementsOf(themeItems);
        assertThat(app.model.getEditedTheme()).isEqualTo(themeSelectedItem);
        assertThat(app.model.getThemeList()).isEqualTo(themeItems);

        verifyThat(app.getQuestionLabel(), hasText("Question:"));
        verifyThat(app.getQuestionSendButton().getText(), equalTo("Question"));
        verifyThat(app.getDefinitionSendButton().getText(), equalTo("Definition"));
        verifyThat(app.getGrammarSendButton().getText(), equalTo("Grammar"));
        verifyThat(app.getFactSendButton().getText(), equalTo("Fact"));
        assertThat(app.getQuestionTextArea().getText()).isEqualTo(questionText);
        assertThat(app.model.getEditedQuestion()).isEqualTo(modelEditedQuestion);

        verifyThat(app.getAnswerGrammarLabel(), hasText("Grammar\nanswer:"));
        verifyThat(app.getAnswerGrammarCopyButton().getText(), equalTo("Copy"));
        app.verifyWebViewBody(app.getAnswerGrammarWebView(), answerGrammarText);
        assertThat(app.getAnswerGrammarCircle().getFill()).isEqualTo(answerGrammarCircleColor);
        assertThat(app.getAnswerGrammarCopyButton().isDisabled()).isTrue();

        verifyThat(app.getAnswerShortLabel(), hasText("Short\nanswer:"));
        verifyThat(app.getAnswerShortCopyButton().getText(), equalTo("Copy"));
        app.verifyWebViewBody(app.getAnswerShortWebView(), answerShortText);
        assertThat(app.getAnswerShortCircle().getFill()).isEqualTo(answerShortCircleColor);
        assertThat(app.getAnswerShortCopyButton().isDisabled()).isFalse();

        verifyThat(app.getAnswerLongLabel(), hasText("Long\nanswer:"));
        verifyThat(app.getAnswerLongCopyButton().getText(), equalTo("Copy"));
        app.verifyWebViewBody(app.getAnswerLongWebView(), answerLongText);
        assertThat(app.getAnswerLongCircle().getFill()).isEqualTo(answerLongCircleColor);
        assertThat(app.getAnswerLongCopyButton().isDisabled()).isFalse();
    }
}

