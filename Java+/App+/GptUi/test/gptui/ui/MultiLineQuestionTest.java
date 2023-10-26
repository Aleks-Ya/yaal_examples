package gptui.ui;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static gptui.gpt.Temperature.GCP_TEMPERATURE_DEFAULT;
import static gptui.gpt.Temperature.GRAMMAR_TEMPERATURE_DEFAULT;
import static gptui.gpt.Temperature.LONG_TEMPERATURE_DEFAULT;
import static gptui.gpt.Temperature.SHORT_TEMPERATURE_DEFAULT;
import static gptui.ui.TestingData.INTERACTION_1_GCP_HTML;
import static gptui.ui.TestingData.INTERACTION_1_GRAMMAR_HTML;
import static gptui.ui.TestingData.INTERACTION_1_LONG_HTML;
import static gptui.ui.TestingData.INTERACTION_1_SHORT_HTML;
import static gptui.ui.TestingData.INTERACTION_1_THEME;
import static java.time.Duration.ZERO;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.WHITE;

class MultiLineQuestionTest extends BaseGptUiTest {
    @Test
    void currentInteractionIsInMiddle() {
        assertion()
                .historySize(0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(null)
                .historyItems()
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

        clickOn(getThemeComboBox());
        overWrite(INTERACTION_1_THEME);
        clickOn(getQuestionTextArea());
        var questionLine1 = "Question line 1";
        var questionLine2 = "Question line 2";
        var questionLine3 = "Question line 3";
        overWrite(questionLine1).type(ENTER).write(questionLine2).type(ENTER).write(questionLine3);

        gptApi.clear()
                .putGrammarResponse(INTERACTION_1_GRAMMAR_HTML, ZERO)
                .putShortResponse(INTERACTION_1_SHORT_HTML, ZERO)
                .putLongResponse(INTERACTION_1_LONG_HTML, ZERO)
                .putGcpResponse(INTERACTION_1_GCP_HTML, ZERO);
        clickOn(getQuestionSendButton());

        var questionText = questionLine1 + "\n" + questionLine2 + "\n" + questionLine3;
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(1)
                .themeSelectedItem(INTERACTION_1_THEME)
                .themeItems(INTERACTION_1_THEME)
                .questionText(questionText)
                .modelEditedQuestion(questionText)
                .answerGrammarText(TestingData.EXP_GRAMMAR_HTML_BODY_1)
                .answerShortText(TestingData.EXP_SHORT_HTML_BODY_1)
                .answerLongText(TestingData.EXP_LONG_HTML_BODY_1)
                .answerGcpText(TestingData.EXP_GCP_HTML_BODY_1)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTemperatures(GRAMMAR_TEMPERATURE_DEFAULT, SHORT_TEMPERATURE_DEFAULT, LONG_TEMPERATURE_DEFAULT, GCP_TEMPERATURE_DEFAULT)
                .assertApp();
    }
}