package gptui.fxml;

import org.junit.jupiter.api.Test;

import static java.time.Duration.ofMillis;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.containsExactlyItems;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;

class GptUiFXMLTest extends BaseGptUiTest {
    private static final String THEME_1 = "Theme 1";
    private static final String THEME_2 = "Theme 2";
    private static final String QUESTION_1 = "The question 1";
    private static final String EXP_CORRECTNESS_HTML_BODY_1 = "<p>Correctness answer 1</p>\n";
    private static final String EXP_SHORT_HTML_BODY_1 = "<p>Short answer 1</p>\n";
    private static final String EXP_LONG_HTML_BODY_1 = "<p>Long answer 1</p>\n";

    @Test
    void shouldSendQuestion() {
        initialState();
        sendFirstQuestion();
        sendSecondQuestion();
        choosePreviousInteraction();
    }

    private void initialState() {
        verifyThat(getInteractionHistoryComboBox(), hasItems(0));
        verifyThat(getThemeComboBox(), hasItems(0));
        verifyThat(getQuestionTextArea().getText(), emptyString());
        verifyWebViewBody(getQuestionCorrectnessWebView(), "");
        verifyWebViewBody(getShortAnswerWebView(), "");
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(WHITE);
        verifyWebViewBody(getLongAnswerWebView(), "");
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(WHITE);
    }

    private void sendFirstQuestion() {
        clickOn(getThemeComboBox());
        overWrite(THEME_1);
        clickOn(getQuestionTextArea());

        overWrite(QUESTION_1);
        assertThat(getQuestionTextArea().getText()).isEqualTo(QUESTION_1);
        verifyThat(getThemeComboBox(), hasSelectedItem(THEME_1));
        verifyThat(getThemeComboBox(), hasItems(0));

        gptApi
                .put("has grammatical mistakes", "Correctness answer 1", ofMillis(500))
                .put("a short response", "Short answer 1", ofMillis(1000))
                .put("a detailed response", "Long answer 1", ofMillis(1500));
        clickOn(getQuestionSendButton());
        verifyWebViewBody(getQuestionCorrectnessWebView(), "");
        verifyWebViewBody(getShortAnswerWebView(), "");
        verifyWebViewBody(getLongAnswerWebView(), "");
        sleep(3000);
        verifyWebViewBody(getQuestionCorrectnessWebView(), EXP_CORRECTNESS_HTML_BODY_1);
        verifyWebViewBody(getShortAnswerWebView(), EXP_SHORT_HTML_BODY_1);
        verifyWebViewBody(getLongAnswerWebView(), EXP_LONG_HTML_BODY_1);
        var allInteractions = storage.readAllInteractions();
        var currentInteraction = allInteractions.get(0);
        verifyThat(getInteractionHistoryComboBox(), containsExactlyItems(allInteractions.toArray()));
        verifyThat(getInteractionHistoryComboBox(), hasSelectedItem(currentInteraction));
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(GREEN);
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(GREEN);
        clickOn(getShortAnswerCopyButton());
        verifyHtmlClipboardContent(EXP_SHORT_HTML_BODY_1);
        clickOn(getLongAnswerCopyButton());
        verifyHtmlClipboardContent(EXP_LONG_HTML_BODY_1);
    }

    private void sendSecondQuestion() {
        clickOn(getThemeComboBox());
        overWrite(THEME_2);
        clickOn(getQuestionTextArea());
        var question2 = "The question 2";
        overWrite(question2);
        assertThat(getQuestionTextArea().getText()).isEqualTo(question2);
        verifyThat(getThemeComboBox(), hasSelectedItem(THEME_2));
        verifyThat(getThemeComboBox(), containsExactlyItems(THEME_1));

        gptApi
                .put("has grammatical mistakes", "Correctness answer 2", ofMillis(500))
                .put("a short response", "Short answer 2", ofMillis(1000))
                .put("a detailed response", "Long answer 2", ofMillis(1500));
        verifyWebViewBody(getQuestionCorrectnessWebView(), EXP_CORRECTNESS_HTML_BODY_1);
        verifyWebViewBody(getShortAnswerWebView(), EXP_SHORT_HTML_BODY_1);
        verifyWebViewBody(getLongAnswerWebView(), EXP_LONG_HTML_BODY_1);
        clickOn(getQuestionSendButton());
        verifyWebViewBody(getQuestionCorrectnessWebView(), "");
        verifyWebViewBody(getShortAnswerWebView(), "");
        verifyWebViewBody(getLongAnswerWebView(), "");
        sleep(3000);
        verifyWebViewBody(getQuestionCorrectnessWebView(), "<p>Correctness answer 2</p>\n");
        var expShortHtmlBody2 = "<p>Short answer 2</p>\n";
        verifyWebViewBody(getShortAnswerWebView(), expShortHtmlBody2);
        var expLongHtmlBody2 = "<p>Long answer 2</p>\n";
        verifyWebViewBody(getLongAnswerWebView(), expLongHtmlBody2);
        var allInteractions = storage.readAllInteractions();
        var currentInteraction = allInteractions.get(0);
        verifyThat(getInteractionHistoryComboBox(), containsExactlyItems(allInteractions.toArray()));
        verifyThat(getInteractionHistoryComboBox(), hasSelectedItem(currentInteraction));
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(GREEN);
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(GREEN);
        clickOn(getShortAnswerCopyButton());
        verifyHtmlClipboardContent(expShortHtmlBody2);
        clickOn(getLongAnswerCopyButton());
        verifyHtmlClipboardContent(expLongHtmlBody2);
    }

    private void choosePreviousInteraction() {
        verifyThat(getThemeComboBox(), hasSelectedItem(THEME_2));
        verifyThat(getThemeComboBox(), containsExactlyItems(THEME_1, THEME_2));
        clickOn(getInteractionHistoryComboBox()).clickOn(String.format("%s: %s", THEME_1, QUESTION_1));
        verifyThat(getThemeComboBox(), hasSelectedItem(THEME_1));
        verifyThat(getThemeComboBox(), containsExactlyItems(THEME_1, THEME_2));
        assertThat(getQuestionTextArea().getText()).isEqualTo(QUESTION_1);
        verifyWebViewBody(getQuestionCorrectnessWebView(), EXP_CORRECTNESS_HTML_BODY_1);
        verifyWebViewBody(getShortAnswerWebView(), EXP_SHORT_HTML_BODY_1);
        verifyWebViewBody(getLongAnswerWebView(), EXP_LONG_HTML_BODY_1);
        var allInteractions = storage.readAllInteractions();
        var currentInteraction = allInteractions.get(1);
        verifyThat(getInteractionHistoryComboBox(), containsExactlyItems(allInteractions.toArray()));
        verifyThat(getInteractionHistoryComboBox(), hasSelectedItem(currentInteraction));
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(GREEN);
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(GREEN);
        clickOn(getShortAnswerCopyButton());
        verifyHtmlClipboardContent(EXP_SHORT_HTML_BODY_1);
        clickOn(getLongAnswerCopyButton());
        verifyHtmlClipboardContent(EXP_LONG_HTML_BODY_1);
    }

}