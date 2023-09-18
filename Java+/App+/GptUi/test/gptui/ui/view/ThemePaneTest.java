package gptui.ui.view;

import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class ThemePaneTest extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        var sceneRoot = new ThemePane();
        var scene = new Scene(sceneRoot, 100, 100);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void shouldContainHeader() {
        verifyThat(".label", hasText("Theme:"));
    }

    @Test
    void shouldContainComboBox() {
        var items = lookup(".combo-box").queryAs(ComboBox.class).getItems();
        assertThat(items).isEmpty();
    }
}