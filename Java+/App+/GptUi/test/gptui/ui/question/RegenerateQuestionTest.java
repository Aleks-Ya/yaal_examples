package gptui.ui.question;

import gptui.model.storage.Interaction;
import gptui.ui.BaseGptUiTest;
import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import org.junit.jupiter.api.Test;

import static gptui.model.storage.AnswerState.FAIL;
import static gptui.model.storage.AnswerType.GCP;
import static gptui.model.storage.AnswerType.LONG;
import static gptui.model.storage.AnswerType.SHORT;
import static gptui.viewmodel.Styles.QUESTION_STYLE_EMPTY;
import static java.time.Duration.ZERO;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

class RegenerateQuestionTest extends BaseGptUiTest {
    private final Interaction interaction1 = I1.INTERACTION
            .withAnswer(SHORT, answer -> answer.withState(FAIL))
            .withAnswer(LONG, answer -> answer.withState(FAIL))
            .withAnswer(GCP, answer -> answer.withState(FAIL));

    @Override
    public void init() {
        storage.saveTheme(I1.THEME);
        storage.saveInteraction(interaction1);
    }

    @Test
    void currentInteractionIsTheOnly() {
        assertion()
                .focus(history().comboBox())
                .historySize(1, 1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(interaction1)
                .historyItems(interaction1)
                .themeSize(1)
                .themeSelectedItem(I1.THEME)
                .themeItems(I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
                .modelEditedQuestion(I1.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I1.GRAMMAR_HTML)
                .shortA().text(I1.SHORT_HTML)
                .longA().text(I1.LONG_HTML)
                .gcpA().text(I1.GCP_HTML)
                .answerCircleColors(GREEN, RED, RED, RED)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(50, 60, 70, 80)
                .assertApp();

        gptApi.clear()
                .putGrammarResponse(I2.GRAMMAR_HTML, ZERO)
                .putShortResponse(I2.SHORT_HTML, ZERO)
                .putLongResponse(I2.LONG_HTML, ZERO)
                .putGcpResponse(I2.GCP_HTML, ZERO);
        clickOn(question().regenerateButton());
        gptApi.waitUntilSent(4);

        assertion()
                .focus(question().regenerateButton())
                .historySize(1, 1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(storage.readInteraction(interaction1.id()).orElseThrow())
                .historyItems(storage.readInteraction(interaction1.id()).orElseThrow())
                .themeSize(1)
                .themeSelectedItem(I1.THEME)
                .themeItems(I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
                .modelEditedQuestion(I1.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I2.EXP_GRAMMAR_HTML_BODY)
                .shortA().text(I2.EXP_SHORT_HTML_BODY)
                .longA().text(I2.EXP_LONG_HTML_BODY)
                .gcpA().text(I2.EXP_GCP_HTML_BODY)
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(50, 60, 70, 80)
                .assertApp();
    }
}