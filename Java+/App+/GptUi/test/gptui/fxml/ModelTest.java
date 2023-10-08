package gptui.fxml;

import org.junit.jupiter.api.Test;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.B;
import static javafx.scene.input.KeyCode.C;
import static org.assertj.core.api.Assertions.assertThat;

class ModelTest extends BaseGptUiTest {
    @Test
    void test() {
        typeTheme();
        typeQuestion();
    }

    private void typeQuestion() {
        assertThat(model.getEditedQuestion()).isNull();
        clickOn(getQuestionTextArea()).type(A, B, C);
        assertThat(model.getEditedQuestion()).isEqualTo("abc");
    }

    private void typeTheme() {
        assertThat(model.getEditedTheme()).isNull();
        clickOn(getThemeComboBox()).type(A, B, C);
        assertThat(model.getEditedTheme()).isEqualTo("abc");
    }
}