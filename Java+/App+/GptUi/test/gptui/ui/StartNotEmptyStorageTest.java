package gptui.ui;

import org.junit.jupiter.api.Test;

import static gptui.ui.TestingData.INTERACTION_1;
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

class StartNotEmptyStorageTest extends BaseGptUiTest {

    @Override
    public void init() {
        storage.saveInteraction(INTERACTION_1);
        storage.saveInteraction(INTERACTION_2);
    }

    @Test
    void startWithNotEmptyStorage() {
        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(INTERACTION_2)
                .historyItems(INTERACTION_2, INTERACTION_1)
                .themeSize(2)
                .themeSelectedItem(INTERACTION_2_THEME)
                .themeItems(INTERACTION_2_THEME, INTERACTION_1_THEME)
                .themeFilterHistorySelected(false)
                .questionText(INTERACTION_2_QUESTION)
                .modelEditedQuestion(INTERACTION_2_QUESTION)
                .grammarA().text(INTERACTION_2_GRAMMAR_HTML)
                .shortA().text(INTERACTION_2_SHORT_HTML)
                .longA().text(INTERACTION_2_LONG_HTML)
                .gcpA().text(INTERACTION_2_GCP_HTML)
                .answerCircleColors(GREEN, GREEN, RED, GREEN)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(50, 60, 70, 80)
                .assertApp();
    }
}