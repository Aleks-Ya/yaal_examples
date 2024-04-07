package gptui.view;

import gptui.model.storage.Interaction;
import gptui.ui.BaseGptUiTest;
import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import gptui.ui.TestingData.I3;
import javafx.geometry.VerticalDirection;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static gptui.model.storage.InteractionType.DEFINITION;
import static gptui.model.storage.InteractionType.FACT;
import static gptui.model.storage.InteractionType.GRAMMAR;
import static gptui.model.storage.InteractionType.QUESTION;
import static gptui.viewmodel.question.QuestionStyle.QUESTION_STYLE_EMPTY;
import static javafx.scene.input.KeyCode.ALT;
import static javafx.scene.input.KeyCode.CONTROL;
import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCode.DIGIT3;
import static javafx.scene.input.KeyCode.DIGIT4;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.E;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.F;
import static javafx.scene.input.KeyCode.G;
import static javafx.scene.input.KeyCode.H;
import static javafx.scene.input.KeyCode.M;
import static javafx.scene.input.KeyCode.Q;
import static javafx.scene.input.KeyCode.R;
import static javafx.scene.input.KeyCode.SPACE;
import static javafx.scene.input.KeyCode.T;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCode.V;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static org.assertj.core.api.Assertions.assertThat;

class HotKeysTest extends BaseGptUiTest {
    private static final String CLIPBOARD_CONTENT = "From clipboard";
    private static final String CLIPBOARD_CONTENT_WRAPPED = "<html><head></head><body>" + CLIPBOARD_CONTENT + "</body></html>";

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
    void hotKeys() {
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

                .work("Copy Grammar Answer By Alt-1", () -> press(ALT, DIGIT1).release(DIGIT1, ALT))
                .focus(grammarAnswer().copyButton())
                .clipboard(I3.GRAMMAR_HTML)

                .work("Copy Short Answer By Alt-2", () -> press(ALT, DIGIT2).release(DIGIT2, ALT))
                .focus(shortAnswer().copyButton())
                .clipboard(I3.SHORT_HTML)

                .work("Copy Long Answer By Alt-3", () -> press(ALT, DIGIT3).release(DIGIT3, ALT))
                .focus(longAnswer().copyButton())
                .clipboard(I3.LONG_HTML)

                .work("Copy GCP Answer By Alt-4", () -> press(ALT, DIGIT4).release(DIGIT4, ALT))
                .focus(gcpAnswer().copyButton())
                .clipboard(I3.GCP_HTML)

                .work("Paste Question From Clipboard By Ctrl-Alt-V", () -> {
                    executeSyncInFxThread(() -> clipboardModel.putHtmlToClipboard(CLIPBOARD_CONTENT_WRAPPED));
                    press(CONTROL, ALT, V).release(V, ALT, CONTROL);
                })
                .focus(gcpAnswer().copyButton())
                .questionText(CLIPBOARD_CONTENT_WRAPPED)
                .clipboard(CLIPBOARD_CONTENT)
                .modelIsEnteringNewQuestion(true)
                .modelEditedQuestion(CLIPBOARD_CONTENT_WRAPPED)

                .assertApp();

    }

    @Test
    void sendQuestionByAltQ() {
        press(ALT, Q).release(Q, ALT);
        assertThat(stateModel.getFullHistory()).hasSize(4)
                .element(0).extracting(Interaction::type).isEqualTo(QUESTION);
    }

    @Test
    void sendDefinitionByAltD() {
        press(ALT, D).release(D, ALT);
        assertThat(stateModel.getFullHistory()).hasSize(4)
                .element(0).extracting(Interaction::type).isEqualTo(DEFINITION);
    }

    @Test
    void sendGrammarByAltG() {
        press(ALT, G).release(G, ALT);
        assertThat(stateModel.getFullHistory()).hasSize(4)
                .element(0).extracting(Interaction::type).isEqualTo(GRAMMAR);
    }

    @Test
    void sendFactByAltF() {
        press(ALT, F).release(F, ALT);
        assertThat(stateModel.getFullHistory()).hasSize(4)
                .element(0).extracting(Interaction::type).isEqualTo(FACT);
    }

    @Test
    void resendByAltR() {
        gptApi.clear();
        assertThat(gptApi.getSendHistory()).isEmpty();
        press(ALT, R).release(R, ALT);
        assertThat(gptApi.getSendHistory()).hasSize(4);
    }

