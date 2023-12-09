package gptui.ui.history;

import gptui.ui.BaseGptUiTest;
import org.junit.jupiter.api.Test;

import static javafx.scene.paint.Color.WHITE;

class StartEmptyStorageTest extends BaseGptUiTest {
    @Test
    void startWithEmptyStorage() {
        assertion()
                .historySize(0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(null)
                .historyItems()
                .themeSize(0)
                .themeSelectedItem(null)
                .themeItems()
                .themeFilterHistorySelected(false)
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
}