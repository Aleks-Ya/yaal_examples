package gptui.ui.question;

import gptui.ui.BaseGptUiTest;
import gptui.ui.TestingData;
import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.time.Duration.ofMillis;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.WHITE;

class SequentialRequestsTest extends BaseGptUiTest {
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
        overWrite(I1.THEME);
        clickOn(question().textArea());
        overWrite(I1.QUESTION);
        assertion()
                .historySize(0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(null)
                .historyItems(List.of())
                .themeSize(0)
                .themeSelectedItem(I1.THEME)
                .themeItems()
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .modelEditedQuestion(I1.QUESTION)
                .grammarA().text("")
                .shortA().text("")
                .longA().text("")
                .gcpA().text("")
                .answerCircleColors(WHITE, WHITE, WHITE, WHITE)
                .answerTextTemperaturesAllEmpty()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        gptApi.clear()
                .putGrammarResponse(I1.GRAMMAR_HTML, ofMillis(1000))
                .putShortResponse(I1.SHORT_HTML, ofMillis(1500))
                .putLongResponse(I1.LONG_HTML, ofMillis(2000))
                .putGcpResponse(I1.GCP_HTML, ofMillis(2500));

        clickOn(question().questionButton());
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(1)
                .themeSelectedItem(I1.THEME)
                .themeItems(I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .modelEditedQuestion(I1.QUESTION)
                .grammarA().text("")
                .shortA().text("")
                .longA().text("")
                .gcpA().text("")
                .answerCircleColors(BLUE, BLUE, BLUE, BLUE)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        gptApi.waitUntilSent(4);
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(1)
                .themeSelectedItem(I1.THEME)
                .themeItems(I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .modelEditedQuestion(I1.QUESTION)
                .grammarA().text(I1.EXP_GRAMMAR_HTML_BODY)
                .shortA().text(I1.EXP_SHORT_HTML_BODY)
                .longA().text(I1.EXP_LONG_HTML_BODY)
                .gcpA().text(I1.EXP_GCP_HTML_BODY)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        clickOn(shortAnswer().copyButton());
        verifyHtmlClipboardContent(I1.EXP_SHORT_HTML_BODY);

        clickOn(longAnswer().copyButton());
        verifyHtmlClipboardContent(I1.EXP_LONG_HTML_BODY);

        clickOn(gcpAnswer().copyButton());
        verifyHtmlClipboardContent(I1.EXP_GCP_HTML_BODY);
    }

    private void sendSecondQuestion() {
        clickOn(theme().comboBox());
        overWrite(I2.THEME);
        clickOn(question().textArea());
        overWrite(I2.QUESTION);
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(1)
                .themeSelectedItem(I2.THEME)
                .themeItems(I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I2.QUESTION)
                .modelEditedQuestion(I2.QUESTION)
                .grammarA().text(I1.EXP_GRAMMAR_HTML_BODY)
                .shortA().text(I1.EXP_SHORT_HTML_BODY)
                .longA().text(I1.EXP_LONG_HTML_BODY)
                .gcpA().text(I1.EXP_GCP_HTML_BODY)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        gptApi.clear()
                .putGrammarResponse(I2.GRAMMAR_HTML, ofMillis(1000))
                .putShortResponse(I2.SHORT_HTML, ofMillis(1500))
                .putLongResponse(I2.LONG_HTML, ofMillis(2000))
                .putGcpResponse(I2.GCP_HTML, ofMillis(2500));
        clickOn(question().questionButton());
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(I2.THEME)
                .themeItems(I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I2.QUESTION)
                .modelEditedQuestion(I2.QUESTION)
                .grammarA().text("")
                .shortA().text("")
                .longA().text("")
                .gcpA().text("")
                .answerCircleColors(BLUE, BLUE, BLUE, BLUE)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();


        gptApi.waitUntilSent(4);
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(I2.THEME)
                .themeItems(I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I2.QUESTION)
                .modelEditedQuestion(I2.QUESTION)
                .grammarA().text(TestingData.I2.EXP_HTML_BODY)
                .shortA().text(TestingData.I2.EXP_SHORT_HTML_BODY)
                .longA().text(TestingData.I2.EXP_LONG_HTML_BODY)
                .gcpA().text(TestingData.I2.EXP_GCP_HTML_BODY)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        clickOn(shortAnswer().copyButton());
        verifyHtmlClipboardContent(TestingData.I2.EXP_SHORT_HTML_BODY);

        clickOn(longAnswer().copyButton());
        verifyHtmlClipboardContent(TestingData.I2.EXP_LONG_HTML_BODY);

        clickOn(gcpAnswer().copyButton());
        verifyHtmlClipboardContent(TestingData.I2.EXP_GCP_HTML_BODY);
    }

    private void choosePreviousInteraction() {
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(I2.THEME)
                .themeItems(I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I2.QUESTION)
                .modelEditedQuestion(I2.QUESTION)
                .grammarA().text(TestingData.I2.EXP_HTML_BODY)
                .shortA().text(TestingData.I2.EXP_SHORT_HTML_BODY)
                .longA().text(TestingData.I2.EXP_LONG_HTML_BODY)
                .gcpA().text(TestingData.I2.EXP_GCP_HTML_BODY)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        clickOn(history().comboBox()).clickOn(String.format("[Q] %s: %s", I1.THEME, I1.QUESTION));
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().get(1))
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(I1.THEME)
                .themeItems(I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .modelEditedQuestion(I1.QUESTION)
                .grammarA().text(I1.EXP_GRAMMAR_HTML_BODY)
                .shortA().text(I1.EXP_SHORT_HTML_BODY)
                .longA().text(I1.EXP_LONG_HTML_BODY)
                .gcpA().text(I1.EXP_GCP_HTML_BODY)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        clickOn(shortAnswer().copyButton());
        verifyHtmlClipboardContent(I1.EXP_SHORT_HTML_BODY);

        clickOn(longAnswer().copyButton());
        verifyHtmlClipboardContent(I1.EXP_LONG_HTML_BODY);

        clickOn(gcpAnswer().copyButton());
        verifyHtmlClipboardContent(I1.EXP_GCP_HTML_BODY);
    }
}