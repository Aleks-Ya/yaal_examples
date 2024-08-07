package javafx.controls;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.ALT;
import static javafx.scene.input.KeyCode.CONTROL;
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.ENTER;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.containsExactlyItems;
import static org.testfx.matcher.control.ComboBoxMatchers.containsExactlyItemsInOrder;
import static org.testfx.matcher.control.ComboBoxMatchers.containsItems;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;

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
        press(CONTROL, A).release(CONTROL, A);
        write("Option 4").type(ENTER);
        verifyThat(comboBox, hasSelectedItem("Option 4"));
        verifyThat(comboBox, containsExactlyItemsInOrder("Option 1", "Option 2", "Option 3"));
    }

    @Test
    void shouldChooseAnotherItemInComboBox() {
        var comboBox = lookup(".combo-box").queryComboBox();
        clickOn(comboBox).press(ALT, DOWN).release(ALT, DOWN).clickOn("Option 2");
        verifyThat(comboBox, hasSelectedItem("Option 2"));
        verifyThat(comboBox, containsExactlyItemsInOrder("Option 1", "Option 2", "Option 3"));
    }
}
