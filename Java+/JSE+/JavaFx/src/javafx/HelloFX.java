package javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * <a href="https://github.com/openjfx/samples/blob/master/HelloFX/Gradle/hellofx/src/main/java/HelloFX.java">Source</a>
 */
public class HelloFX extends Application {

    @Override
    public void start(Stage stage) {
        var javaVersion = System.getProperty("java.version");
        var javafxVersion = System.getProperty("javafx.version");
        var l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}