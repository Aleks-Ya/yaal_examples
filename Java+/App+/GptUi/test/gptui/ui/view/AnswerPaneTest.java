package gptui.ui.view;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static gptui.ui.view.AnswerPane.STATUS_CIRCLE_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.ColorMatchers.isColor;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class AnswerPaneTest extends ApplicationTest {
    private static final String header = "Short answer";

    @Override
    public void start(Stage stage) {
        var sceneRoot = new AnswerPane(header, "AnswerPane");
        var scene = new Scene(sceneRoot, 100, 100);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void shouldContainHeader() {
        verifyThat(".label", hasText(header));
    }

    @Test
    void shouldContainButton() {
        verifyThat(".button", hasText("Copy"));
    }

    @Test
    void shouldContainStatusCircle() {
        var fill = (Color) lookup("#" + STATUS_CIRCLE_ID).queryAs(Circle.class).getFill();
        verifyThat(fill, isColor(Color.WHITE));
    }

    @Test
    void shouldContainWebView() {
        var document = lookup(".web-view").queryAs(WebView.class).getEngine().getDocument();
        assertThat(document).isNull();
    }
}