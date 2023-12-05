package gptui.ui;

import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.B;
import static javafx.scene.input.KeyCode.C;
import static org.assertj.core.api.Assertions.assertThat;

class ModelTest extends BaseGptUiTest {
    @Test
    void typeQuestion() {
        assertThat(model.getEditedQuestion()).isNull();
        clickOn(question().textArea()).type(A, B, C);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(model.getEditedQuestion()).isEqualTo("abc");
    }

    @Test
    void typeTheme() {
        assertThat(model.getCurrentTheme()).isNull();
        clickOn(theme().comboBox()).type(A, B, C);
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(model.getCurrentTheme()).isEqualTo("abc");
    }
}