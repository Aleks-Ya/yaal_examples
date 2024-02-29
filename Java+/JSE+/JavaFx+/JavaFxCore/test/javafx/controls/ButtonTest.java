package javafx.controls;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

class ButtonTest extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        var label = new Label("init text");
        var button = new Button("Change label");
        button.setId("change_label_button");
        button.setOnAction(evt -> label.setText("updated text"));
        var scene = new Scene(new VBox(button, new Separator(), label), 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void containButton() {
        var buttonByStyle = lookup(".button").queryButton();
        assertThat(buttonByStyle).isNotNull();
        var buttonById = lookup("#change_label_button").queryButton();
        assertThat(buttonById).isEqualTo(buttonByStyle);
    }

    @Test
    void clickButton() {
        var label = lookup(".label").queryLabeled();
        assertThat(label).extracting(Labeled::getText).isEqualTo("init text");
        var button = lookup("#change_label_button").queryButton();
        clickOn(button);
        assertThat(label).extracting(Labeled::getText).isEqualTo("updated text");
    }
}
