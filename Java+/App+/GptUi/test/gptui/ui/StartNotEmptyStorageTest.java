package gptui.ui;

import org.junit.jupiter.api.Test;

import static gptui.ui.TestingData.INTERACTION_1;
import static gptui.ui.TestingData.INTERACTION_2;
import static javafx.scene.paint.Color.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class StartNotEmptyStorageTest extends BaseGptUiTest {

    @Override
    public void init() {
        storage.saveInteraction(INTERACTION_1);
        storage.saveInteraction(INTERACTION_2);
    }

    @Test
    void startWithNotEmptyStorage() {
        verifyThat(getInteractionHistoryLabel(), hasText("Question history:"));
        verifyThat(getInteractionHistoryComboBox(), hasItems(2));
        verifyThat(getInteractionHistoryDeleteButton().getText(), equalTo("Delete"));
        verifyThat(getInteractionHistoryDeleteButton().isDisabled(), is(false));

        verifyThat(getThemeLabel(), hasText("Theme:"));
        verifyThat(getThemeComboBox(), hasItems(2));

        verifyThat(getQuestionLabel(), hasText("Question:"));
        assertThat(getQuestionTextArea().getText()).isEqualTo("Question 2");
        verifyThat(getQuestionSendButton().getText(), equalTo("Question"));
        verifyThat(getDefinitionSendButton().getText(), equalTo("Definition"));
        verifyThat(getGrammarSendButton().getText(), equalTo("Grammar"));
        assertThat(model.getEditedQuestion()).isEqualTo("Question 2");

        verifyThat(getGrammarAnswerLabel(), hasText("Grammar\nanswer:"));
        verifyWebViewBody(getGrammarAnswerWebView(), "Grammar answer HTML 2");
        verifyThat(getGrammarAnswerCopyButton().getText(), equalTo("Copy"));
        verifyThat(getGrammarAnswerCopyButton().isDisabled(), is(true));
        assertThat(getGrammarAnswerCircle().getFill()).isEqualTo(GREEN);

        verifyThat(getShortAnswerLabel(), hasText("Short\nanswer:"));
        verifyWebViewBody(getShortAnswerWebView(), "Short answer HTML 2");
        verifyThat(getShortAnswerCopyButton().getText(), equalTo("Copy"));
        verifyThat(getShortAnswerCopyButton().isDisabled(), is(false));
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(GREEN);

        verifyThat(getLongAnswerLabel(), hasText("Long\nanswer:"));
        verifyWebViewBody(getLongAnswerWebView(), "Long answer HTML 2");
        verifyThat(getLongAnswerCopyButton().getText(), equalTo("Copy"));
        verifyThat(getLongAnswerCopyButton().isDisabled(), is(false));
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(RED);
    }

}