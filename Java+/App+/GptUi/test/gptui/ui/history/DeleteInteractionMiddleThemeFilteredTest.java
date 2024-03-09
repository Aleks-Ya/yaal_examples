package gptui.ui.history;

import gptui.ui.BaseGptUiTest;
import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import gptui.ui.TestingData.I3;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

class DeleteInteractionMiddleThemeFilteredTest extends BaseGptUiTest {
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
    void currentInteractionIsInMiddle() {
        assertion()
                .historySize(3, 3)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(I3.INTERACTION)
                .historyItems(I3.INTERACTION, I2.INTERACTION, I1.INTERACTION)
                .themeSize(3)
                .themeSelectedItem(I3.THEME)
                .themeItems(I3.THEME, I2.THEME, I1.THEME)
                .themeFilterHistorySelected(false)
                .questionText(I3.QUESTION)
                .modelEditedQuestion(I3.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I3.GRAMMAR_HTML)
                .shortA().text(I3.SHORT_HTML)
                .longA().text(I3.LONG_HTML)
                .gcpA().text(I3.GCP_HTML)
                .answerCircleColors(GREEN, GREEN, RED, GREEN)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(50, 60, 70, 80)
                .assertApp();

        clickOn(history().comboBox()).clickOn(format("[Q] %s: %s", I2.THEME.title(), I2.QUESTION));
        clickOn(theme().filterHistoryCheckBox());

        assertion()
                .historySize(1, 3)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(I2.INTERACTION)
                .historyItems(I2.INTERACTION)
                .themeSize(3)
                .themeSelectedItem(I2.THEME)
                .themeItems(I3.THEME, I2.THEME, I1.THEME)
                .themeFilterHistorySelected(true)
                .questionText(I2.QUESTION)
                .modelEditedQuestion(I2.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I2.GRAMMAR_HTML)
                .shortA().text(I2.SHORT_HTML)
                .longA().text(I2.LONG_HTML)
                .gcpA().text(I2.GCP_HTML)
                .answerCircleColors(GREEN, GREEN, RED, GREEN)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(50, 60, 70, 80)
                .assertApp();

        clickOn(history().deleteButton());

        assertion()
                .historySize(1, 2)
                .historyDeleteButtonDisabled(false)
                .historySelectedItem(I3.INTERACTION)
                .historyItems(I3.INTERACTION)
                .themeSize(3)
                .themeSelectedItem(I3.THEME)
                .themeItems(I3.THEME, I2.THEME, I1.THEME)
                .themeFilterHistorySelected(true)
                .questionText(I3.QUESTION)
                .modelEditedQuestion(I3.QUESTION)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I3.GRAMMAR_HTML)
                .shortA().text(I3.SHORT_HTML)
                .longA().text(I3.LONG_HTML)
                .gcpA().text(I3.GCP_HTML)
                .answerCircleColors(GREEN, GREEN, RED, GREEN)
                .answerTextTemperatures(50, 60, 70, 80)
                .answerSpinnerTemperatures(50, 60, 70, 80)
                .assertApp();
    }
}