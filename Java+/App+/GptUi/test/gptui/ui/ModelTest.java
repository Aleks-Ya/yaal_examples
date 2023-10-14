package gptui.ui;

import org.junit.jupiter.api.Test;

import static javafx.scene.input.KeyCode.*;
import static org.assertj.core.api.Assertions.assertThat;

class ModelTest extends BaseGptUiTest {
    @Test
    void typeQuestion() {
        assertThat(model.getEditedQuestion()).isNull();
        clickOn(getQuestionTextArea()).type(A, B, C);
        assertThat(model.getEditedQuestion()).isEqualTo("abc");
    }

    @Test
    void typeTheme() {
        assertThat(model.getEditedTheme()).isNull();
        clickOn(getThemeComboBox()).type(A, B, C);
        assertThat(model.getEditedTheme()).isEqualTo("abc");
    }
}