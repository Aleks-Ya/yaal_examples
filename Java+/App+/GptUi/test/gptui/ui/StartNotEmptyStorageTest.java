package gptui.ui;

import gptui.storage.Answer;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static gptui.storage.AnswerState.FAIL;
import static gptui.storage.AnswerState.SUCCESS;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.QUESTION_CORRECTNESS;
import static gptui.storage.AnswerType.SHORT;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class StartNotEmptyStorageTest extends BaseGptUiTest {

    @Override
    public void init() {
        var interaction1 = new Interaction(new InteractionId(1L), "Theme 1", "Question 1", Map.of(
                QUESTION_CORRECTNESS,
                new Answer(QUESTION_CORRECTNESS, "QC prompt 1", "QC answer MD 1", "QC answer HTML 1", SUCCESS),
                SHORT,
                new Answer(SHORT, "Short prompt 1", "Short answer MD 1", "Short answer HTML 1", SUCCESS),
                LONG,
                new Answer(LONG, "Long prompt 1", "Long answer MD 1", "Long answer HTML 1", SUCCESS)
        ));
        var interaction2 = new Interaction(new InteractionId(2L), "Theme 2", "Question 2", Map.of(
                QUESTION_CORRECTNESS,
                new Answer(QUESTION_CORRECTNESS, "QC prompt 2", "QC answer MD 2", "QC answer HTML 2", SUCCESS),
                SHORT,
                new Answer(SHORT, "Short prompt 2", "Short answer MD 2", "Short answer HTML 2", SUCCESS),
                LONG,
                new Answer(LONG, "Long prompt 2", "Long answer MD 2", "Long answer HTML 2", FAIL)
        ));
        storage.saveInteraction(interaction1);
        storage.saveInteraction(interaction2);
    }

    @Test
    void startWithNotEmptyStorage() {
        verifyThat(getInteractionHistoryLabel(), hasText("Question history:"));
        verifyThat(getInteractionHistoryComboBox(), hasItems(2));

        verifyThat(getThemeLabel(), hasText("Theme:"));
        verifyThat(getThemeComboBox(), hasItems(2));

        verifyThat(getQuestionLabel(), hasText("Question:"));
        assertThat(getQuestionTextArea().getText()).isEqualTo("Question 2");
        verifyThat(getQuestionSendButton().getText(), equalTo("Send"));

        verifyThat(getQuestionCorrectnessLabel(), hasText("Question\ncorrectness:"));
        verifyWebViewBody(getQuestionCorrectnessWebView(), "QC answer HTML 2");

        verifyThat(getShortAnswerLabel(), hasText("Short\nanswer:"));
        verifyWebViewBody(getShortAnswerWebView(), "Short answer HTML 2");
        verifyThat(getShortAnswerCopyButton().getText(), equalTo("Copy"));
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(GREEN);

        verifyThat(getLongAnswerLabel(), hasText("Long\nanswer:"));
        verifyWebViewBody(getLongAnswerWebView(), "Long answer HTML 2");
        verifyThat(getLongAnswerCopyButton().getText(), equalTo("Copy"));
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(RED);
    }

}