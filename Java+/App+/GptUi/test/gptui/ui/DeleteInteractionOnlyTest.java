package gptui.ui;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static gptui.ui.TestingData.INTERACTION_1;
import static gptui.ui.TestingData.INTERACTION_1_GCP_HTML;
import static gptui.ui.TestingData.INTERACTION_1_GRAMMAR_HTML;
import static gptui.ui.TestingData.INTERACTION_1_LONG_HTML;
import static gptui.ui.TestingData.INTERACTION_1_QUESTION;
import static gptui.ui.TestingData.INTERACTION_1_SHORT_HTML;
import static gptui.ui.TestingData.INTERACTION_1_THEME;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.WHITE;

class DeleteInteractionOnlyTest extends BaseGptUiTest {
    @Override
    public void init() {
        storage.saveInteraction(INTERACTION_1);
    }

    @Test
    void currentInteractionIsTheOnly() {
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(INTERACTION_1)
                .historyItems(INTERACTION_1)
                .themeSize(1)
                .themeSelectedItem(INTERACTION_1_THEME)
                .themeItems(INTERACTION_1_THEME)
                .questionText(INTERACTION_1_QUESTION)
                .modelEditedQuestion(INTERACTION_1_QUESTION)
                .answerGrammarText(INTERACTION_1_GRAMMAR_HTML)
                .answerShortText(INTERACTION_1_SHORT_HTML)
                .answerLongText(INTERACTION_1_LONG_HTML)
                .answerGcpText(INTERACTION_1_GCP_HTML)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTemperatures(0.5, 0.6, 0.7, 0.8)
                .assertApp();

        clickOn(getHistoryDeleteButton());

        assertion()
                .historySize(0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(null)
                .historyItems()
                .themeSize(0)
                .themeSelectedItem(null)
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
    }
}