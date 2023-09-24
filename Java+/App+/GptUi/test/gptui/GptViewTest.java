package gptui;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import gptui.storage.GptStorage;
import gptui.storage.GptStorageFilesystem;
import gptui.ui.GptModel;
import gptui.ui.view.GptView;
import gptui.ui.view.GptViewModel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ComboBoxMatchers.hasItems;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class GptViewTest extends ApplicationTest {
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

    public Label getInteractionHistoryLabel() {
        return lookup("##InteractionHistoryLabel").queryAs(Label.class);
    }

    public ComboBox<String> getInteractionHistoryComboBox() {
        return lookup("#InteractionHistoryComboBox").queryComboBox();
    }

    public Label getThemeLabel() {
        return lookup("#ThemeLabel").queryAs(Label.class);
    }

    public ComboBox<String> getThemeComboBox() {
        return lookup("#ThemeComboBox").queryComboBox();
    }

    public Label getQuestionPaneLabel() {
        return lookup("#QuestionPaneLabel").queryAs(Label.class);
    }

    public TextArea getQuestionPaneTextArea() {
        return lookup("#QuestionPaneTextArea").queryAs(TextArea.class);
    }

    public Button getQuestionPaneButton() {
        return lookup("#QuestionPaneButton").queryButton();
    }

    @Test
    void shouldContainInteractionHistoryPane() {
        verifyThat(getThemeLabel(), hasText("Theme:"));
        verifyThat(getInteractionHistoryComboBox(), hasItems(0));
    }

    @Test
    void shouldContainThemePane() {
        verifyThat(getInteractionHistoryLabel(), hasText("Question history:"));
        verifyThat(getThemeComboBox(), hasItems(0));
    }

    @Test
    void shouldContainQuestionPane() {
        verifyThat(getQuestionPaneLabel(), hasText("Question:"));
        verifyThat(getQuestionPaneTextArea().getText(), nullValue());
        verifyThat(getQuestionPaneButton().getText(), equalTo("Send"));
    }

}