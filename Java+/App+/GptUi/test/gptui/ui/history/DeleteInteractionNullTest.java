package gptui.ui.history;

import gptui.ui.BaseGptUiTest;
import gptui.ui.TestingData.I0;
import org.junit.jupiter.api.Test;

import static javafx.scene.paint.Color.WHITE;

class DeleteInteractionNullTest extends BaseGptUiTest {
    @Test
    void currentInteractionIsNull() {
        assertion()
                .historySize(0, 0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(I0.HISTORY_SELECTED_ITEM)
                .historyItems()
                .themeSize(I0.THEME_SIZE)
                .themeSelectedItem(I0.THEME_SELECTED_ITEM)
                .themeItems(I0.THEME_ITEMS)
                .themeFilterHistorySelected(false)
                .questionText(I0.QUESTION)
                .modelEditedQuestion(null)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I0.GRAMMAR_HTML)
                .shortA().text(I0.SHORT_HTML)
                .longA().text(I0.LONG_HTML)
                .gcpA().text(I0.GCP_HTML)
                .answerCircleColors(WHITE, WHITE, WHITE, WHITE)
                .answerTextTemperaturesAllEmpty()
                .answerSpinnerTemperaturesDefault()
                .assertApp();
    }
}