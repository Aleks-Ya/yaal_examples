package gptui.ui;

import gptui.ui.controller.ClipboardHelper;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static gptui.storage.InteractionType.DEFINITION;
import static gptui.storage.InteractionType.FACT;
import static gptui.storage.InteractionType.GRAMMAR;
import static gptui.storage.InteractionType.QUESTION;
import static gptui.ui.TestingData.INTERACTION_1;
import static gptui.ui.TestingData.INTERACTION_1_QUESTION;
import static gptui.ui.TestingData.INTERACTION_1_THEME;
import static gptui.ui.TestingData.INTERACTION_2;
import static gptui.ui.TestingData.INTERACTION_3;
import static gptui.ui.TestingData.INTERACTION_3_GCP_HTML;
import static gptui.ui.TestingData.INTERACTION_3_GRAMMAR_HTML;
import static gptui.ui.TestingData.INTERACTION_3_LONG_HTML;
import static gptui.ui.TestingData.INTERACTION_3_QUESTION;
import static gptui.ui.TestingData.INTERACTION_3_SHORT_HTML;
import static gptui.ui.TestingData.INTERACTION_3_THEME;
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
        storage.saveInteraction(INTERACTION_1);
        storage.saveInteraction(INTERACTION_2);
        storage.saveInteraction(INTERACTION_3);
    }

    @Test
    void copyGrammarAnswerByAlt1() {
        verifyWebViewBody(getAnswerGrammarWebView(), INTERACTION_3_GRAMMAR_HTML);
        press(ALT, DIGIT1).release(DIGIT1, ALT);
        verifyHtmlClipboardContent(INTERACTION_3_GRAMMAR_HTML);
    }

    @Test
    void copyShortAnswerByAlt2() {
        verifyWebViewBody(getAnswerShortWebView(), INTERACTION_3_SHORT_HTML);
        press(ALT, DIGIT2).release(DIGIT2, ALT);
        verifyHtmlClipboardContent(INTERACTION_3_SHORT_HTML);
    }

    @Test
    void copyLongAnswerByAlt3() {
        verifyWebViewBody(getAnswerLongWebView(), INTERACTION_3_LONG_HTML);
        press(ALT, DIGIT3).release(DIGIT3, ALT);
        verifyHtmlClipboardContent(INTERACTION_3_LONG_HTML);
    }

    @Test
    void copyGcpAnswerByAlt4() {
        verifyWebViewBody(getAnswerGcpWebView(), INTERACTION_3_GCP_HTML);
        press(ALT, DIGIT4).release(DIGIT4, ALT);
        verifyHtmlClipboardContent(INTERACTION_3_GCP_HTML);
    }

    @Test
    void insertQuestionFromClipboardByCtrlAltV() {
        var clipboardHelper = new ClipboardHelper();
        assertThat(getQuestionTextArea().getText()).isEqualTo(INTERACTION_3_QUESTION);
        executeSyncInFxThread(() -> clipboardHelper.putHtmlToClipboard("From clipboard"));
        press(CONTROL, ALT, V).release(V, ALT, CONTROL);
        assertThat(getQuestionTextArea().getText()).isEqualTo("From clipboard");
    }

    @Test
    void sendQuestionByAltQ() {
        press(ALT, Q).release(Q, ALT);
        assertThat(model.getHistory()).hasSize(4)
                .element(0)
                .extracting(interactionId -> storage.readInteraction(interactionId).orElseThrow().type())
                .isEqualTo(QUESTION);
    }

    @Test
    void sendDefinitionByAltD() {
        press(ALT, D).release(D, ALT);
        assertThat(model.getHistory()).hasSize(4)
                .element(0)
                .extracting(interactionId -> storage.readInteraction(interactionId).orElseThrow().type())
                .isEqualTo(DEFINITION);
    }

    @Test
    void sendGrammarByAltG() {
        press(ALT, G).release(G, ALT);
        assertThat(model.getHistory()).hasSize(4)
                .element(0)
                .extracting(interactionId -> storage.readInteraction(interactionId).orElseThrow().type())
                .isEqualTo(GRAMMAR);
    }

    @Test
    void sendFactByAltF() {
        press(ALT, F).release(F, ALT);
        assertThat(model.getHistory()).hasSize(4)
                .element(0)
                .extracting(interactionId -> storage.readInteraction(interactionId).orElseThrow().type())
                .isEqualTo(FACT);
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
        assertThat(getQuestionTextArea().isFocused()).isFalse();
        press(ESCAPE).release(ESCAPE);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(getQuestionTextArea().isFocused()).isTrue();
    }

    @Test
    void sendQuestionByCtrlEnter() {
        press(CONTROL, ENTER).release(ENTER, CONTROL);
        assertThat(model.getHistory()).hasSize(4)
                .element(0)
                .extracting(interactionId -> storage.readInteraction(interactionId).orElseThrow().type())
                .isEqualTo(QUESTION);
    }

    @Test
    void selectNextInteractionByCtrlAltUp() {
        clickOn(getHistoryComboBox()).clickOn(String.format("[Q] %s: %s", INTERACTION_1_THEME, INTERACTION_1_QUESTION));
        assertThat(getHistoryComboBox().getSelectionModel().getSelectedItem()).isEqualTo(INTERACTION_1);

        press(CONTROL, ALT, UP).release(UP, ALT, CONTROL);
        assertThat(getHistoryComboBox().getSelectionModel().getSelectedItem()).isEqualTo(INTERACTION_2);

        press(CONTROL, ALT, UP).release(UP, ALT, CONTROL);
        assertThat(getHistoryComboBox().getSelectionModel().getSelectedItem()).isEqualTo(INTERACTION_3);

        press(CONTROL, ALT, UP).release(UP, ALT, CONTROL);
        assertThat(getHistoryComboBox().getSelectionModel().getSelectedItem()).isEqualTo(INTERACTION_3);
    }

    @Test
    void selectPreviousInteractionByCtrlAltDown() {
        clickOn(getHistoryComboBox()).clickOn(String.format("[Q] %s: %s", INTERACTION_3_THEME, INTERACTION_3_QUESTION));
        assertThat(getHistoryComboBox().getSelectionModel().getSelectedItem()).isEqualTo(INTERACTION_3);

        press(CONTROL, ALT, DOWN).release(DOWN, ALT, CONTROL);
        assertThat(getHistoryComboBox().getSelectionModel().getSelectedItem()).isEqualTo(INTERACTION_2);

        press(CONTROL, ALT, DOWN).release(DOWN, ALT, CONTROL);
        assertThat(getHistoryComboBox().getSelectionModel().getSelectedItem()).isEqualTo(INTERACTION_1);

        press(CONTROL, ALT, DOWN).release(DOWN, ALT, CONTROL);
        assertThat(getHistoryComboBox().getSelectionModel().getSelectedItem()).isEqualTo(INTERACTION_1);
    }
}