package javafx.controls;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static javafx.scene.input.KeyCode.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.*;

class ComboBoxEditableTest extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        var comboBox = editableComboBox();
        var scene = new Scene(new StackPane(comboBox), 100, 100);
        stage.setScene(scene);
        stage.show();
    }

    private static ComboBox<String> editableComboBox() {
        var comboBox = new ComboBox<String>();
        var defaultOption = "Option 1";
        comboBox.getItems().addAll(defaultOption, "Option 2", "Option 3");
        comboBox.setEditable(true);
        comboBox.setValue(defaultOption);
        return comboBox;
    }

    @Test
    void shouldContainComboBox() {
        var comboBox = lookup(".combo-box").queryComboBox();
        verifyThat(comboBox, hasSelectedItem("Option 1"));
        verifyThat(comboBox, hasItems(3));
        verifyThat(comboBox, containsExactlyItemsInOrder("Option 1", "Option 2", "Option 3"));
        verifyThat(comboBox, containsItems("Option 3", "Option 2"));
        verifyThat(comboBox, containsExactlyItems("Option 3", "Option 2", "Option 1"));
    }

    @Test
    void shouldWriteToComboBox() {
        var comboBox = lookup(".combo-box").queryComboBox();
        clickOn(comboBox);
        press(CONTROL).press(A).release(A).release(CONTROL);
        write("Option 4").press(ENTER);
        verifyThat(comboBox, hasSelectedItem("Option 1"));
        verifyThat(comboBox, containsExactlyItemsInOrder("Option 1", "Option 2", "Option 3"));
    }

    @Test
    void shouldChooseAnotherItemInComboBox() {
        var comboBox = lookup(".combo-box").queryComboBox();
        clickOn(comboBox).press(ALT, DOWN).clickOn("Option 2");
        verifyThat(comboBox, hasSelectedItem("Option 2"));
        verifyThat(comboBox, containsExactlyItemsInOrder("Option 1", "Option 2", "Option 3"));
    }
}
