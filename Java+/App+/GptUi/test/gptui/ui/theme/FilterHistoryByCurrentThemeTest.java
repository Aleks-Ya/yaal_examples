package gptui.ui.theme;

import gptui.ui.BaseGptUiTest;
import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import gptui.ui.TestingData.I3;
import org.junit.jupiter.api.Test;

import static gptui.viewmodel.question.QuestionStyle.QUESTION_STYLE_EMPTY;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

class FilterHistoryByCurrentThemeTest extends BaseGptUiTest {
    @Override
    public void init() {
        storage.saveTheme(I1.THEME);
        storage.saveTheme(I2.THEME);
        storage.saveTheme(I3.THEME);
        storage.saveInteraction(I1.INTERACTION);
        storage.saveInteraction(I2.INTERACTION);
        storage.saveInteraction(I3.INTERACTION);
    }

    @Test
    void filterHistory() {
        assertion()
                .focus(history().comboBox())
                .historySize(3, 3)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(I3.INTERACTION)
                .historyItems(I3.INTERACTION, I2.INTERACTION, I1.INTERACTION)
                .themeSize(3)
                .themeSelectedItem(I3.THEME)
                .themeItems(I3.THEME, I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I3.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
                .modelEditedQuestion(I3.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I3.GRAMMAR_HTML)
                .shortA().text(I3.SHORT_HTML)
                .longA().text(I3.LONG_HTML)
                .gcpA().text(I3.GCP_HTML)
                .answerCircleColors(GREEN, GREEN, RED, GREEN)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(50, 60, 70, 80)

                .work("Filter", () -> clickOn(theme().filterHistoryCheckBox()))
                .focus(theme().filterHistoryCheckBox())
                .historySize(1, 3)
                .historyItems(I3.INTERACTION)
                .themeFilterHistorySelected(true)

                .work("Remove Filter", () -> clickOn(theme().filterHistoryCheckBox()))
                .historySize(3, 3)
                .historyItems(I3.INTERACTION, I2.INTERACTION, I1.INTERACTION)
                .themeFilterHistorySelected(false)
                .assertApp();
    }
}