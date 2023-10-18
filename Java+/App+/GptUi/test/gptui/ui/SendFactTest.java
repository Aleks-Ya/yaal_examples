package gptui.ui;

import org.junit.jupiter.api.Test;

import static gptui.ui.TestingData.INTERACTION_1;
import static gptui.ui.TestingData.INTERACTION_2;
import static gptui.ui.TestingData.INTERACTION_3;
import static java.time.Duration.ofMillis;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;

class SendFactTest extends BaseGptUiTest {
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

        gptApi.clear().putFactResponse("Fact answer 4", ofMillis(500));
        clickOn(getQuestionTextArea());
        overWrite("Question 4");
        clickOn(getFactSendButton());
        sleep(1000);

        assertion()
                .historySize(4)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(3)
                .themeSelectedItem("Theme 3")
                .themeItems("Theme 3", "Theme 2", "Theme 1")
                .questionText("Question 4")
                .modelEditedQuestion("Question 4")
                .answerGrammarText("<p>Fact answer 4</p>\n")
                .answerShortText("")
                .answerLongText("")
                .answerCircleColors(GREEN, WHITE, WHITE)
                .assertApp();
    }
}