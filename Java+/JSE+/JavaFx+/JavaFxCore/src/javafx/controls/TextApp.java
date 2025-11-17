package javafx.controls;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TextApp extends Application {

    @Override
    public void start(Stage stage) {
        var data = """
                The JavaFX Documentation Project aims to pull together useful information for JavaFX developers
                from all over the web. The project is open source and encourages community participation to ensure
                that the documentation is as highly polished and useful as possible.
                """;
        var text = new Text(data);
        var scene = new Scene(new StackPane(text), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

}