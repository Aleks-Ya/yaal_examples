package javafx.fxml_.mvc_multi_fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static java.util.Objects.requireNonNull;

public class MvcMultiFxmlApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        var model = new MvcMultiModel();
        var loader = new FXMLLoader();
        loader.setLocation(requireNonNull(getClass().getResource("parent.fxml")));
        loader.setControllerFactory(param -> {
            if (param == ParentController.class) {
                return new ParentController(model);
            } else if (param == DriverController.class) {
                return new DriverController(model);
            } else if (param == CarController.class) {
                return new CarController(model);
            } else {
                throw new RuntimeException("No controller for " + param);
            }
        });
        Parent root = loader.load();
        var scene = new Scene(root, Color.LIGHTYELLOW);
        stage.setTitle("JavaFX MVC with multi FXML files");
        stage.setScene(scene);
        stage.show();
        model.modelUpdated();
    }

}