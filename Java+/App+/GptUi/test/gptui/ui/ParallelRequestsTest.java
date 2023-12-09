package gptui.ui;

import org.junit.jupiter.api.Test;

import java.util.List;

import static gptui.ui.TestingData.EXP_GCP_HTML_BODY_2;
import static gptui.ui.TestingData.EXP_GRAMMAR_HTML_BODY_2;
import static gptui.ui.TestingData.EXP_LONG_HTML_BODY_2;
import static gptui.ui.TestingData.EXP_SHORT_HTML_BODY_2;
import static gptui.ui.TestingData.INTERACTION_1_GCP_HTML;
import static gptui.ui.TestingData.INTERACTION_1_GRAMMAR_HTML;
import static gptui.ui.TestingData.INTERACTION_1_LONG_HTML;
import static gptui.ui.TestingData.INTERACTION_1_QUESTION;
import static gptui.ui.TestingData.INTERACTION_1_SHORT_HTML;
import static gptui.ui.TestingData.INTERACTION_1_THEME;
import static gptui.ui.TestingData.INTERACTION_2_GCP_HTML;
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
                .themeFilterHistorySelected(false)
                .questionText("")
                .modelEditedQuestion(null)
                .grammarA().text("")
                .shortA().text("")
                .longA().text("")
                .gcpA().text("")
                .answerCircleColors(WHITE, WHITE, WHITE, WHITE)
                .answerTextTemperaturesAllEmpty()
                .answerSpinnerTemperaturesDefault()
                .assertApp();
    }

    private void sendFirstQuestion() {
        clickOn(theme().comboBox());
        overWrite(INTERACTION_1_THEME);
        clickOn(question().textArea());
        overWrite(INTERACTION_1_QUESTION);
        assertion()
                .historySize(0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(null)
                .historyItems(List.of())
                .themeSize(0)
                .themeSelectedItem(INTERACTION_1_THEME)
                .themeItems()
                .themeFilterHistorySelected(false)
                .questionText(INTERACTION_1_QUESTION)
                .modelEditedQuestion(INTERACTION_1_QUESTION)
                .grammarA().text("")
                .shortA().text("")
                .longA().text("")
                .gcpA().text("")
                .answerCircleColors(WHITE, WHITE, WHITE, WHITE)
                .answerTextTemperaturesAllEmpty()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        gptApi.clear()
                .putGrammarResponse(INTERACTION_1_GRAMMAR_HTML, ofMillis(6000))
                .putShortResponse(INTERACTION_1_SHORT_HTML, ofMillis(6500))
                .putLongResponse(INTERACTION_1_LONG_HTML, ofMillis(7000))
                .putGcpResponse(INTERACTION_1_GCP_HTML, ofMillis(7500));

        clickOn(question().questionButton());
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(1)
                .themeSelectedItem(INTERACTION_1_THEME)
                .themeItems(INTERACTION_1_THEME)
                .themeFilterHistorySelected(false)
                .questionText(INTERACTION_1_QUESTION)
                .modelEditedQuestion(INTERACTION_1_QUESTION)
                .grammarA().text("")
                .shortA().text("")
                .longA().text("")
                .gcpA().text("")
                .answerCircleColors(BLUE, BLUE, BLUE, BLUE)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();
    }

    private void sendSecondQuestion() {
        clickOn(theme().comboBox());
        overWrite(INTERACTION_2_THEME);
        clickOn(question().textArea());
        overWrite(INTERACTION_2_QUESTION);
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(1)
                .themeSelectedItem(INTERACTION_2_THEME)
                .themeItems(INTERACTION_1_THEME)
                .themeFilterHistorySelected(false)
                .questionText(INTERACTION_2_QUESTION)
                .modelEditedQuestion(INTERACTION_2_QUESTION)
                .grammarA().text("")
                .shortA().text("")
                .longA().text("")
                .gcpA().text("")
                .answerCircleColors(BLUE, BLUE, BLUE, BLUE)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        gptApi.clear()
                .putGrammarResponse(INTERACTION_2_GRAMMAR_HTML, ofMillis(1000))
                .putShortResponse(INTERACTION_2_SHORT_HTML, ofMillis(1500))
                .putLongResponse(INTERACTION_2_LONG_HTML, ofMillis(2000))
                .putGcpResponse(INTERACTION_2_GCP_HTML, ofMillis(2500));
        clickOn(question().questionButton());
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(INTERACTION_2_THEME)
                .themeItems(INTERACTION_2_THEME, INTERACTION_1_THEME)
                .themeFilterHistorySelected(false)
                .questionText(INTERACTION_2_QUESTION)
                .modelEditedQuestion(INTERACTION_2_QUESTION)
                .grammarA().text("")
                .shortA().text("")
                .longA().text("")
                .gcpA().text("")
                .answerCircleColors(BLUE, BLUE, BLUE, BLUE)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();


        gptApi.waitUntilSent(8);
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(INTERACTION_2_THEME)
                .themeItems(INTERACTION_1_THEME, INTERACTION_2_THEME)
                .themeFilterHistorySelected(false)
                .questionText(INTERACTION_2_QUESTION)
                .modelEditedQuestion(INTERACTION_2_QUESTION)
                .grammarA().text(EXP_GRAMMAR_HTML_BODY_2)
                .shortA().text(EXP_SHORT_HTML_BODY_2)
                .longA().text(EXP_LONG_HTML_BODY_2)
                .gcpA().text(EXP_GCP_HTML_BODY_2)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();
    }

    private void firstRequestFinished() {
        gptApi.waitUntilSent(8);
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(INTERACTION_2_THEME)
                .themeItems(INTERACTION_1_THEME, INTERACTION_2_THEME)
                .themeFilterHistorySelected(false)
                .questionText(INTERACTION_2_QUESTION)
                .modelEditedQuestion(INTERACTION_2_QUESTION)
                .grammarA().text(EXP_GRAMMAR_HTML_BODY_2)
                .shortA().text(EXP_SHORT_HTML_BODY_2)
                .longA().text(EXP_LONG_HTML_BODY_2)
                .gcpA().text(EXP_GCP_HTML_BODY_2)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();
    }
}