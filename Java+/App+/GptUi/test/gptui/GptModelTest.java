package gptui;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import gptui.storage.GptStorage;
import gptui.storage.GptStorageFilesystem;
import gptui.ui.GptModel;
import gptui.ui.view.GptView;
import gptui.ui.view.GptViewModel;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class GptModelTest extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        var storageFileSystem = new GptStorageFilesystem(Jimfs.newFileSystem(Configuration.unix()));
        var storage = new GptStorage(storageFileSystem);
        var model = new GptModel(storage);
        var viewModel = new GptViewModel(model);
        model.setViewModel(viewModel);
        var view = new GptView(viewModel);
        var scene = new Scene(view, 640, 900);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void shouldContainHeader() {
        verifyThat(".label", hasText("Question history:"));
    }

    @Test
    void shouldContainComboBox() {
        var items = lookup(".combo-box").queryComboBox().getItems();
        assertThat(items).isEmpty();
    }
}