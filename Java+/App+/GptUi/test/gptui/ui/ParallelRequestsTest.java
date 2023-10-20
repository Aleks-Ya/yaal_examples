package gptui.ui;

import org.junit.jupiter.api.Test;

import java.util.List;

import static gptui.ui.TestingData.INTERACTION_1_GRAMMAR_HTML;
import static gptui.ui.TestingData.INTERACTION_1_LONG_HTML;
import static gptui.ui.TestingData.INTERACTION_1_QUESTION;
import static gptui.ui.TestingData.INTERACTION_1_SHORT_HTML;
import static gptui.ui.TestingData.INTERACTION_1_THEME;
import static gptui.ui.TestingData.INTERACTION_2_GRAMMAR_HTML;
import static gptui.ui.TestingData.INTERACTION_2_LONG_HTML;
import static gptui.ui.TestingData.INTERACTION_2_QUESTION;
import static gptui.ui.TestingData.INTERACTION_2_SHORT_HTML;
import static gptui.ui.TestingData.INTERACTION_2_THEME;
import static java.time.Duration.ofMillis;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.WHITE;

class ParallelRequestsTest extends BaseGptUiTest {
    private static final String EXP_GRAMMAR_HTML_BODY_2 = wrapExpectedWebViewContent(INTERACTION_2_GRAMMAR_HTML);
    private static final String EXP_SHORT_HTML_BODY_2 = wrapExpectedWebViewContent(INTERACTION_2_SHORT_HTML);
    private static final String EXP_LONG_HTML_BODY_2 = wrapExpectedWebViewContent(INTERACTION_2_LONG_HTML);

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
        overWrite(INTERACTION_1_THEME);
        clickOn(getQuestionTextArea());
        overWrite(INTERACTION_1_QUESTION);
        assertion()
                .historySize(0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(null)
                .historyItems(List.of())
                .themeSize(0)
                .themeSelectedItem(INTERACTION_1_THEME)
                .themeItems()
                .questionText(INTERACTION_1_QUESTION)
                .modelEditedQuestion(INTERACTION_1_QUESTION)
                .answerGrammarText("")
                .answerShortText("")
                .answerLongText("")
                .answerCircleColors(WHITE, WHITE, WHITE)
                .assertApp();

        gptApi.clear()
                .putGrammarResponse(INTERACTION_1_GRAMMAR_HTML, ofMillis(6000))
                .putShortResponse(INTERACTION_1_SHORT_HTML, ofMillis(6500))
                .putLongResponse(INTERACTION_1_LONG_HTML, ofMillis(7000));

        clickOn(getQuestionSendButton());
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(1)
                .themeSelectedItem(INTERACTION_1_THEME)
                .themeItems(INTERACTION_1_THEME)
                .questionText(INTERACTION_1_QUESTION)
                .modelEditedQuestion(INTERACTION_1_QUESTION)
                .answerGrammarText("")
                .answerShortText("")
                .answerLongText("")
                .answerCircleColors(BLUE, BLUE, BLUE)
                .assertApp();
    }

    private void sendSecondQuestion() {
        clickOn(getThemeComboBox());
        overWrite(INTERACTION_2_THEME);
        clickOn(getQuestionTextArea());
        overWrite(INTERACTION_2_QUESTION);
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(1)
                .themeSelectedItem(INTERACTION_2_THEME)
                .themeItems(INTERACTION_1_THEME)
                .questionText(INTERACTION_2_QUESTION)
                .modelEditedQuestion(INTERACTION_2_QUESTION)
                .answerGrammarText("")
                .answerShortText("")
                .answerLongText("")
                .answerCircleColors(BLUE, BLUE, BLUE)
                .assertApp();

        gptApi.clear()
                .putGrammarResponse(INTERACTION_2_GRAMMAR_HTML, ofMillis(1000))
                .putShortResponse(INTERACTION_2_SHORT_HTML, ofMillis(1500))
                .putLongResponse(INTERACTION_2_LONG_HTML, ofMillis(2000));
        clickOn(getQuestionSendButton());
        sleep(500);
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(INTERACTION_2_THEME)
                .themeItems(INTERACTION_2_THEME, INTERACTION_1_THEME)
                .questionText(INTERACTION_2_QUESTION)
                .modelEditedQuestion(INTERACTION_2_QUESTION)
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
                .themeSelectedItem(INTERACTION_2_THEME)
                .themeItems(INTERACTION_2_THEME, INTERACTION_1_THEME)
                .questionText(INTERACTION_2_QUESTION)
                .modelEditedQuestion(INTERACTION_2_QUESTION)
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
                .themeSelectedItem(INTERACTION_2_THEME)
                .themeItems(INTERACTION_2_THEME, INTERACTION_1_THEME)
                .questionText(INTERACTION_2_QUESTION)
                .modelEditedQuestion(INTERACTION_2_QUESTION)
                .answerGrammarText(EXP_GRAMMAR_HTML_BODY_2)
                .answerShortText(EXP_SHORT_HTML_BODY_2)
                .answerLongText(EXP_LONG_HTML_BODY_2)
                .answerCircleColors(GREEN, GREEN, GREEN)
                .assertApp();
    }

}