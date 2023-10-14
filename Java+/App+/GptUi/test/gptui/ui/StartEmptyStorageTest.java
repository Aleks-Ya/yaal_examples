package gptui.ui;

import org.junit.jupiter.api.Test;

import static javafx.scene.paint.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class StartEmptyStorageTest extends BaseGptUiTest {
    @Test
    void startWithEmptyStorage() {
        verifyThat(getInteractionHistoryLabel(), hasText("Question history:"));
        verifyThat(getInteractionHistoryComboBox(), hasItems(0));
        verifyThat(getInteractionHistoryDeleteButton().getText(), equalTo("Delete"));
        verifyThat(getInteractionHistoryDeleteButton().isDisabled(), is(true));

        verifyThat(getThemeLabel(), hasText("Theme:"));
        verifyThat(getThemeComboBox(), hasItems(0));

        verifyThat(getQuestionLabel(), hasText("Question:"));
        verifyThat(getQuestionTextArea().getText(), emptyString());
        verifyThat(getQuestionSendButton().getText(), equalTo("Question"));
        verifyThat(getDefinitionSendButton().getText(), equalTo("Definition"));
        verifyThat(getGrammarSendButton().getText(), equalTo("Grammar"));
        verifyThat(getFactSendButton().getText(), equalTo("Fact"));
        assertThat(model.getEditedQuestion()).isNull();

        verifyThat(getGrammarAnswerLabel(), hasText("Grammar\nanswer:"));
        verifyWebViewBody(getGrammarAnswerWebView(), "");
        verifyThat(getGrammarAnswerCopyButton().getText(), equalTo("Copy"));
        verifyThat(getGrammarAnswerCopyButton().isDisabled(), is(true));
        assertThat(getGrammarAnswerCircle().getFill()).isEqualTo(WHITE);

        verifyThat(getShortAnswerLabel(), hasText("Short\nanswer:"));
        verifyThat(getShortAnswerCopyButton().getText(), equalTo("Copy"));
        verifyThat(getShortAnswerCopyButton().isDisabled(), is(false));
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(WHITE);

        verifyThat(getLongAnswerLabel(), hasText("Long\nanswer:"));
        verifyWebViewBody(getLongAnswerWebView(), "");
        verifyThat(getLongAnswerCopyButton().getText(), equalTo("Copy"));
        verifyThat(getLongAnswerCopyButton().isDisabled(), is(false));
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(WHITE);
    }
}