    @Test
    void focusOnQuestionByEsc() {
        assertThat(question().textArea().isFocused()).isFalse();
        press(ESCAPE).release(ESCAPE);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(question().textArea().isFocused()).isTrue();
    }

    @Test
    void sendQuestionByCtrlEnter() {
        press(CONTROL, ENTER).release(ENTER, CONTROL);
        assertThat(stateModel.getFullHistory()).hasSize(4)
                .element(0).extracting(Interaction::type).isEqualTo(QUESTION);
    }

    @Test
    void selectNextInteractionByCtrlAltUp() {
        clickOn(history().comboBox()).clickOn(String.format("[Q] %s: %s", I1.THEME.title(), I1.QUESTION));
        assertThat(history().comboBox().getSelectionModel().getSelectedItem().interaction()).isEqualTo(I1.INTERACTION);

        press(CONTROL, ALT, UP).release(UP, ALT, CONTROL);
        assertThat(history().comboBox().getSelectionModel().getSelectedItem().interaction()).isEqualTo(I2.INTERACTION);

        press(CONTROL, ALT, UP).release(UP, ALT, CONTROL);
        assertThat(history().comboBox().getSelectionModel().getSelectedItem().interaction()).isEqualTo(I3.INTERACTION);

        press(CONTROL, ALT, UP).release(UP, ALT, CONTROL);
        assertThat(history().comboBox().getSelectionModel().getSelectedItem().interaction()).isEqualTo(I3.INTERACTION);
    }

    @Test
    void selectPreviousInteractionByCtrlAltDown() {
        clickOn(history().comboBox()).clickOn(String.format("[Q] %s: %s", I3.THEME.title(), I3.QUESTION));
        assertThat(history().comboBox().getSelectionModel().getSelectedItem().interaction()).isEqualTo(I3.INTERACTION);

        press(CONTROL, ALT, DOWN).release(DOWN, ALT, CONTROL);
        assertThat(history().comboBox().getSelectionModel().getSelectedItem().interaction()).isEqualTo(I2.INTERACTION);

        press(CONTROL, ALT, DOWN).release(DOWN, ALT, CONTROL);
        assertThat(history().comboBox().getSelectionModel().getSelectedItem().interaction()).isEqualTo(I1.INTERACTION);

        press(CONTROL, ALT, DOWN).release(DOWN, ALT, CONTROL);
        assertThat(history().comboBox().getSelectionModel().getSelectedItem().interaction()).isEqualTo(I1.INTERACTION);
    }

    @Test
    void selectPreviousInteractionByCtrlAltDown_FocusOnWebView() {
        clickOn(history().comboBox()).clickOn(String.format("[Q] %s: %s", I3.THEME.title(), I3.QUESTION));
        clickOn(longAnswer().webView());
        assertThat(history().comboBox().getSelectionModel().getSelectedItem().interaction()).isEqualTo(I3.INTERACTION);

        press(CONTROL, ALT, DOWN).release(CONTROL, ALT, DOWN);
        assertThat(history().comboBox().getSelectionModel().getSelectedItem().interaction()).isEqualTo(I2.INTERACTION);
    }

    @Test
    void selectPreviousInteractionByCtrlAltUp_FocusOnWebView() {
        clickOn(history().comboBox()).clickOn(String.format("[Q] %s: %s", I2.THEME.title(), I2.QUESTION));
        clickOn(longAnswer().webView());
        scroll(10, VerticalDirection.DOWN);
        assertThat(history().comboBox().getSelectionModel().getSelectedItem().interaction()).isEqualTo(I2.INTERACTION);

        press(CONTROL, ALT, UP).release(UP, ALT, CONTROL);
        assertThat(history().comboBox().getSelectionModel().getSelectedItem().interaction()).isEqualTo(I1.INTERACTION);
    }

    @Test
    void altTFocusOnThemeComboBox() {
        assertThat(theme().comboBox().isFocused()).isFalse();
        assertThat(theme().comboBox().getSelectionModel().getSelectedItem()).isEqualTo(I3.THEME);
        press(ALT, T).release(ALT, T);
        assertThat(theme().comboBox().isFocused()).isTrue();
        type(T, H, E, M, E, SPACE, DIGIT2, ENTER);
        assertThat(theme().comboBox().getSelectionModel().getSelectedItem()).isEqualTo(I2.THEME);
    }
}