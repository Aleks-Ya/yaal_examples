package gptui.ui;

import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import java.util.List;

import static java.time.Duration.ofMillis;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.WHITE;

class ParallelRequestsTest extends BaseGptUiTest {
    private static final String THEME_1 = "Theme 1";
    private static final String THEME_2 = "Theme 2";
    private static final String QUESTION_1 = "The question 1";
    private static final String QUESTION_2 = "The question 2";
    private static final String EXP_GRAMMAR_HTML_BODY_2 = "<p>Grammar answer 2</p>\n";
    private static final String EXP_SHORT_HTML_BODY_2 = "<p>Short answer 2</p>\n";
    private static final String EXP_LONG_HTML_BODY_2 = "<p>Long answer 2</p>\n";

    @Test
    void shouldSendQuestion() {
        initialState();
        sendFirstQuestion();
        sendSecondQuestion();
        firstRequestFinished();
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
                .put("has grammatical mistakes", "Grammar answer 1", ofMillis(6000))
                .put("a short response", "Short answer 1", ofMillis(6500))
                .put("a detailed response", "Long answer 1", ofMillis(7000));

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
    }

    private void sendSecondQuestion() {
        clickOn(getThemeComboBox());
        overWrite(THEME_2);
        clickOn(getQuestionTextArea());
        overWrite(QUESTION_2);
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(1)
                .themeSelectedItem(THEME_2)
                .themeItems(THEME_1)
                .questionText(QUESTION_2)
                .modelEditedQuestion(QUESTION_2)
                .answerGrammarText("")
                .answerShortText("")
                .answerLongText("")
                .answerCircleColors(BLUE, BLUE, BLUE)
                .assertApp();

        gptApi.clear()
                .put("has grammatical mistakes", "Grammar answer 2", ofMillis(1000))
                .put("a short response", "Short answer 2", ofMillis(1500))
                .put("a detailed response", "Long answer 2", ofMillis(2000));
        clickOn(getQuestionSendButton());
        sleep(500);
        WaitForAsyncUtils.waitForFxEvents();
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(THEME_2)
                .themeItems(THEME_2, THEME_1)
                .questionText(QUESTION_2)
                .modelEditedQuestion(QUESTION_2)
                .answerGrammarText("")
                .answerShortText("")
                .answerLongText("")
                .answerCircleColors(BLUE, BLUE, BLUE)
                .assertApp();


        sleep(2000);
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(THEME_2)
                .themeItems(THEME_2, THEME_1)
                .questionText(QUESTION_2)
                .modelEditedQuestion(QUESTION_2)
                .answerGrammarText(EXP_GRAMMAR_HTML_BODY_2)
                .answerShortText(EXP_SHORT_HTML_BODY_2)
                .answerLongText(EXP_LONG_HTML_BODY_2)
                .answerCircleColors(GREEN, GREEN, GREEN)
                .assertApp();
    }

    private void firstRequestFinished() {
        sleep(5000);
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(THEME_2)
                .themeItems(THEME_2, THEME_1)
                .questionText(QUESTION_2)
                .modelEditedQuestion(QUESTION_2)
                .answerGrammarText(EXP_GRAMMAR_HTML_BODY_2)
                .answerShortText(EXP_SHORT_HTML_BODY_2)
                .answerLongText(EXP_LONG_HTML_BODY_2)
                .answerCircleColors(GREEN, GREEN, GREEN)
                .assertApp();
    }

}