package gptui.view;

import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.Module;
import gptui.RootModule;
import gptui.viewmodel.GptUiApplicationVM;
import jakarta.inject.Inject;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class GptUiApplication extends Application {
    private static final Logger log = LoggerFactory.getLogger(GptUiApplication.class);
    private final GuiceContext context;
    @Inject
    private FXMLLoader fxmlLoader;
    @Inject
    private GptUiApplicationVM vm;

    public GptUiApplication() {
        this(new RootModule());
    }

    public GptUiApplication(Module module) {
        context = new GuiceContext(this, () -> List.of(module));
        context.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            var scene = createScene();
            var version = vm.getAppVersion();
            createStage(stage, version, scene);
            vm.stageShowed(scene.getAccelerators());
        } catch (Exception e) {
            log.error("Starting application error", e);
            throw e;
        }
    }

    private Scene createScene() throws IOException {
        fxmlLoader.setLocation(vm.getFxmlLocation());
        Parent root = fxmlLoader.load();
        return new Scene(root, Color.LIGHTYELLOW);
    }

    private void createStage(Stage stage, String version, Scene scene) {
        stage.setTitle("GPT-4 Question Client v" + version);
        stage.setScene(scene);
        stage.setMaximized(true);
        var applicationIcon = vm.getApplicationIcon();
        stage.getIcons().add(applicationIcon);
        stage.show();
    }

    public GuiceContext getGuiceContext() {
        return context;
    }
}
