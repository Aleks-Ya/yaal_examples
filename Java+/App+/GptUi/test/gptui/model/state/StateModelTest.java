package gptui.model.state;

import gptui.ui.BaseGptUiTest;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.B;
import static javafx.scene.input.KeyCode.C;
import static org.assertj.core.api.Assertions.assertThat;

class StateModelTest extends BaseGptUiTest {
    @Test
    void typeQuestion() {
        assertThat(stateModel.getEditedQuestion()).isNull();
        clickOn(question().textArea()).type(A, B, C);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(stateModel.getEditedQuestion()).isEqualTo("abc");
    }

    @Test
    void typeTheme() {
        assertThat(stateModel.getCurrentTheme()).isNull();
        clickOn(theme().comboBox()).type(A, B, C);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(stateModel.getCurrentTheme().title()).isEqualTo("abc");
    }
}