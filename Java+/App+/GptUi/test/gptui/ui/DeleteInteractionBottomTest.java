package gptui.ui;

import org.junit.jupiter.api.Test;

import static gptui.ui.TestingData.INTERACTION_1;
import static gptui.ui.TestingData.INTERACTION_2;
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
                .themeSelectedItem("Theme 2")
                .themeItems("Theme 2", "Theme 1")
                .questionText("Question 2")
                .modelEditedQuestion("Question 2")
                .answerGrammarText("Grammar answer HTML 2")
                .answerShortText("Short answer HTML 2")
                .answerLongText("Long answer HTML 2")
                .answerCircleColors(GREEN, GREEN, RED)
                .assertApp();

        clickOn(getHistoryComboBox()).clickOn(String.format("[Q] %s: %s", "Theme 1", "Question 1"));
        clickOn(getHistoryDeleteButton());

        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(INTERACTION_2)
                .historyItems(INTERACTION_2)
                .themeSize(1)
                .themeSelectedItem("Theme 2")
                .themeItems("Theme 2")
                .questionText("Question 2")
                .modelEditedQuestion("Question 2")
                .answerGrammarText("Grammar answer HTML 2")
                .answerShortText("Short answer HTML 2")
                .answerLongText("Long answer HTML 2")
                .answerCircleColors(GREEN, GREEN, RED)
                .assertApp();
    }
}