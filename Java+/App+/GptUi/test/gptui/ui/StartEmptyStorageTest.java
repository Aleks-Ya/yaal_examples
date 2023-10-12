package gptui.ui;

import org.junit.jupiter.api.Test;

import static javafx.scene.paint.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class StartEmptyStorageTest extends BaseGptUiTest {
    @Test
    void startWithEmptyStorage() {
        verifyThat(getInteractionHistoryLabel(), hasText("Question history:"));
        verifyThat(getInteractionHistoryComboBox(), hasItems(0));

        verifyThat(getThemeLabel(), hasText("Theme:"));
        verifyThat(getThemeComboBox(), hasItems(0));

        verifyThat(getQuestionLabel(), hasText("Question:"));
        verifyThat(getQuestionTextArea().getText(), emptyString());
        verifyThat(getQuestionSendButton().getText(), equalTo("Question"));
        verifyThat(getDefinitionSendButton().getText(), equalTo("Definition"));

        verifyThat(getGrammarLabel(), hasText("Grammar:"));
        verifyWebViewBody(getgrammarWebView(), "");

        verifyThat(getShortAnswerLabel(), hasText("Short\nanswer:"));
        verifyThat(getShortAnswerCopyButton().getText(), equalTo("Copy"));
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(WHITE);

        verifyThat(getLongAnswerLabel(), hasText("Long\nanswer:"));
        verifyWebViewBody(getLongAnswerWebView(), "");
        verifyThat(getLongAnswerCopyButton().getText(), equalTo("Copy"));
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(WHITE);
    }
}