package gptui.ui;

import org.junit.jupiter.api.Test;

import static javafx.scene.paint.Color.WHITE;

class DeleteInteractionNullTest extends BaseGptUiTest {
    @Test
    void currentInteractionIsNull() {
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
                .answerTemperaturesAllEmpty()
                .assertApp();
    }
}