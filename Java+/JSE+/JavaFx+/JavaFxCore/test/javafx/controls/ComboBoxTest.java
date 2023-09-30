package javafx.controls;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.CONTROL;
import static javafx.scene.input.KeyCode.ENTER;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.containsExactlyItems;
import static org.testfx.matcher.control.ComboBoxMatchers.containsExactlyItemsInOrder;
import static org.testfx.matcher.control.ComboBoxMatchers.containsItems;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;

class ComboBoxTest extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        var comboBox = editableComboBox();
        var scene = new Scene(new StackPane(comboBox), 100, 100);
        stage.setScene(scene);
        stage.show();
    }

    private static ComboBox<Object> editableComboBox() {
        var comboBox = new ComboBox<>();
        var defaultOption = "Option 1";
        comboBox.getItems().addAll(defaultOption, "Option 2", "Option 3");
        comboBox.setEditable(true);
        comboBox.setValue(defaultOption);
        return comboBox;
    }

    @Test
    void shouldContainComboBox() {
        verifyThat(lookup(".combo-box").queryComboBox(), hasSelectedItem("Option 1"));
        verifyThat(lookup(".combo-box").queryComboBox(), hasItems(3));
        verifyThat(lookup(".combo-box").queryComboBox(), hasItems(3));
        verifyThat(lookup(".combo-box").queryComboBox(), containsExactlyItemsInOrder("Option 1", "Option 2", "Option 3"));
        verifyThat(lookup(".combo-box").queryComboBox(), containsItems("Option 3", "Option 2"));
        verifyThat(lookup(".combo-box").queryComboBox(), containsExactlyItems("Option 3", "Option 2", "Option 1"));
    }

    @Test
    void shouldWriteToComboBox() {
        clickOn(".combo-box");
        press(CONTROL).press(A).release(A).release(CONTROL);
        write("Option 4").press(ENTER);
        verifyThat(lookup(".combo-box").queryComboBox(), hasSelectedItem("Option 1"));
        verifyThat(lookup(".combo-box").queryComboBox(), containsExactlyItemsInOrder("Option 1", "Option 2", "Option 3"));
    }
}
