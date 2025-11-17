package javafx.shape;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Random;

public class CircleApp extends Application {

    @Override
    public void start(Stage stage) {
        var circle = simpleCircle();
        var button = buttonWithCircle();
        var box = new VBox(circle, button);
        var scene = new Scene(box, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static Circle simpleCircle() {
        var circle = new Circle();
        circle.setRadius(50);
        circle.setFill(Color.GREEN);
        return circle;
    }

    private static Button buttonWithCircle() {
        var circle = new Circle(10, Color.RED);
        var button = new Button("Click Me");
        button.setGraphic(circle);
        var random = new Random();
        button.setOnAction(_ ->
                circle.setFill(Color.color(random.nextDouble(1), random.nextDouble(1), random.nextDouble(1)))
        );
        return button;
    }

}