package gptui.ui;

import org.junit.jupiter.api.Test;

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
                .themeFilterHistorySelected(false)
                .questionText(INTERACTION_1_QUESTION)
                .modelEditedQuestion(INTERACTION_1_QUESTION)
                .grammarA().text(INTERACTION_1_GRAMMAR_HTML)
                .shortA().text(INTERACTION_1_SHORT_HTML)
                .longA().text(INTERACTION_1_LONG_HTML)
                .gcpA().text(INTERACTION_1_GCP_HTML)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(50, 60, 70, 80)
                .assertApp();

        clickOn(history().deleteButton());

        assertion()
                .historySize(0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(null)
                .historyItems()
                .themeSize(0)
                .themeSelectedItem(null)
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
                .answerSpinnerTemperatures(50, 60, 70, 80)
                .assertApp();
    }
}