package gptui.ui;

import gptui.format.ClipboardHelper;
import gptui.storage.Interaction;
import org.junit.jupiter.api.Test;

import static gptui.storage.InteractionType.*;
import static gptui.ui.TestingData.INTERACTION_1;
import static javafx.scene.input.KeyCode.*;
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
        verifyWebViewBody(getShortAnswerWebView(), "Short answer HTML 1");
        verifyThat(getShortAnswerCopyButton().getText(), equalTo("Copy"));
        press(CONTROL, DIGIT1).release(DIGIT1, CONTROL);
        verifyHtmlClipboardContent("Short answer HTML 1");
    }

    @Test
    void copyLongAnswerByCtrl2() {
        verifyWebViewBody(getLongAnswerWebView(), "Long answer HTML 1");
        verifyThat(getLongAnswerCopyButton().getText(), equalTo("Copy"));
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
        assertThat(model.getInteractionHistory()).hasSize(2)
                .element(0).extracting(Interaction::type).isEqualTo(QUESTION);
    }

    @Test
    void sendDefinitionByCtrlD() {
        press(CONTROL, D).release(D, CONTROL);
        assertThat(model.getInteractionHistory()).hasSize(2)
                .element(0).extracting(Interaction::type).isEqualTo(DEFINITION);
    }

    @Test
    void sendGrammarByCtrlG() {
        press(CONTROL, G).release(G, CONTROL);
        assertThat(model.getInteractionHistory()).hasSize(2)
                .element(0).extracting(Interaction::type).isEqualTo(GRAMMAR);
    }
}