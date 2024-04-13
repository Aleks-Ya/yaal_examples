package gptui.ui.theme;

import gptui.ui.BaseGptUiTest;
import gptui.ui.TestingData.I0;
import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import static gptui.viewmodel.question.QuestionStyle.QUESTION_STYLE_EMPTY;
import static javafx.scene.paint.Color.WHITE;

class AddThemeTest extends BaseGptUiTest {
    @Test
    void addThemes() {
        assertion()
                .focus(history().comboBox())
                .historySize(0, 0)
                .historyDeleteButtonDisabled(true)
                .historySelectedItem(I0.HISTORY_SELECTED_ITEM)
                .historyItems(I0.HISTORY_ITEMS)
                .themeSize(I0.THEME_SIZE)
                .themeSelectedItem(I0.THEME_SELECTED_ITEM)
                .themeItems(I0.THEME_ITEMS)
                .themeFilterHistorySelected(false)
                .questionText(I0.QUESTION)
                .questionStyle(QUESTION_STYLE_EMPTY)
                .modelEditedQuestion(null)
                .modelIsEnteringNewQuestion(false)
                .grammarA().text(I0.GRAMMAR_HTML)
                .shortA().text(I0.SHORT_HTML)
                .longA().text(I0.LONG_HTML)
                .gcpA().text(I0.GCP_HTML)
                .answerCircleColors(WHITE, WHITE, WHITE, WHITE)
                .answerTextTemperaturesAllEmpty()
                .answerSpinnerTemperaturesDefault()

                .work("Add Theme 1", () ->
                        clickOn(theme().addThemeButton()).write(I1.THEME.title()).type(KeyCode.ENTER))
                .focus(question().textArea())
                .themeSize(1)
                .themeSelectedItem(I1.THEME)
                .themeItems(I1.THEME)

                .work("Add Theme 2", () ->
                        clickOn(theme().addThemeButton()).write(I2.THEME.title()).type(KeyCode.ENTER))
                .themeSize(2)
                .themeSelectedItem(I2.THEME)
                .themeItems(I1.THEME, I2.THEME)

                .work("Add Duplicating Theme", () ->
                        clickOn(theme().addThemeButton()).write(I2.THEME.title()).type(KeyCode.ENTER))
                .assertApp();
    }
}