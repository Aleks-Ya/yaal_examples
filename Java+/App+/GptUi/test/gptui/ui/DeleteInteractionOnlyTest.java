package gptui.ui;

import org.junit.jupiter.api.Test;

import static gptui.ui.TestingData.INTERACTION_1;
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
                .themeSelectedItem("Theme 1")
                .themeItems("Theme 1")
                .questionText("Question 1")
                .modelEditedQuestion("Question 1")
                .answerGrammarText("Grammar answer HTML 1")
                .answerShortText("Short answer HTML 1")
                .answerLongText("Long answer HTML 1")
                .answerCircleColors(GREEN, GREEN, GREEN)
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
                .questionText("Question 1")
                .modelEditedQuestion("Question 1")
                .answerGrammarText("")
                .answerShortText("")
                .answerLongText("")
                .answerCircleColors(WHITE, WHITE, WHITE)
                .assertApp();
    }
}