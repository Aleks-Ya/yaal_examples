package gptui;

import org.junit.jupiter.api.Test;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.containsExactlyItems;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class SendQuestionGptViewTest extends BaseGptViewTest {
    private static final String theme1 = "Theme 1";
    private static final String theme2 = "Theme 2";

    @Test
    void shouldSendQuestion() {
        initialState();
        sendFirstQuestion();
        sendSecondQuestion();
    }

    private void initialState() {
        verifyThat(getThemeLabel(), hasText("Theme:"));
        verifyThat(getInteractionHistoryComboBox(), hasItems(0));
        verifyThat(getInteractionHistoryLabel(), hasText("Question history:"));
        verifyThat(getThemeComboBox(), hasItems(0));
        verifyThat(getQuestionPaneLabel(), hasText("Question:"));
        verifyThat(getQuestionPaneTextArea().getText(), nullValue());
        verifyThat(getSendButton().getText(), equalTo("Send"));
        verifyWebViewBody(getQuestionCorrectnessWebView(), "");
        verifyWebViewBody(getShortAnswerWebView(), "");
        verifyWebViewBody(getLongAnswerWebView(), "");
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(WHITE);
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(WHITE);
    }

    private void sendFirstQuestion() {
        clickOn(getThemeComboBox());
        overWrite(theme1);
        clickOn(getQuestionPaneTextArea());
        var question = "The question 1";
        overWrite(question);
        assertThat(getQuestionPaneTextArea().getText()).isEqualTo(question);
        verifyThat(getThemeComboBox(), hasSelectedItem(theme1));
        verifyThat(getThemeComboBox(), containsExactlyItems(theme1));

        gptApi
                .put("has grammatical mistakes", "Correctness answer 1")
                .put("a short response", "Short answer 1")
                .put("a detailed response", "Long answer 1");
        clickOn(getSendButton());
        sleep(1000);
        verifyWebViewBody(getQuestionCorrectnessWebView(), "<p>Correctness answer 1</p>\n");
        verifyWebViewBody(getShortAnswerWebView(), "<p>Short answer 1</p>\n");
        verifyWebViewBody(getLongAnswerWebView(), "<p>Long answer 1</p>\n");
        var allInteractions = storage.readAllInteractions();
        var currentInteraction = allInteractions.get(0);
        verifyThat(getInteractionHistoryComboBox(), containsExactlyItems(allInteractions.toArray()));
        verifyThat(getInteractionHistoryComboBox(), hasSelectedItem(currentInteraction));
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(GREEN);
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(GREEN);
    }

    private void sendSecondQuestion() {
        clickOn(getThemeComboBox());
        overWrite(theme2);
        clickOn(getQuestionPaneTextArea());
        var question = "The question 2";
        overWrite(question);
        assertThat(getQuestionPaneTextArea().getText()).isEqualTo(question);
        verifyThat(getThemeComboBox(), hasSelectedItem(theme2));
        verifyThat(getThemeComboBox(), containsExactlyItems(theme1, theme2));

        gptApi
                .put("has grammatical mistakes", "Correctness answer 2")
                .put("a short response", "Short answer 2")
                .put("a detailed response", "Long answer 2");
        clickOn(getSendButton());
        sleep(1000);
        verifyWebViewBody(getQuestionCorrectnessWebView(), "<p>Correctness answer 2</p>\n");
        verifyWebViewBody(getShortAnswerWebView(), "<p>Short answer 2</p>\n");
        verifyWebViewBody(getLongAnswerWebView(), "<p>Long answer 2</p>\n");
        var allInteractions = storage.readAllInteractions();
        var currentInteraction = allInteractions.get(1);
        verifyThat(getInteractionHistoryComboBox(), containsExactlyItems(allInteractions.toArray()));
        verifyThat(getInteractionHistoryComboBox(), hasSelectedItem(currentInteraction));
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(GREEN);
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(GREEN);
    }

}