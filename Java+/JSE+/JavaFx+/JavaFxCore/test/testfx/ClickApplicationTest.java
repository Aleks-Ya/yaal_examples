package testfx;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

/**
 * <a href="https://github.com/TestFX/TestFX/wiki/Getting-Started#simple-testfx-application-test">Source</a>
 */
class ClickApplicationTest extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        var sceneRoot = new ClickApplication.ClickPane();
        var scene = new Scene(sceneRoot, 100, 100);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void shouldContainButton() {
        verifyThat(".button", hasText("click me!"));
    }

    @Test
    void shouldClickOnButton() {
        clickOn(".button");
        verifyThat(".button", hasText("clicked!"));
    }
}
