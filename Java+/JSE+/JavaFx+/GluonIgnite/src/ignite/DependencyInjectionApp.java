package ignite;

import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.AbstractModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DependencyInjectionApp extends Application {
    private final GuiceContext context = new GuiceContext(this, () -> List.of(new GuiceModule()));
    @Inject
    private FXMLLoader fxmlLoader;
    @Inject
    private IgniteController igniteController;

    @Override
    public void start(Stage primaryStage) throws IOException {
        assert fxmlLoader != null;
        assert igniteController != null;
        context.init();
        fxmlLoader.setLocation(Objects.requireNonNull(getClass().getResource("ignite.fxml")));
        Parent view = fxmlLoader.load();

        primaryStage.setTitle("Guice Example");
        primaryStage.setScene(new Scene(view));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(NumberService.class).to(NumberServiceImpl.class);
    }
}