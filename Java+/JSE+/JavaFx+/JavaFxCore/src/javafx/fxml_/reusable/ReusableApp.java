package javafx.fxml_.reusable;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Objects;

public class ReusableApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        var location = Objects.requireNonNull(getClass().getResource("Root.fxml"));
        var loader = new FXMLLoader(location);
        var listeners = new ArrayList<InitializeAfterLoadingFxml>();
        loader.setControllerFactory(param -> {
            try {
                var instance = Class.forName(param.getName()).getDeclaredConstructor().newInstance();
                if (instance instanceof InitializeAfterLoadingFxml) {
                    listeners.add((InitializeAfterLoadingFxml) instance);
                }
                return instance;
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
        Parent root = loader.load();
        listeners.forEach(InitializeAfterLoadingFxml::initFinally);
        var scene = new Scene(root, Color.LIGHTYELLOW);
        stage.setTitle("Reusable component");
        stage.setScene(scene);
        stage.show();
    }

}