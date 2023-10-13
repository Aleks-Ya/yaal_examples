package gptui.ui;

import org.junit.jupiter.api.Test;

import static gptui.ui.TestingData.INTERACTION_1;
import static gptui.ui.TestingData.INTERACTION_2;
import static gptui.ui.TestingData.INTERACTION_3;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;

class DeleteInteractionMiddleTest extends BaseGptUiTest {
    @Override
    public void init() {
        storage.saveInteraction(INTERACTION_1);
        storage.saveInteraction(INTERACTION_2);
        storage.saveInteraction(INTERACTION_3);
    }

    @Test
    void currentInteractionIsInMiddle() {
        initialState();
        clickOn(getInteractionHistoryComboBox()).clickOn(String.format("%s: %s", "Theme 2", "Question 2"));
        clickOn(getInteractionHistoryDeleteButton());
        sleep(1000);
        afterDeletionState();
    }

    private void initialState() {
        verifyThat(getInteractionHistoryComboBox(), hasItems(3));
        verifyThat(getInteractionHistoryDeleteButton().isDisabled(), is(false));

        verifyThat(getThemeComboBox(), hasItems(3));

        assertThat(getQuestionTextArea().getText()).isEqualTo("Question 3");
        assertThat(model.getEditedQuestion()).isEqualTo("Question 3");

        verifyWebViewBody(getGrammarWebView(), "Grammar answer HTML 3");

        verifyWebViewBody(getShortAnswerWebView(), "Short answer HTML 3");
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(GREEN);

        verifyWebViewBody(getLongAnswerWebView(), "Long answer HTML 3");
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(RED);
    }

    void afterDeletionState() {
        verifyThat(getInteractionHistoryComboBox(), hasItems(2));
        verifyThat(getInteractionHistoryDeleteButton().isDisabled(), is(false));

        verifyThat(getThemeComboBox(), hasItems(2));

        assertThat(getQuestionTextArea().getText()).isEqualTo("Question 3");
        assertThat(model.getEditedQuestion()).isEqualTo("Question 3");

        verifyWebViewBody(getGrammarWebView(), "Grammar answer HTML 3");

        verifyWebViewBody(getShortAnswerWebView(), "Short answer HTML 3");
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(GREEN);

        verifyWebViewBody(getLongAnswerWebView(), "Long answer HTML 3");
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(RED);
    }
}