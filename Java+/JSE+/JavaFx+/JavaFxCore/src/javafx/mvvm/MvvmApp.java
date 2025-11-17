package javafx.mvvm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Example of a Model-View-ViewModel application.<br/>
 * <a href="https://fxdocs.github.io/docs/html5/#_application_structure">Source</a>
 */
public class MvvmApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        var view = new EmploymentRequestView();
        var scene = new Scene(view);
        primaryStage.setTitle("MVVM App");
        primaryStage.setScene(scene);
        primaryStage.setWidth(480);
        primaryStage.setHeight(320);
        primaryStage.show();
    }

}
