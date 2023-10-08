package gptui.fxml;

import com.google.common.jimfs.Jimfs;
import gptui.MockGptApi;
import gptui.format.ClipboardHelper;
import gptui.storage.GptStorage;
import gptui.storage.GptStorageFilesystem;
import gptui.storage.GptStorageImpl;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.ExecutionException;

import static com.google.common.jimfs.Configuration.unix;
import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.CONTROL;
import static org.assertj.core.api.Assertions.assertThat;

abstract class BaseGptUiTest extends ApplicationTest {
    protected final Model model = new Model();
    protected final MockGptApi gptApi = new MockGptApi();
    protected final GptStorage storage = new GptStorageImpl(new GptStorageFilesystem(Jimfs.newFileSystem(unix())));

    @Override
    public void start(Stage stage) throws Exception {
        new GptUiFXML(new TestGuiceModule(model, gptApi, storage)).start(stage);
    }

    protected Label getInteractionHistoryLabel() {
        return lookup("#interactionHistoryLabel").queryAs(Label.class);
    }

    protected ComboBox<String> getInteractionHistoryComboBox() {
        return lookup("#interactionHistoryComboBox").queryComboBox();
    }

    protected Label getThemeLabel() {
        return lookup("#themeLabel").queryAs(Label.class);
    }

    protected ComboBox<String> getThemeComboBox() {
        return lookup("#themeComboBox").queryComboBox();
    }

    protected Label getQuestionLabel() {
        return lookup("#questionLabel").queryAs(Label.class);
    }

    protected TextArea getQuestionTextArea() {
        return lookup("#questionTextArea").queryAs(TextArea.class);
    }

    protected Button getQuestionSendButton() {
        return lookup("#sendQuestionButton").queryButton();
    }

    protected Label getQuestionCorrectnessLabel() {
        return lookup("#questionCorrectnessLabel").queryAs(Label.class);
    }

    protected WebView getQuestionCorrectnessWebView() {
        return lookup("#questionCorrectnessWebView").queryAs(WebView.class);
    }

    protected Label getShortAnswerLabel() {
        return lookup("#shortAnswer #answerLabel").queryAs(Label.class);
    }

    protected WebView getShortAnswerWebView() {
        return lookup("#shortAnswer #webView").queryAs(WebView.class);
    }

    protected Button getShortAnswerCopyButton() {
        return lookup("#shortAnswer #copyButton").queryButton();
    }

    protected Circle getShortAnswerCircle() {
        return lookup("#shortAnswer #statusCircle").queryAs(Circle.class);
    }

    protected Label getLongAnswerLabel() {
        return lookup("#longAnswer #answerLabel").queryAs(Label.class);
    }

    protected WebView getLongAnswerWebView() {
        return lookup("#longAnswer #webView").queryAs(WebView.class);
    }

    protected Button getLongAnswerCopyButton() {
        return lookup("#longAnswer #copyButton").queryButton();
    }

    protected Circle getLongAnswerCircle() {
        return lookup("#longAnswer #statusCircle").queryAs(Circle.class);
    }

    protected String getWebViewContent(WebView webView) {
        return (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
    }

    protected FxRobot overWrite(String text) {
        return press(CONTROL, A).release(A, CONTROL).write(text);
    }

    protected void verifyWebViewBody(WebView webView, String expContent) {
        interact(() -> assertThat(getWebViewContent(webView))
                .isEqualTo("<html><head></head><body>" + expContent + "</body></html>"));
    }

    protected void verifyHtmlClipboardContent(String expContent) {
        interact(() -> assertThat(ClipboardHelper.getTextFromClipboard())
                .isEqualTo("<html><head></head><body>" + expContent + "</body></html>"));
    }

    protected void executeSyncInFxThread(Runnable runnable) {
        try {
            WaitForAsyncUtils.asyncFx(runnable).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}