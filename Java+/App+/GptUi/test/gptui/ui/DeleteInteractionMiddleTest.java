package gptui.ui;

import org.junit.jupiter.api.Test;

import static gptui.ui.TestingData.INTERACTION_1;
import static gptui.ui.TestingData.INTERACTION_1_THEME;
import static gptui.ui.TestingData.INTERACTION_2;
import static gptui.ui.TestingData.INTERACTION_2_QUESTION;
import static gptui.ui.TestingData.INTERACTION_2_THEME;
import static gptui.ui.TestingData.INTERACTION_3;
import static gptui.ui.TestingData.INTERACTION_3_GCP_HTML;
import static gptui.ui.TestingData.INTERACTION_3_GRAMMAR_HTML;
import static gptui.ui.TestingData.INTERACTION_3_LONG_HTML;
import static gptui.ui.TestingData.INTERACTION_3_QUESTION;
import static gptui.ui.TestingData.INTERACTION_3_SHORT_HTML;
import static gptui.ui.TestingData.INTERACTION_3_THEME;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

class DeleteInteractionMiddleTest extends BaseGptUiTest {
    @Override
    public void init() {
        storage.saveInteraction(INTERACTION_1);
        storage.saveInteraction(INTERACTION_2);
        storage.saveInteraction(INTERACTION_3);
    }

    @Test
    void currentInteractionIsInMiddle() {
        assertion()
                .historySize(3)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(INTERACTION_3)
                .historyItems(INTERACTION_3, INTERACTION_2, INTERACTION_1)
                .themeSize(3)
                .themeSelectedItem(INTERACTION_3_THEME)
                .themeItems(INTERACTION_3_THEME, INTERACTION_2_THEME, INTERACTION_1_THEME)
                .questionText(INTERACTION_3_QUESTION)
                .modelEditedQuestion(INTERACTION_3_QUESTION)
                .answerGrammarText(INTERACTION_3_GRAMMAR_HTML)
                .answerShortText(INTERACTION_3_SHORT_HTML)
                .answerLongText(INTERACTION_3_LONG_HTML)
                .answerGcpText(INTERACTION_3_GCP_HTML)
                .answerCircleColors(GREEN, GREEN, RED, GREEN)
                .answerTemperatures(0.5, 0.6, 0.7, 0.8)
                .assertApp();

        clickOn(getHistoryComboBox()).clickOn(String.format("[Q] %s: %s", INTERACTION_2_THEME, INTERACTION_2_QUESTION));
        clickOn(getHistoryDeleteButton());

        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(INTERACTION_3)
                .historyItems(INTERACTION_3, INTERACTION_1)
                .themeSize(2)
                .themeSelectedItem(INTERACTION_3_THEME)
                .themeItems(INTERACTION_3_THEME, INTERACTION_1_THEME)
                .questionText(INTERACTION_3_QUESTION)
                .modelEditedQuestion(INTERACTION_3_QUESTION)
                .answerGrammarText(INTERACTION_3_GRAMMAR_HTML)
                .answerShortText(INTERACTION_3_SHORT_HTML)
                .answerLongText(INTERACTION_3_LONG_HTML)
                .answerGcpText(INTERACTION_3_GCP_HTML)
                .answerCircleColors(GREEN, GREEN, RED, GREEN)
                .answerTemperatures(0.5, 0.6, 0.7, 0.8)
                .assertApp();
    }
}