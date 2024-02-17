package javafx.fxml_.reusable;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

class ReusableAppTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        new ReusableApp().start(stage);
    }

    @Test
    void getReusableNodeById() {
        var vbox1 = lookup("#id1").queryAs(VBox.class);
        var vbox2 = lookup("#id2").queryAs(VBox.class);
        assertThat(vbox1.getId()).isEqualTo("id1");
        assertThat(vbox2.getId()).isEqualTo("id2");
    }
}