package gptui.ui;

import org.junit.jupiter.api.Test;

import static gptui.ui.TestingData.INTERACTION_1;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;

class DeleteInteractionOnlyTest extends BaseGptUiTest {
    @Override
    public void init() {
        storage.saveInteraction(INTERACTION_1);
    }

    @Test
    void currentInteractionIsTheOnly() {
        initialState();
        clickOn(getInteractionHistoryDeleteButton());
        afterDeletionState();
    }

    private void initialState() {
        verifyThat(getInteractionHistoryComboBox(), hasItems(1));
        verifyThat(getInteractionHistoryDeleteButton().isDisabled(), is(false));

        verifyThat(getThemeComboBox(), hasItems(1));

        assertThat(getQuestionTextArea().getText()).isEqualTo("Question 1");
        assertThat(model.getEditedQuestion()).isEqualTo("Question 1");

        verifyWebViewBody(getGrammarAnswerWebView(), "Grammar answer HTML 1");

        verifyWebViewBody(getShortAnswerWebView(), "Short answer HTML 1");
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(GREEN);

        verifyWebViewBody(getLongAnswerWebView(), "Long answer HTML 1");
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(GREEN);
    }

    void afterDeletionState() {
        verifyThat(getInteractionHistoryComboBox(), hasItems(0));
        verifyThat(getInteractionHistoryDeleteButton().isDisabled(), is(true));

        verifyThat(getThemeComboBox(), hasItems(0));

        assertThat(getQuestionTextArea().getText()).isEqualTo("Question 1");
        assertThat(model.getEditedQuestion()).isEqualTo("Question 1");

        verifyWebViewBody(getGrammarAnswerWebView(), "");

        verifyWebViewBody(getShortAnswerWebView(), "");
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(WHITE);

        verifyWebViewBody(getLongAnswerWebView(), "");
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(WHITE);
    }
}