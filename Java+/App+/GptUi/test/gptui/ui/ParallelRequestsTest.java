package gptui.ui;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static gptui.gpt.Temperature.GCP_TEMPERATURE_DEFAULT;
import static gptui.gpt.Temperature.GRAMMAR_TEMPERATURE_DEFAULT;
import static gptui.gpt.Temperature.LONG_TEMPERATURE_DEFAULT;
import static gptui.gpt.Temperature.SHORT_TEMPERATURE_DEFAULT;
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
                .questionText("")
                .modelEditedQuestion(null)
                .answerGrammarText("")
                .answerShortText("")
                .answerLongText("")
                .answerGcpText("")
                .answerCircleColors(WHITE, WHITE, WHITE, WHITE)
                .answerTemperatures((BigDecimal) null, null, null, null)
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
                .answerGcpText("")
                .answerCircleColors(WHITE, WHITE, WHITE, WHITE)
                .answerTemperatures((BigDecimal) null, null, null, null)
                .assertApp();

        gptApi.clear()
                .putGrammarResponse(INTERACTION_1_GRAMMAR_HTML, ofMillis(6000))
                .putShortResponse(INTERACTION_1_SHORT_HTML, ofMillis(6500))
                .putLongResponse(INTERACTION_1_LONG_HTML, ofMillis(7000))
                .putGcpResponse(INTERACTION_1_GCP_HTML, ofMillis(7500));

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
                .answerGcpText("")
                .answerCircleColors(BLUE, BLUE, BLUE, BLUE)
                .answerTemperatures(GRAMMAR_TEMPERATURE_DEFAULT, SHORT_TEMPERATURE_DEFAULT, LONG_TEMPERATURE_DEFAULT, GCP_TEMPERATURE_DEFAULT)
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
                .answerGcpText("")
                .answerCircleColors(BLUE, BLUE, BLUE, BLUE)
                .answerTemperatures(GRAMMAR_TEMPERATURE_DEFAULT, SHORT_TEMPERATURE_DEFAULT, LONG_TEMPERATURE_DEFAULT, GCP_TEMPERATURE_DEFAULT)
                .assertApp();

        gptApi.clear()
                .putGrammarResponse(INTERACTION_2_GRAMMAR_HTML, ofMillis(1000))
                .putShortResponse(INTERACTION_2_SHORT_HTML, ofMillis(1500))
                .putLongResponse(INTERACTION_2_LONG_HTML, ofMillis(2000))
                .putGcpResponse(INTERACTION_2_GCP_HTML, ofMillis(2500));
        clickOn(getQuestionSendButton());
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
                .answerGcpText("")
                .answerCircleColors(BLUE, BLUE, BLUE, BLUE)
                .answerTemperatures(GRAMMAR_TEMPERATURE_DEFAULT, SHORT_TEMPERATURE_DEFAULT, LONG_TEMPERATURE_DEFAULT, GCP_TEMPERATURE_DEFAULT)
                .assertApp();


        sleep(4000);
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(2)
                .themeSelectedItem(INTERACTION_2_THEME)
                .themeItems(INTERACTION_1_THEME, INTERACTION_2_THEME)
                .questionText(INTERACTION_2_QUESTION)
                .modelEditedQuestion(INTERACTION_2_QUESTION)
                .answerGrammarText(EXP_GRAMMAR_HTML_BODY_2)
                .answerShortText(EXP_SHORT_HTML_BODY_2)
                .answerLongText(EXP_LONG_HTML_BODY_2)
                .answerGcpText(EXP_GCP_HTML_BODY_2)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTemperatures(GRAMMAR_TEMPERATURE_DEFAULT, SHORT_TEMPERATURE_DEFAULT, LONG_TEMPERATURE_DEFAULT, GCP_TEMPERATURE_DEFAULT)
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
                .themeItems(INTERACTION_1_THEME, INTERACTION_2_THEME)
                .questionText(INTERACTION_2_QUESTION)
                .modelEditedQuestion(INTERACTION_2_QUESTION)
                .answerGrammarText(EXP_GRAMMAR_HTML_BODY_2)
                .answerShortText(EXP_SHORT_HTML_BODY_2)
                .answerLongText(EXP_LONG_HTML_BODY_2)
                .answerGcpText(EXP_GCP_HTML_BODY_2)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTemperatures(GRAMMAR_TEMPERATURE_DEFAULT, SHORT_TEMPERATURE_DEFAULT, LONG_TEMPERATURE_DEFAULT, GCP_TEMPERATURE_DEFAULT)
                .assertApp();
    }
}