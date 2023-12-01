package gptui.ui;

import org.junit.jupiter.api.Test;

import java.util.List;

import static gptui.ui.TestingData.EXP_GCP_HTML_BODY_1;
import static gptui.ui.TestingData.EXP_GRAMMAR_HTML_BODY_1;
import static gptui.ui.TestingData.EXP_LONG_HTML_BODY_1;
import static gptui.ui.TestingData.EXP_SHORT_HTML_BODY_1;
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
                .putGrammarResponse(INTERACTION_1_GRAMMAR_HTML, ofMillis(1000))
                .putShortResponse(INTERACTION_1_SHORT_HTML, ofMillis(1500))
                .putLongResponse(INTERACTION_1_LONG_HTML, ofMillis(2000))
                .putGcpResponse(INTERACTION_1_GCP_HTML, ofMillis(2500));

        clickOn(question().questionButton());
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
                .themeSelectedItem(INTERACTION_1_THEME)
                .themeItems(INTERACTION_1_THEME)
                .questionText(INTERACTION_1_QUESTION)
                .modelEditedQuestion(INTERACTION_1_QUESTION)
                .grammarA().text(EXP_GRAMMAR_HTML_BODY_1)
                .shortA().text(EXP_SHORT_HTML_BODY_1)
                .longA().text(EXP_LONG_HTML_BODY_1)
                .gcpA().text(EXP_GCP_HTML_BODY_1)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        clickOn(shortAnswer().copyButton());
        verifyHtmlClipboardContent(EXP_SHORT_HTML_BODY_1);

        clickOn(longAnswer().copyButton());
        verifyHtmlClipboardContent(EXP_LONG_HTML_BODY_1);

        clickOn(gcpAnswer().copyButton());
        verifyHtmlClipboardContent(EXP_GCP_HTML_BODY_1);
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
                .questionText(INTERACTION_2_QUESTION)
                .modelEditedQuestion(INTERACTION_2_QUESTION)
                .grammarA().text(EXP_GRAMMAR_HTML_BODY_1)
                .shortA().text(EXP_SHORT_HTML_BODY_1)
                .longA().text(EXP_LONG_HTML_BODY_1)
                .gcpA().text(EXP_GCP_HTML_BODY_1)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
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


        gptApi.waitUntilSent(4);
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
                .grammarA().text(TestingData.EXP_GRAMMAR_HTML_BODY_2)
                .shortA().text(TestingData.EXP_SHORT_HTML_BODY_2)
                .longA().text(TestingData.EXP_LONG_HTML_BODY_2)
                .gcpA().text(TestingData.EXP_GCP_HTML_BODY_2)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        clickOn(shortAnswer().copyButton());
        verifyHtmlClipboardContent(TestingData.EXP_SHORT_HTML_BODY_2);

        clickOn(longAnswer().copyButton());
        verifyHtmlClipboardContent(TestingData.EXP_LONG_HTML_BODY_2);

        clickOn(gcpAnswer().copyButton());
        verifyHtmlClipboardContent(TestingData.EXP_GCP_HTML_BODY_2);
    }

    private void choosePreviousInteraction() {
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
                .grammarA().text(TestingData.EXP_GRAMMAR_HTML_BODY_2)
                .shortA().text(TestingData.EXP_SHORT_HTML_BODY_2)
                .longA().text(TestingData.EXP_LONG_HTML_BODY_2)
                .gcpA().text(TestingData.EXP_GCP_HTML_BODY_2)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        clickOn(history().comboBox()).clickOn(String.format("[Q] %s: %s", INTERACTION_1_THEME, INTERACTION_1_QUESTION));
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().get(1))
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(INTERACTION_1_THEME)
                .themeItems(INTERACTION_2_THEME, INTERACTION_1_THEME)
                .questionText(INTERACTION_1_QUESTION)
                .modelEditedQuestion(INTERACTION_1_QUESTION)
                .grammarA().text(EXP_GRAMMAR_HTML_BODY_1)
                .shortA().text(EXP_SHORT_HTML_BODY_1)
                .longA().text(EXP_LONG_HTML_BODY_1)
                .gcpA().text(EXP_GCP_HTML_BODY_1)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        clickOn(shortAnswer().copyButton());
        verifyHtmlClipboardContent(EXP_SHORT_HTML_BODY_1);

        clickOn(longAnswer().copyButton());
        verifyHtmlClipboardContent(EXP_LONG_HTML_BODY_1);

        clickOn(gcpAnswer().copyButton());
        verifyHtmlClipboardContent(EXP_GCP_HTML_BODY_1);
    }
}