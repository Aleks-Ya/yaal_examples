package gptui.ui.history;

import gptui.ui.BaseGptUiTest;
import org.junit.jupiter.api.Test;

import static javafx.scene.paint.Color.WHITE;

class DeleteInteractionNullTest extends BaseGptUiTest {
    @Test
    void currentInteractionIsNull() {
        assertion()
                .historySize(0, 0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(null)
                .historyItems()
                .themeSize(0)
                .themeSelectedItem(null)
                .themeItems()
                .themeFilterHistorySelected(false)
                .questionText("")
                .modelEditedQuestion(null)
                .modelIsEnteringNewQuestion(false)
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