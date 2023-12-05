package javafx.controls;

import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class CheckBoxTest extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        var checkBox = new CheckBox("Select me");
        var scene = new Scene(new StackPane(checkBox), 300, 300);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void containCheckBox() {
        var checkBox = lookup(".check-box").queryAs(CheckBox.class);
        assertThat(checkBox).extracting(CheckBox::isSelected).isEqualTo(false);
        assertThat(checkBox).extracting(CheckBox::getText).isEqualTo("Select me");
    }

    @Test
    void tickCheckBox() {
        var checkBox = lookup(".check-box").queryAs(CheckBox.class);
        assertThat(checkBox).extracting(CheckBox::isSelected).isEqualTo(false);
        clickOn(checkBox);
        assertThat(checkBox).extracting(CheckBox::isSelected).isEqualTo(true);
        clickOn(checkBox);
        assertThat(checkBox).extracting(CheckBox::isSelected).isEqualTo(false);
    }

    @Test
    void handleTickCheckBox() {
        var checkBox = lookup(".check-box").queryAs(CheckBox.class);
        var history = new ArrayList<String>();
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) ->
                history.add(oldValue + "-" + newValue));
        clickOn(checkBox);
        clickOn(checkBox);
        clickOn(checkBox);
        assertThat(history).containsExactly("false-true", "true-false", "false-true");
    }
}
