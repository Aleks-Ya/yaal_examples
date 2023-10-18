package gptui.ui;

import gptui.storage.AnswerType;
import gptui.storage.Interaction;
import org.junit.jupiter.api.Test;

import static gptui.storage.AnswerState.FAIL;
import static gptui.ui.TestingData.INTERACTION_1;
import static java.time.Duration.ofMillis;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

class RegenerateQuestionTest extends BaseGptUiTest {
    private static final String EXP_GRAMMAR_HTML_BODY_2 = "<p>Grammar answer 2</p>\n";
    private static final String EXP_SHORT_HTML_BODY_2 = "<p>Short answer 2</p>\n";
    private static final String EXP_LONG_HTML_BODY_2 = "<p>Long answer 2</p>\n";
    private final Interaction interaction1 = INTERACTION_1
            .withAnswer(AnswerType.SHORT, answer -> answer.withState(FAIL))
            .withAnswer(AnswerType.LONG, answer -> answer.withState(FAIL));

    @Override
    public void init() {
        storage.saveInteraction(interaction1);
    }

    @Test
    void currentInteractionIsTheOnly() {
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(interaction1)
                .historyItems(interaction1)
                .themeSize(1)
                .themeSelectedItem("Theme 1")
                .themeItems("Theme 1")
                .questionText("Question 1")
                .modelEditedQuestion("Question 1")
                .answerGrammarText("Grammar answer HTML 1")
                .answerShortText("Short answer HTML 1")
                .answerLongText("Long answer HTML 1")
                .answerCircleColors(GREEN, RED, RED)
                .assertApp();

        gptApi.clear()
                .putGrammarResponse("Grammar answer 2", ofMillis(500))
                .putShortResponse("Short answer 2", ofMillis(1000))
                .putLongResponse("Long answer 2", ofMillis(1500));
        clickOn(getRegenerateButton());

        sleep(2000);
        assertion()
                .historySize(1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(interaction1.id()).orElseThrow())
                .historyItems(storage.readInteraction(interaction1.id()).orElseThrow())
                .themeSize(1)
                .themeSelectedItem("Theme 1")
                .themeItems("Theme 1")
                .questionText("Question 1")
                .modelEditedQuestion("Question 1")
                .answerGrammarText(EXP_GRAMMAR_HTML_BODY_2)
                .answerShortText(EXP_SHORT_HTML_BODY_2)
                .answerLongText(EXP_LONG_HTML_BODY_2)
                .answerCircleColors(GREEN, GREEN, GREEN)
                .assertApp();
    }
}