package gptui.ui;

import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import java.util.List;

import static java.time.Duration.ofMillis;
import static javafx.scene.paint.Color.*;

class GptUiApplicationTest extends BaseGptUiTest {
    private static final String THEME_1 = "Theme 1";
    private static final String THEME_2 = "Theme 2";
    private static final String QUESTION_1 = "The question 1";
    private static final String EXP_GRAMMAR_HTML_BODY_1 = "<p>Grammar answer 1</p>\n";
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
        assertion()
                .historySize(0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(null)
                .historyItems(List.of())
                .themeSize(0)
                .themeSelectedItem(null)
                .themeItems()
                .questionText("")
                .modelEditedQuestion(null)
                .answerGrammarText("")
                .answerShortText("")
                .answerLongText("")
                .answerCircleColors(WHITE, WHITE, WHITE)
                .assertApp();
    }

    private void sendFirstQuestion() {
        clickOn(getThemeComboBox());
        overWrite(THEME_1);
        clickOn(getQuestionTextArea());
        overWrite(QUESTION_1);
        assertion()
                .historySize(0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(null)
                .historyItems(List.of())
                .themeSize(0)
                .themeSelectedItem(THEME_1)
                .themeItems()
                .questionText(QUESTION_1)
                .modelEditedQuestion(QUESTION_1)
                .answerGrammarText("")
                .answerShortText("")
                .answerLongText("")
                .answerCircleColors(WHITE, WHITE, WHITE)
                .assertApp();

        gptApi.clear()
                .put("has grammatical mistakes", "Grammar answer 1", ofMillis(1000))
                .put("a short response", "Short answer 1", ofMillis(1500))
                .put("a detailed response", "Long answer 1", ofMillis(2000));

        clickOn(getQuestionSendButton());
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(1)
                .themeSelectedItem(THEME_1)
                .themeItems(THEME_1)
                .questionText(QUESTION_1)
                .modelEditedQuestion(QUESTION_1)
                .answerGrammarText("")
                .answerShortText("")
                .answerLongText("")
                .answerCircleColors(BLUE, BLUE, BLUE)
                .assertApp();

        sleep(2000);
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(1)
                .themeSelectedItem(THEME_1)
                .themeItems(THEME_1)
                .questionText(QUESTION_1)
                .modelEditedQuestion(QUESTION_1)
                .answerGrammarText(EXP_GRAMMAR_HTML_BODY_1)
                .answerShortText(EXP_SHORT_HTML_BODY_1)
                .answerLongText(EXP_LONG_HTML_BODY_1)
                .answerCircleColors(GREEN, GREEN, GREEN)
                .assertApp();

        clickOn(getAnswerShortCopyButton());
        verifyHtmlClipboardContent(EXP_SHORT_HTML_BODY_1);

        clickOn(getAnswerLongCopyButton());
        verifyHtmlClipboardContent(EXP_LONG_HTML_BODY_1);
    }

    private void sendSecondQuestion() {
        clickOn(getThemeComboBox());
        overWrite(THEME_2);
        clickOn(getQuestionTextArea());
        var question2 = "The question 2";
        overWrite(question2);
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(1)
                .themeSelectedItem(THEME_2)
                .themeItems(THEME_1)
                .themeItems(THEME_1)
                .questionText(question2)
                .modelEditedQuestion(question2)
                .answerGrammarText(EXP_GRAMMAR_HTML_BODY_1)
                .answerShortText(EXP_SHORT_HTML_BODY_1)
                .answerLongText(EXP_LONG_HTML_BODY_1)
                .answerCircleColors(GREEN, GREEN, GREEN)
                .assertApp();

        gptApi.clear()
                .put("has grammatical mistakes", "Grammar answer 2", ofMillis(1000))
                .put("a short response", "Short answer 2", ofMillis(1500))
                .put("a detailed response", "Long answer 2", ofMillis(2000));
        clickOn(getQuestionSendButton());
        WaitForAsyncUtils.waitForFxEvents();
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(THEME_2)
                .themeItems(THEME_2, THEME_1)
                .questionText(question2)
                .modelEditedQuestion(question2)
                .answerGrammarText("")
                .answerShortText("")
                .answerLongText("")
                .answerCircleColors(BLUE, BLUE, BLUE)
                .assertApp();


        sleep(2000);
        var expShortHtmlBody2 = "<p>Short answer 2</p>\n";
        var expLongHtmlBody2 = "<p>Long answer 2</p>\n";
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(THEME_2)
                .themeItems(THEME_2, THEME_1)
                .questionText(question2)
                .modelEditedQuestion(question2)
                .answerGrammarText("<p>Grammar answer 2</p>\n")
                .answerShortText(expShortHtmlBody2)
                .answerLongText(expLongHtmlBody2)
                .answerCircleColors(GREEN, GREEN, GREEN)
                .assertApp();

        clickOn(getAnswerShortCopyButton());
        verifyHtmlClipboardContent(expShortHtmlBody2);

        clickOn(getAnswerLongCopyButton());
        verifyHtmlClipboardContent(expLongHtmlBody2);
    }

    private void choosePreviousInteraction() {
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(THEME_2)
                .themeItems(THEME_2, THEME_1)
                .questionText("The question 2")
                .modelEditedQuestion("The question 2")
                .answerGrammarText("<p>Grammar answer 2</p>\n")
                .answerShortText("<p>Short answer 2</p>\n")
                .answerLongText("<p>Long answer 2</p>\n")
                .answerCircleColors(GREEN, GREEN, GREEN)
                .assertApp();

        clickOn(getHistoryComboBox()).clickOn(String.format("[Q] %s: %s", THEME_1, QUESTION_1));
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().get(1))
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(THEME_1)
                .themeItems(THEME_2, THEME_1)
                .questionText(QUESTION_1)
                .modelEditedQuestion(QUESTION_1)
                .answerGrammarText(EXP_GRAMMAR_HTML_BODY_1)
                .answerShortText(EXP_SHORT_HTML_BODY_1)
                .answerLongText(EXP_LONG_HTML_BODY_1)
                .answerCircleColors(GREEN, GREEN, GREEN)
                .assertApp();

        clickOn(getAnswerShortCopyButton());
        verifyHtmlClipboardContent(EXP_SHORT_HTML_BODY_1);

        clickOn(getAnswerLongCopyButton());
        verifyHtmlClipboardContent(EXP_LONG_HTML_BODY_1);
    }

}