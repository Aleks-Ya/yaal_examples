package javafx.controls;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

class TextAreaTest extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        var textArea = new TextArea("Text Area 1");
        var scene = new Scene(new StackPane(textArea), 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void containTextArea() {
        var textArea = lookup(".text-area").queryAs(TextArea.class);
        assertThat(textArea).extracting(TextArea::getText).isEqualTo("Text Area 1");
    }

    @Test
    void writeText() {
        var textArea = lookup(".text-area").queryAs(TextArea.class);
        assertThat(textArea).extracting(TextArea::getText).isEqualTo("Text Area 1");
        clickOn(textArea);
        write(" updated");
        assertThat(textArea).extracting(TextArea::getText).isEqualTo("Text Area 1 updated");
    }

    @Test
    void updateText() {
        var textArea = lookup(".text-area").queryAs(TextArea.class);
        assertThat(textArea).extracting(TextArea::getText).isEqualTo("Text Area 1");
        textArea.setText("Text Area 2");
        assertThat(textArea).extracting(TextArea::getText).isEqualTo("Text Area 2");
    }

    @Test
    void changeBackgroundColor() {
        var textArea = lookup(".text-area").queryAs(TextArea.class);
        assertThat(textArea).extracting(TextArea::getStyle).isEqualTo("");

        var style = "-fx-control-inner-background: red;";
        textArea.setStyle(style);
        assertThat(textArea).extracting(TextArea::getStyle).isEqualTo(style);
    }
}
