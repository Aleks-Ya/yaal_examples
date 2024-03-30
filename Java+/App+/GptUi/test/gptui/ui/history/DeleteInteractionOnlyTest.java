package gptui.ui.history;

import gptui.ui.BaseGptUiTest;
import gptui.ui.TestingData.I0;
import gptui.ui.TestingData.I1;
import org.junit.jupiter.api.Test;

import static gptui.viewmodel.Styles.QUESTION_STYLE_EMPTY;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.WHITE;

class DeleteInteractionOnlyTest extends BaseGptUiTest {
    @Override
    public void init() {
        storage.saveTheme(I1.THEME);
        storage.saveInteraction(I1.INTERACTION);
    }

    @Test
    void currentInteractionIsTheOnly() {
        assertion()
                .focus(history().comboBox())
                .historySize(1, 1)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(I1.INTERACTION)
                .historyItems(I1.INTERACTION)
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
                .answerCircleColors(GREEN, GREEN, GREEN, GREEN)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(50, 60, 70, 80)
                .assertApp();

        clickOn(history().deleteButton());

        assertion()
                .focus(theme().comboBox())
                .historySize(0, 0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(I0.HISTORY_SELECTED_ITEM)
                .historyItems()
                .themeSize(1)
                .themeSelectedItem(I0.THEME_SELECTED_ITEM)
                .themeItems(I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I1.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
                .modelEditedQuestion(I1.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I0.GRAMMAR_HTML)
                .shortA().text(I0.SHORT_HTML)
                .longA().text(I0.LONG_HTML)
                .gcpA().text(I0.GCP_HTML)
                .answerCircleColors(WHITE, WHITE, WHITE, WHITE)
                .answerTextTemperaturesAllEmpty()
                .answerSpinnerTemperatures(50, 60, 70, 80)
                .assertApp();
    }
}