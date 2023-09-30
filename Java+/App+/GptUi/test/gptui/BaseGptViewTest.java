package gptui;

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
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import static com.google.common.jimfs.Configuration.unix;
import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.CONTROL;
import static org.assertj.core.api.Assertions.assertThat;

abstract class BaseGptViewTest extends ApplicationTest {
    protected final MockGptApi gptApi = new MockGptApi();
    protected final GptStorage storage = new GptStorage(new GptStorageFilesystem(Jimfs.newFileSystem(unix())));

    @Override
    public void start(Stage stage) {
        var model = new GptModel(storage, gptApi);
        var viewModel = new GptViewModel(model);
        model.setViewModel(viewModel);
        var view = new GptView(viewModel);
        var scene = new Scene(view, 640, 900);
        stage.setScene(scene);
        stage.show();
    }

    protected Label getInteractionHistoryLabel() {
        return lookup("##InteractionHistoryLabel").queryAs(Label.class);
    }

    protected ComboBox<String> getInteractionHistoryComboBox() {
        return lookup("#InteractionHistoryComboBox").queryComboBox();
    }

    protected Label getThemeLabel() {
        return lookup("#ThemeLabel").queryAs(Label.class);
    }

    protected ComboBox<String> getThemeComboBox() {
        return lookup("#ThemeComboBox").queryComboBox();
    }

    protected Label getQuestionPaneLabel() {
        return lookup("#QuestionPaneLabel").queryAs(Label.class);
    }

    protected TextArea getQuestionPaneTextArea() {
        return lookup("#QuestionPaneTextArea").queryAs(TextArea.class);
    }

    protected Button getSendButton() {
        return lookup("#QuestionPaneButton").queryButton();
    }

    protected WebView getQuestionCorrectnessWebView() {
        return lookup("#QuestionCorrectnessWebView").queryAs(WebView.class);
    }

    protected WebView getShortAnswerWebView() {
        return lookup("#ShortAnswerPane .web-view").queryAs(WebView.class);
    }

    protected Circle getShortAnswerCircle() {
        return lookup("#ShortAnswerPane #status-circle").queryAs(Circle.class);
    }

    protected Circle getLongAnswerCircle() {
        return lookup("#LongAnswerPane #status-circle").queryAs(Circle.class);
    }

    protected WebView getLongAnswerWebView() {
        return lookup("#LongAnswerPane .web-view").queryAs(WebView.class);
    }

    protected String getWebViewContent(WebView webView) {
        return (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
    }

    protected FxRobot overWrite(String text) {
        return press(CONTROL).press(A).release(A).release(CONTROL).write(text);
    }

    protected void verifyWebViewBody(WebView webView, String expContent) {
        interact(() -> assertThat(getWebViewContent(webView))
                .isEqualTo("<html><head></head><body>" + expContent + "</body></html>"));
    }
}