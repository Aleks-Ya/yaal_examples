package gptui.ui;

import org.junit.jupiter.api.Test;

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
                .grammarA().text("")
                .shortA().text("")
                .longA().text("")
                .gcpA().text("")
                .answerCircleColors(WHITE, WHITE, WHITE, WHITE)
                .answerTextTemperaturesAllEmpty()
                .answerSpinnerTemperaturesDefault()
                .assertApp();

        clickOn(theme().comboBox());
        overWrite(INTERACTION_1_THEME);
        clickOn(question().textArea());
        var questionLine1 = "Question line 1";
        var questionLine2 = "Question line 2";
        var questionLine3 = "Question line 3";
        overWrite(questionLine1).type(ENTER).write(questionLine2).type(ENTER).write(questionLine3);

        gptApi.clear()
                .putGrammarResponse(INTERACTION_1_GRAMMAR_HTML, ZERO)
                .putShortResponse(INTERACTION_1_SHORT_HTML, ZERO)
                .putLongResponse(INTERACTION_1_LONG_HTML, ZERO)
                .putGcpResponse(INTERACTION_1_GCP_HTML, ZERO);
        clickOn(question().questionButton());

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
                .grammarA().text(TestingData.EXP_GRAMMAR_HTML_BODY_1)
                .shortA().text(TestingData.EXP_SHORT_HTML_BODY_1)
                .longA().text(TestingData.EXP_LONG_HTML_BODY_1)
                .gcpA().text(TestingData.EXP_GCP_HTML_BODY_1)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperaturesDefault()
                .answerSpinnerTemperaturesDefault()
                .assertApp();
    }
}