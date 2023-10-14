package gptui.ui;

import org.junit.jupiter.api.Test;

import static gptui.ui.TestingData.*;
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
                .themeSelectedItem("Theme 3")
                .themeItems("Theme 3", "Theme 2", "Theme 1")
                .questionText("Question 3")
                .modelEditedQuestion("Question 3")
                .answerGrammarText("Grammar answer HTML 3")
                .answerShortText("Short answer HTML 3")
                .answerLongText("Long answer HTML 3")
                .answerCircleColors(GREEN, GREEN, RED)
                .assertApp();

        clickOn(getHistoryComboBox()).clickOn(String.format("%s: %s", "Theme 2", "Question 2"));
        clickOn(getHistoryDeleteButton());

        assertion()
                .historySize(2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(INTERACTION_3)
                .historyItems(INTERACTION_3, INTERACTION_1)
                .themeSize(2)
                .themeSelectedItem("Theme 3")
                .themeItems("Theme 3", "Theme 1")
                .questionText("Question 3")
                .modelEditedQuestion("Question 3")
                .answerGrammarText("Grammar answer HTML 3")
                .answerShortText("Short answer HTML 3")
                .answerLongText("Long answer HTML 3")
                .answerCircleColors(GREEN, GREEN, RED)
                .assertApp();
    }
}