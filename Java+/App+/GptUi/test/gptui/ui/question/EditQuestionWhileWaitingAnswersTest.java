package gptui.ui.question;

import gptui.ui.BaseGptUiTest;
import gptui.ui.TestingData.I0;
import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import org.junit.jupiter.api.Test;

import static gptui.viewmodel.question.QuestionStyle.QUESTION_STYLE_EDITED;
import static gptui.viewmodel.question.QuestionStyle.QUESTION_STYLE_EMPTY;
import static java.time.Duration.ofMillis;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.WHITE;

class EditQuestionWhileWaitingAnswersTest extends BaseGptUiTest {
    @Override
    public void init() {
        storage.saveTheme(I1.THEME);
        storage.saveTheme(I2.THEME);
    }

    @Test
    void shouldSendQuestion() {
        initialState();
        sendFirstQuestion();
        editQuestion();
    }

    private void initialState() {
        assertion()
                .focus(history().comboBox())
                .historySize(0, 0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(I0.HISTORY_SELECTED_ITEM)
                .historyItems(I0.HISTORY_ITEMS)
                .themeSize(2)
                .themeSelectedItem(I0.THEME_SELECTED_ITEM)
                .themeItems(I1.THEME, I2.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I0.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
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

    private void sendFirstQuestion() {
        clickOn(theme().comboBoxNarrow()).clickOn(I1.THEME.title() + " (0)");
        clickOn(question().textArea());
        overWrite(I1.QUESTION);
        assertion()
                .focus(question().textArea())
                .historySize(0, 0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(I0.HISTORY_SELECTED_ITEM)
                .historyItems(I0.HISTORY_ITEMS)
                .themeSize(2)
                .themeSelectedItem(I1.THEME)
                .themeItems(I1.THEME, I2.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
                .modelEditedQuestion(I1.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I0.GRAMMAR_HTML)
                .shortA().text(I0.SHORT_HTML)
                .longA().text(I0.LONG_HTML)
                .gcpA().text(I0.GCP_HTML)
                .answerCircleColors(WHITE, WHITE, WHITE, WHITE)
                .answerTextTemperaturesAllEmpty()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        gptApi.clear()
                .putGrammarResponse(I1.GRAMMAR_HTML, ofMillis(5000))
                .putShortResponse(I1.SHORT_HTML, ofMillis(5500))
                .putLongResponse(I1.LONG_HTML, ofMillis(6000))
                .putGcpResponse(I1.GCP_HTML, ofMillis(6500));

        clickOn(question().questionButton());
        assertion()
                .focus(question().questionButton())
                .historySize(1, 1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(I1.THEME)
                .themeItems(I1.THEME, I2.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
                .modelEditedQuestion(I1.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I0.GRAMMAR_HTML)
                .shortA().text(I0.SHORT_HTML)
                .longA().text(I0.LONG_HTML)
                .gcpA().text(I0.GCP_HTML)
                .answerCircleColors(BLUE, BLUE, BLUE, BLUE)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();
    }

    private void editQuestion() {
        clickOn(theme().comboBoxNarrow()).clickOn(I2.THEME.title() + " (0)");
        clickOn(question().textArea());
        overWrite(I2.QUESTION);
        assertion()
                .focus(question().textArea())
                .historySize(1, 1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(I2.THEME)
                .themeItems(I1.THEME, I2.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I2.QUESTION)
                .questionStyle(QUESTION_STYLE_EDITED)
                .modelEditedQuestion(I2.QUESTION)
                .modelIsEnteringNewQuestion(true)
                .grammarA().text(I0.GRAMMAR_HTML)
                .shortA().text(I0.SHORT_HTML)
                .longA().text(I0.LONG_HTML)
                .gcpA().text(I0.GCP_HTML)
                .answerCircleColors(BLUE, BLUE, BLUE, BLUE)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        gptApi.waitUntilSent(4);
        assertion()
                .focus(question().textArea())
                .historySize(1, 1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(I2.THEME)
                .themeItems(I1.THEME, I2.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I2.QUESTION)
                .questionStyle(QUESTION_STYLE_EDITED)
                .modelEditedQuestion(I2.QUESTION)
                .modelIsEnteringNewQuestion(true)
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