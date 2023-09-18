package gptui.ui.view;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class QuestionCorrectnessPaneTest extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        var sceneRoot = new QuestionCorrectnessPane();
        var scene = new Scene(sceneRoot, 100, 100);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void shouldContainHeader() {
        verifyThat(".label", hasText("Question\ncorrectness:"));
    }

    @Test
    void shouldContainTextArea() {
        var text = lookup(".text-area").queryAs(TextArea.class).getText();
        assertThat(text).isEmpty();
    }
}