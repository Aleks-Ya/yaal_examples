package javafx.controls;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Separator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static javafx.scene.input.KeyCode.BACK_SPACE;
import static javafx.scene.input.KeyCode.CONTROL;
import static javafx.scene.input.KeyCode.ENTER;
import static org.assertj.core.api.Assertions.assertThat;

class TextInputDialogTest extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        var dialog = new TextInputDialog("John");
        dialog.setTitle("Name");
        dialog.setHeaderText("Enter your name:");
        var label = new Label("no name");
        var button = new Button("Enter name");
        button.setOnAction(evt -> dialog.showAndWait().ifPresentOrElse(label::setText, () -> label.setText("<cancelled>")));
        var scene = new Scene(new VBox(button, new Separator(), label), 640, 300);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    @Disabled("fails")
    void enterValue() {
        var button = lookup(".button").queryButton();
        var label = lookup(".label").queryLabeled();
        assertThat(label).extracting(Labeled::getText).isEqualTo("no name");
        clickOn(button);
        clickOn(".text-field");
        press(CONTROL, BACK_SPACE);
        write("Mary");
        press(ENTER);
        assertThat(label).extracting(Labeled::getText).isEqualTo("Mary");
    }

}
