package gptui.ui;

import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static gptui.storage.InteractionType.QUESTION;
import static gptui.ui.TestingData.INTERACTION_2;
import static javafx.scene.paint.Color.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;

class ChooseInteractionFromHistoryTest extends BaseGptUiTest {
    private static final InteractionId interactionId1 = new InteractionId(1L);
    private static final InteractionId interactionId2 = new InteractionId(2L);

    @Override
    public void init() {
        var interaction1 = new Interaction(interactionId1, QUESTION, "Theme 1", "Question 1", Map.of());
        storage.saveInteraction(interaction1);
        storage.saveInteraction(INTERACTION_2);
    }

    @Test
    void shouldCleanAnswerWebViewIfChosenInteractionHasNoAnswer() {
        initialState();
        chooseInteraction1();
    }

    private void initialState() {
        verifyThat(getInteractionHistoryComboBox(), hasItems(2));
        verifyThat(getInteractionHistoryComboBox(), hasSelectedItem(storage.readInteraction(interactionId2).orElseThrow()));
        verifyThat(getThemeComboBox(), hasItems(2));
        verifyThat(getThemeComboBox(), hasSelectedItem("Theme 2"));
        assertThat(getQuestionTextArea().getText()).isEqualTo("Question 2");
        verifyWebViewBody(getGrammarAnswerWebView(), "Grammar answer HTML 2");
        verifyWebViewBody(getShortAnswerWebView(), "Short answer HTML 2");
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(GREEN);
        verifyWebViewBody(getLongAnswerWebView(), "Long answer HTML 2");
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(RED);
    }

    private void chooseInteraction1() {
        clickOn(getInteractionHistoryComboBox()).clickOn("Theme 1: Question 1");
        verifyThat(getInteractionHistoryComboBox(), hasItems(2));
        verifyThat(getInteractionHistoryComboBox(), hasSelectedItem(storage.readInteraction(interactionId1).orElseThrow()));
        verifyThat(getThemeComboBox(), hasItems(2));
        verifyThat(getThemeComboBox(), hasSelectedItem("Theme 1"));
        assertThat(getQuestionTextArea().getText()).isEqualTo("Question 1");
        verifyWebViewBody(getGrammarAnswerWebView(), "");
        verifyWebViewBody(getShortAnswerWebView(), "");
        assertThat(getShortAnswerCircle().getFill()).isEqualTo(WHITE);
        verifyWebViewBody(getLongAnswerWebView(), "");
        assertThat(getLongAnswerCircle().getFill()).isEqualTo(WHITE);
    }

}