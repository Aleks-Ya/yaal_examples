package gptui.fxml;

import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.AbstractModule;
import gptui.gpt.GptApi;
import gptui.gpt.GptApiImpl;
import gptui.storage.GptStorage;
import gptui.storage.GptStorageFilesystem;
import gptui.storage.GptStorageImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.Objects;

public class GptUiFXML extends Application {
    private final GuiceContext context = new GuiceContext(this, () -> List.of(new GuiceModule()));
    @Inject
    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws Exception {
        context.init();
        fxmlLoader.setLocation(Objects.requireNonNull(getClass().getResource("GptUi.fxml")));
        Parent root = fxmlLoader.load();
        var scene = new Scene(root, Color.LIGHTYELLOW);
        stage.setTitle("Reusable component");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GptApi.class).to(GptApiImpl.class);
        bind(GptStorage.class).toInstance(new GptStorageImpl(new GptStorageFilesystem(FileSystems.getDefault())));
    }
}