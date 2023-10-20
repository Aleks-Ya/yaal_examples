package gptui.ui;

import org.junit.jupiter.api.Test;

import static gptui.ui.TestingData.INTERACTION_1;
import static gptui.ui.TestingData.INTERACTION_1_QUESTION;
import static gptui.ui.TestingData.INTERACTION_1_THEME;
import static gptui.ui.TestingData.INTERACTION_2;
import static gptui.ui.TestingData.INTERACTION_2_GCP_HTML;
import static gptui.ui.TestingData.INTERACTION_2_GRAMMAR_HTML;
import static gptui.ui.TestingData.INTERACTION_2_LONG_HTML;
import static gptui.ui.TestingData.INTERACTION_2_QUESTION;
import static gptui.ui.TestingData.INTERACTION_2_SHORT_HTML;
import static gptui.ui.TestingData.INTERACTION_2_THEME;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

class DeleteInteractionBottomTest extends BaseGptUiTest {
    @Override
    public void init() {
        storage.saveInteraction(INTERACTION_1);
        storage.saveInteraction(INTERACTION_2);
    }

    @Test
    void currentInteractionIsAtBottom() {
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(INTERACTION_2)
                .historyItems(INTERACTION_2, INTERACTION_1)
                .themeSize(2)
                .themeSelectedItem(INTERACTION_2_THEME)
                .themeItems(INTERACTION_2_THEME, INTERACTION_1_THEME)
                .questionText(INTERACTION_2_QUESTION)
                .modelEditedQuestion(INTERACTION_2_QUESTION)
                .answerGrammarText(INTERACTION_2_GRAMMAR_HTML)
                .answerShortText(INTERACTION_2_SHORT_HTML)
                .answerLongText(INTERACTION_2_LONG_HTML)
                .answerGcpText(INTERACTION_2_GCP_HTML)
                .answerCircleColors(GREEN, GREEN, RED, GREEN)
                .assertApp();

        clickOn(getHistoryComboBox()).clickOn(String.format("[Q] %s: %s", INTERACTION_1_THEME, INTERACTION_1_QUESTION));
        clickOn(getHistoryDeleteButton());

        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(INTERACTION_2)
                .historyItems(INTERACTION_2)
                .themeSize(1)
                .themeSelectedItem(INTERACTION_2_THEME)
                .themeItems(INTERACTION_2_THEME)
                .questionText(INTERACTION_2_QUESTION)
                .modelEditedQuestion(INTERACTION_2_QUESTION)
                .answerGrammarText(INTERACTION_2_GRAMMAR_HTML)
                .answerShortText(INTERACTION_2_SHORT_HTML)
                .answerLongText(INTERACTION_2_LONG_HTML)
                .answerGcpText(INTERACTION_2_GCP_HTML)
                .answerCircleColors(GREEN, GREEN, RED, GREEN)
                .assertApp();
    }
}