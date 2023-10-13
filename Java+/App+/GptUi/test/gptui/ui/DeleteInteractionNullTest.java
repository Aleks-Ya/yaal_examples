package gptui.ui;

import org.junit.jupiter.api.Test;

import static javafx.scene.paint.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;

class DeleteInteractionNullTest extends BaseGptUiTest {
    @Test
    void currentInteractionIsNull() {
        verifyThat(getInteractionHistoryComboBox(), hasItems(0));
        verifyThat(getInteractionHistoryDeleteButton().isDisabled(), is(true));

        verifyThat(getThemeComboBox(), hasItems(0));

        assertThat(getQuestionTextArea().getText()).isEmpty();
        assertThat(model.getEditedQuestion()).isNull();

        verifyWebViewBody(getGrammarAnswerWebView(), "");

        verifyWebViewBody(getShortAnswerWebView(), "");
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(WHITE);

        verifyWebViewBody(getLongAnswerWebView(), "");
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(WHITE);
    }
}