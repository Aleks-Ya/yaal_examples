package gptui.ui;

import gptui.format.ClipboardHelper;
import org.junit.jupiter.api.Test;

import static gptui.storage.InteractionType.DEFINITION;
import static gptui.storage.InteractionType.FACT;
import static gptui.storage.InteractionType.GRAMMAR;
import static gptui.storage.InteractionType.QUESTION;
import static gptui.ui.TestingData.INTERACTION_1;
import static javafx.scene.input.KeyCode.ALT;
import static javafx.scene.input.KeyCode.CONTROL;
import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCode.F;
import static javafx.scene.input.KeyCode.G;
import static javafx.scene.input.KeyCode.Q;
import static javafx.scene.input.KeyCode.V;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testfx.api.FxAssert.verifyThat;

class HotKeysTest extends BaseGptUiTest {
    @Override
    public void init() {
        storage.saveInteraction(INTERACTION_1);
    }

    @Test
    void copyShortAnswerByCtrl1() {
        verifyWebViewBody(getAnswerShortWebView(), "Short answer HTML 1");
        verifyThat(getAnswerShortCopyButton().getText(), equalTo("Copy"));
        press(CONTROL, DIGIT1).release(DIGIT1, CONTROL);
        verifyHtmlClipboardContent("Short answer HTML 1");
    }

    @Test
    void copyLongAnswerByCtrl2() {
        verifyWebViewBody(getAnswerLongWebView(), "Long answer HTML 1");
        verifyThat(getAnswerLongCopyButton().getText(), equalTo("Copy"));
        press(CONTROL, DIGIT2).release(DIGIT2, CONTROL);
        verifyHtmlClipboardContent("Long answer HTML 1");
    }

    @Test
    void insertQuestionFromClipboardByCtrlAltV() {
        var clipboardHelper = new ClipboardHelper();
        assertThat(getQuestionTextArea().getText()).isEqualTo("Question 1");
        executeSyncInFxThread(() -> clipboardHelper.putHtmlToClipboard("From clipboard"));
        press(CONTROL, ALT, V).release(V, ALT, CONTROL);
        assertThat(getQuestionTextArea().getText()).isEqualTo("From clipboard");
    }

    @Test
    void sendQuestionByCtrlQ() {
        press(CONTROL, Q).release(Q, CONTROL);
        assertThat(model.getHistory()).hasSize(2)
                .element(0)
                .extracting(interactionId -> storage.readInteraction(interactionId).orElseThrow().type())
                .isEqualTo(QUESTION);
    }

    @Test
    void sendDefinitionByCtrlD() {
        press(CONTROL, D).release(D, CONTROL);
        assertThat(model.getHistory()).hasSize(2)
                .element(0)
                .extracting(interactionId -> storage.readInteraction(interactionId).orElseThrow().type())
                .isEqualTo(DEFINITION);
    }

    @Test
    void sendGrammarByCtrlG() {
        press(CONTROL, G).release(G, CONTROL);
        assertThat(model.getHistory()).hasSize(2)
                .element(0)
                .extracting(interactionId -> storage.readInteraction(interactionId).orElseThrow().type())
                .isEqualTo(GRAMMAR);
    }

    @Test
    void sendFactByCtrlF() {
        press(CONTROL, F).release(F, CONTROL);
        assertThat(model.getHistory()).hasSize(2)
                .element(0)
                .extracting(interactionId -> storage.readInteraction(interactionId).orElseThrow().type())
                .isEqualTo(FACT);
    }
}