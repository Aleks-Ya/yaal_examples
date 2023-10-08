package gptui.ui;

import gptui.format.ClipboardHelper;
import gptui.storage.Answer;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static gptui.storage.AnswerState.SUCCESS;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.QUESTION_CORRECTNESS;
import static gptui.storage.AnswerType.SHORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testfx.api.FxAssert.verifyThat;

class HotKeysTest extends BaseGptUiTest {

    @Override
    public void init() {
        storage.saveInteraction(new Interaction(new InteractionId(1L), "Theme 1", "Question 1", Map.of(
                QUESTION_CORRECTNESS,
                new Answer(QUESTION_CORRECTNESS, "QC prompt 1", "QC answer MD 1", "QC answer HTML 1", SUCCESS),
                SHORT,
                new Answer(SHORT, "Short prompt 1", "Short answer MD 1", "Short answer HTML 1", SUCCESS),
                LONG,
                new Answer(LONG, "Long prompt 1", "Long answer MD 1", "Long answer HTML 1", SUCCESS)
        )));
    }

    @Test
    void copyShortAnswer() {
        verifyWebViewBody(getShortAnswerWebView(), "Short answer HTML 1");
        verifyThat(getShortAnswerCopyButton().getText(), equalTo("Copy"));
        press(KeyCode.CONTROL, KeyCode.DIGIT1);
        verifyHtmlClipboardContent("Short answer HTML 1");
    }

    @Test
    void copyLongAnswer() {
        verifyWebViewBody(getLongAnswerWebView(), "Long answer HTML 1");
        verifyThat(getLongAnswerCopyButton().getText(), equalTo("Copy"));
        press(KeyCode.CONTROL, KeyCode.DIGIT2);
        verifyHtmlClipboardContent("Long answer HTML 1");
    }

    @Test
    void insertQuestionFromClipboard() {
        var clipboardHelper = new ClipboardHelper();
        assertThat(getQuestionTextArea().getText()).isEqualTo("Question 1");
        executeSyncInFxThread(() -> clipboardHelper.putHtmlToClipboard("From clipboard"));
        press(KeyCode.CONTROL, KeyCode.ALT, KeyCode.V);
        assertThat(getQuestionTextArea().getText()).isEqualTo("From clipboard");
    }
}