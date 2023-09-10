package richtext;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fxmisc.richtext.StyleClassedTextArea;

public class StyleClassedTextAreaApp extends Application {
    @Override
    public void start(Stage stage) {
        var textArea2 = bigTextArea();
        var scene = new Scene(new VBox(textArea2), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static StyleClassedTextArea bigTextArea() {
        var data = "The JavaFX Documentation Project aims to pull together useful information for JavaFX developers " +
                "from all over the web. The project is open source and encourages community participation to ensure " +
                "that the documentation is as highly polished and useful as possible.";
        var styleClassedTextArea = new StyleClassedTextArea();
        styleClassedTextArea.insertText(0, data);
        styleClassedTextArea.setWrapText(true);
        return styleClassedTextArea;
    }

    public static void main(String[] args) {
        launch();
    }
}