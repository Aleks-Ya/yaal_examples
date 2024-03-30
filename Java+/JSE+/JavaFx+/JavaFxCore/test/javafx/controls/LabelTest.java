package javafx.controls;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.assertj.core.api.Assertions.assertThat;

class LabelTest extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        var label = new Label("Label 1");
        var scene = new Scene(new StackPane(label), 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void containLabel() {
        var label = lookup(".label").queryLabeled();
        assertThat(label).extracting(Labeled::getText).isEqualTo("Label 1");
    }

    @Test
    void updateText() {
        var label = lookup(".label").queryLabeled();
        assertThat(label).extracting(Labeled::getText).isEqualTo("Label 1");
        Platform.runLater(() -> label.setText("Label 2"));
        WaitForAsyncUtils.waitForFxEvents();
        assertThat(label).extracting(Labeled::getText).isEqualTo("Label 2");
    }

    @Test
    void changeBackgroundColor() {
        var label = lookup(".label").queryLabeled();
        assertThat(label).extracting(Labeled::getStyle).isEqualTo("");

        var style = "-fx-background-color: red;";
        label.setStyle(style);
        assertThat(label).extracting(Labeled::getStyle).isEqualTo(style);
    }
}
