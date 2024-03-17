package javafx.fxml_.label_for;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static javafx.scene.input.KeyCode.ALT;
import static javafx.scene.input.KeyCode.T;
import static org.assertj.core.api.Assertions.assertThat;

class LabelForTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        new LabelForApp().start(stage);
    }

    @Test
    void typeText() {
        var initialFocusedTextField = lookup("#initialFocusedTextField").queryAs(TextField.class);
        var textField = lookup("#textField1").queryAs(TextField.class);

        assertThat(initialFocusedTextField.isFocused()).isTrue();
        assertThat(textField.isFocused()).isFalse();
        assertThat(textField.getText()).isEmpty();

        press(ALT, T).release(ALT, T);//Mnemonic for "label1"
        assertThat(textField.isFocused()).isTrue();
        assertThat(textField.getText()).isEmpty();

        write("text1");
        assertThat(textField.isFocused()).isTrue();
        assertThat(textField.getText()).isEqualTo("text1");
    }

}