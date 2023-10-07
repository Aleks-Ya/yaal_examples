package javafx.fxml_.mvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static java.util.Objects.requireNonNull;

public class MvcFXML extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        var model = new MvcModel();
        var loader = new FXMLLoader();
        loader.setLocation(requireNonNull(getClass().getResource("mvc.fxml")));
        loader.setControllerFactory(param -> new MvcController(model));
        Parent root = loader.load();
        var scene = new Scene(root, Color.LIGHTYELLOW);
        stage.setTitle("MyShapesApp with JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}