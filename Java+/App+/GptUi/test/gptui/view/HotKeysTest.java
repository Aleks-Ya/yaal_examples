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
import static javafx.scene.input.KeyCode.ALT;
import static javafx.scene.input.KeyCode.CONTROL;
import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCode.DIGIT3;
import static javafx.scene.input.KeyCode.DIGIT4;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.F;
import static javafx.scene.input.KeyCode.G;
import static javafx.scene.input.KeyCode.Q;
import static javafx.scene.input.KeyCode.R;
import static javafx.scene.input.KeyCode.UP;
import static javafx.scene.input.KeyCode.V;
import static org.assertj.core.api.Assertions.assertThat;

class HotKeysTest extends BaseGptUiTest {
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
    void copyGrammarAnswerByAlt1() {
        verifyWebViewBody(grammarAnswer().webView(), I3.GRAMMAR_HTML);
        press(ALT, DIGIT1).release(DIGIT1, ALT);
        verifyHtmlClipboardContent(I3.GRAMMAR_HTML);
    }

    @Test
    void copyShortAnswerByAlt2() {
        verifyWebViewBody(shortAnswer().webView(), I3.SHORT_HTML);
        press(ALT, DIGIT2).release(DIGIT2, ALT);
        verifyHtmlClipboardContent(I3.SHORT_HTML);
    }

    @Test
    void copyLongAnswerByAlt3() {
        verifyWebViewBody(longAnswer().webView(), I3.LONG_HTML);
        press(ALT, DIGIT3).release(DIGIT3, ALT);
        verifyHtmlClipboardContent(I3.LONG_HTML);
    }

    @Test
    void copyGcpAnswerByAlt4() {
        verifyWebViewBody(gcpAnswer().webView(), I3.GCP_HTML);
        press(ALT, DIGIT4).release(DIGIT4, ALT);
        verifyHtmlClipboardContent(I3.GCP_HTML);
    }

    @Test
    void pasteQuestionFromClipboardByCtrlAltV() {
        assertThat(question().textArea().getText()).isEqualTo(I3.QUESTION);
        var content = "From clipboard";
        executeSyncInFxThread(() -> clipboardModel.putHtmlToClipboard(content));
        press(CONTROL, ALT, V).release(V, ALT, CONTROL);
        assertThat(question().textArea().getText()).isEqualTo(content);
        assertThat(stateModel.getEditedQuestion()).isEqualTo(content);
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
}