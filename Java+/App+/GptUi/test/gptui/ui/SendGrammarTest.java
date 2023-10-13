package gptui.ui;

import org.junit.jupiter.api.Test;

import static gptui.ui.TestingData.*;
import static java.time.Duration.ofMillis;
import static javafx.scene.paint.Color.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;

class SendGrammarTest extends BaseGptUiTest {
    @Override
    public void init() {
        storage.saveInteraction(INTERACTION_1);
        storage.saveInteraction(INTERACTION_2);
        storage.saveInteraction(INTERACTION_3);
    }

    @Test
    void currentInteractionIsInMiddle() {
        initialState();
        gptApi.clear().put("has grammatical mistakes", "Grammar answer 4", ofMillis(500));
        clickOn(getQuestionTextArea());
        overWrite("Question 4");
        clickOn(getGrammarSendButton());
        sleep(1000);
        afterGrammarState();
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

    void afterGrammarState() {
        verifyThat(getInteractionHistoryComboBox(), hasItems(4));
        verifyThat(getInteractionHistoryDeleteButton().isDisabled(), is(false));

        verifyThat(getThemeComboBox(), hasItems(3));

        assertThat(getQuestionTextArea().getText()).isEqualTo("Question 4");
        assertThat(model.getEditedQuestion()).isEqualTo("Question 4");

        verifyWebViewBody(getGrammarWebView(), "<p>Grammar answer 4</p>\n");

        verifyWebViewBody(getShortAnswerWebView(), "");
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(WHITE);

        verifyWebViewBody(getLongAnswerWebView(), "");
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(WHITE);
    }
}