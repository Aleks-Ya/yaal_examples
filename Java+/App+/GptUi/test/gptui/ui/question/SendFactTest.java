package gptui.ui.question;

import gptui.ui.BaseGptUiTest;
import org.junit.jupiter.api.Test;

import static gptui.ui.TestingData.INTERACTION_1;
import static gptui.ui.TestingData.INTERACTION_1_THEME;
import static gptui.ui.TestingData.INTERACTION_2;
import static gptui.ui.TestingData.INTERACTION_2_THEME;
import static gptui.ui.TestingData.INTERACTION_3;
import static gptui.ui.TestingData.INTERACTION_3_GCP_HTML;
import static gptui.ui.TestingData.INTERACTION_3_GRAMMAR_HTML;
import static gptui.ui.TestingData.INTERACTION_3_LONG_HTML;
import static gptui.ui.TestingData.INTERACTION_3_QUESTION;
import static gptui.ui.TestingData.INTERACTION_3_SHORT_HTML;
import static gptui.ui.TestingData.INTERACTION_3_THEME;
import static java.time.Duration.ZERO;
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
                .themeSelectedItem(INTERACTION_3_THEME)
                .themeItems(INTERACTION_3_THEME, INTERACTION_2_THEME, INTERACTION_1_THEME)
                .themeFilterHistorySelected(false)
                .questionText(INTERACTION_3_QUESTION)
                .modelEditedQuestion(INTERACTION_3_QUESTION)
                .grammarA().text(INTERACTION_3_GRAMMAR_HTML)
                .shortA().text(INTERACTION_3_SHORT_HTML)
                .longA().text(INTERACTION_3_LONG_HTML)
                .gcpA().text(INTERACTION_3_GCP_HTML)
                .answerCircleColors(GREEN, GREEN, RED, GREEN)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(50, 60, 70, 80)
                .assertApp();

        gptApi.clear().putFactResponse("Fact answer 4", ZERO);
        clickOn(question().textArea());
        overWrite("Question 4");
        clickOn(question().factButton());

        assertion()
                .historySize(4)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readAllInteractions().getFirst())
                .historyItems(storage.readAllInteractions())
                .themeSize(3)
                .themeSelectedItem(INTERACTION_3_THEME)
                .themeItems(INTERACTION_3_THEME, INTERACTION_2_THEME, INTERACTION_1_THEME)
                .themeFilterHistorySelected(false)
                .questionText("Question 4")
                .modelEditedQuestion("Question 4")
                .grammarA().text("<p>Fact answer 4</p>\n")
                .shortA().text("")
                .longA().text("")
                .gcpA().text("")
                .answerCircleColors(GREEN, WHITE, WHITE, WHITE)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(50, 60, 70, 80)
                .assertApp();
    }
}