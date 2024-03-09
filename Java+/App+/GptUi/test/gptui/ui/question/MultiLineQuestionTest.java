package gptui.ui.question;

import gptui.ui.BaseGptUiTest;
import gptui.ui.TestingData.I0;
import gptui.ui.TestingData.I1;
import org.junit.jupiter.api.Test;

import static java.time.Duration.ZERO;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.WHITE;

class MultiLineQuestionTest extends BaseGptUiTest {
    @Override
    public void init() {
        storage.saveTheme(I1.THEME);
    }

    @Test
    void currentInteractionIsInMiddle() {
        initialState();
        sendQuestion();
    }

    private void initialState() {
        assertion()
                .historySize(0, 0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(I0.HISTORY_SELECTED_ITEM)
                .historyItems()
                .themeSize(1)
                .themeSelectedItem(I0.THEME_SELECTED_ITEM)
                .themeItems(I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I0.QUESTION)
                .modelEditedQuestion(null)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I0.GRAMMAR_HTML)
                .shortA().text(I0.SHORT_HTML)
                .longA().text(I0.LONG_HTML)
                .gcpA().text(I0.GCP_HTML)
                .answerCircleColors(WHITE, WHITE, WHITE, WHITE)
                .answerTextTemperaturesAllEmpty()
                .answerSpinnerTemperaturesDefault()
                .assertApp();
    }

    private void sendQuestion() {
        clickOn(theme().comboBoxNarrow()).clickOn(I1.THEME.title() + " (0)");
        clickOn(question().textArea());
        var questionLine1 = "Question line 1";
        var questionLine2 = "Question line 2";
        var questionLine3 = "Question line 3";
        overWrite(questionLine1).type(ENTER).write(questionLine2).type(ENTER).write(questionLine3);

        gptApi.clear()
                .putGrammarResponse(I1.GRAMMAR_HTML, ZERO)
                .putShortResponse(I1.SHORT_HTML, ZERO)
                .putLongResponse(I1.LONG_HTML, ZERO)
                .putGcpResponse(I1.GCP_HTML, ZERO);
        clickOn(question().questionButton());

        gptApi.waitUntilSent(4);
        var questionText = questionLine1 + "\n" + questionLine2 + "\n" + questionLine3;
        assertion()
                .historySize(1, 1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(1)
                .themeSelectedItem(I1.THEME)
                .themeItems(I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(questionText)
                .modelEditedQuestion(questionText)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I1.EXP_GRAMMAR_HTML_BODY)
                .shortA().text(I1.EXP_SHORT_HTML_BODY)
                .longA().text(I1.EXP_LONG_HTML_BODY)
                .gcpA().text(I1.EXP_GCP_HTML_BODY)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();
    }
}