package gptui.ui;

import gptui.ui.controller.ClipboardHelper;
import org.junit.jupiter.api.Test;

import static gptui.storage.InteractionType.DEFINITION;
import static gptui.storage.InteractionType.FACT;
import static gptui.storage.InteractionType.GRAMMAR;
import static gptui.storage.InteractionType.QUESTION;
import static gptui.ui.TestingData.INTERACTION_1;
import static gptui.ui.TestingData.INTERACTION_1_GCP_HTML;
import static gptui.ui.TestingData.INTERACTION_1_GRAMMAR_HTML;
import static gptui.ui.TestingData.INTERACTION_1_LONG_HTML;
import static gptui.ui.TestingData.INTERACTION_1_QUESTION;
import static gptui.ui.TestingData.INTERACTION_1_SHORT_HTML;
import static javafx.scene.input.KeyCode.ALT;
import static javafx.scene.input.KeyCode.CONTROL;
import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCode.DIGIT3;
import static javafx.scene.input.KeyCode.DIGIT4;
import static javafx.scene.input.KeyCode.F;
import static javafx.scene.input.KeyCode.G;
import static javafx.scene.input.KeyCode.Q;
import static javafx.scene.input.KeyCode.R;
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
    void copyGrammarAnswerByAlt1() {
        verifyWebViewBody(getAnswerGrammarWebView(), INTERACTION_1_GRAMMAR_HTML);
        verifyThat(getAnswerGrammarCopyButton().getText(), equalTo("Copy"));
        press(ALT, DIGIT1).release(DIGIT1, ALT);
        verifyHtmlClipboardContent(INTERACTION_1_GRAMMAR_HTML);
    }

    @Test
    void copyShortAnswerByAlt2() {
        verifyWebViewBody(getAnswerShortWebView(), INTERACTION_1_SHORT_HTML);
        verifyThat(getAnswerShortCopyButton().getText(), equalTo("Copy"));
        press(ALT, DIGIT2).release(DIGIT2, ALT);
        verifyHtmlClipboardContent(INTERACTION_1_SHORT_HTML);
    }

    @Test
    void copyLongAnswerByAlt3() {
        verifyWebViewBody(getAnswerLongWebView(), INTERACTION_1_LONG_HTML);
        verifyThat(getAnswerLongCopyButton().getText(), equalTo("Copy"));
        press(ALT, DIGIT3).release(DIGIT3, ALT);
        verifyHtmlClipboardContent(INTERACTION_1_LONG_HTML);
    }

    @Test
    void copyGcpAnswerByAlt4() {
        verifyWebViewBody(getAnswerGcpWebView(), INTERACTION_1_GCP_HTML);
        verifyThat(getAnswerGcpCopyButton().getText(), equalTo("Copy"));
        press(ALT, DIGIT4).release(DIGIT4, ALT);
        verifyHtmlClipboardContent(INTERACTION_1_GCP_HTML);
    }

    @Test
    void insertQuestionFromClipboardByCtrlAltV() {
        var clipboardHelper = new ClipboardHelper();
        assertThat(getQuestionTextArea().getText()).isEqualTo(INTERACTION_1_QUESTION);
        executeSyncInFxThread(() -> clipboardHelper.putHtmlToClipboard("From clipboard"));
        press(CONTROL, ALT, V).release(V, ALT, CONTROL);
        assertThat(getQuestionTextArea().getText()).isEqualTo("From clipboard");
    }

    @Test
    void sendQuestionByAltQ() {
        press(ALT, Q).release(Q, ALT);
        assertThat(model.getHistory()).hasSize(2)
                .element(0)
                .extracting(interactionId -> storage.readInteraction(interactionId).orElseThrow().type())
                .isEqualTo(QUESTION);
    }

    @Test
    void sendDefinitionByAltD() {
        press(ALT, D).release(D, ALT);
        assertThat(model.getHistory()).hasSize(2)
                .element(0)
                .extracting(interactionId -> storage.readInteraction(interactionId).orElseThrow().type())
                .isEqualTo(DEFINITION);
    }

    @Test
    void sendGrammarByAltG() {
        press(ALT, G).release(G, ALT);
        assertThat(model.getHistory()).hasSize(2)
                .element(0)
                .extracting(interactionId -> storage.readInteraction(interactionId).orElseThrow().type())
                .isEqualTo(GRAMMAR);
    }

    @Test
    void sendFactByAltF() {
        press(ALT, F).release(F, ALT);
        assertThat(model.getHistory()).hasSize(2)
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
}