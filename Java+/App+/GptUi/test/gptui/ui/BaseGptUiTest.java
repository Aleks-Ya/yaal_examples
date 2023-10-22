package gptui.ui;

import com.google.inject.util.Modules;
import gptui.gpt.openai.MockGptApi;
import gptui.storage.GptStorage;
import gptui.storage.Interaction;
import gptui.ui.controller.ClipboardHelper;
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

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.CONTROL;
import static org.assertj.core.api.Assertions.assertThat;

abstract class BaseGptUiTest extends ApplicationTest {
    private final GptUiApplication app = new GptUiApplication(Modules.override(new RootModule()).with(new TestRootModule()));
    protected final Model model = app.getGuiceContext().getInstance(Model.class);
    protected final MockGptApi gptApi = app.getGuiceContext().getInstance(MockGptApi.class);
    protected final GptStorage storage = app.getGuiceContext().getInstance(GptStorage.class);
    protected final ClipboardHelper clipboardHelper = app.getGuiceContext().getInstance(ClipboardHelper.class);

    @Override
    public void start(Stage stage) throws Exception {
        app.start(stage);
    }

    protected Label getHistoryLabel() {
        return lookup("#historyLabel").queryAs(Label.class);
    }

    protected ComboBox<Interaction> getHistoryComboBox() {
        return lookup("#historyComboBox").queryComboBox();
    }

    protected Button getHistoryDeleteButton() {
        return lookup("#historyDeleteButton").queryButton();
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
        return lookup("#questionButton").queryButton();
    }

    protected Button getDefinitionSendButton() {
        return lookup("#definitionButton").queryButton();
    }

    protected Button getGrammarSendButton() {
        return lookup("#grammarButton").queryButton();
    }

    protected Button getFactSendButton() {
        return lookup("#factButton").queryButton();
    }

    protected Button getRegenerateButton() {
        return lookup("#regenerateButton").queryButton();
    }

    protected Label getAnswerGrammarLabel() {
        return lookup("#grammarAnswer #answerLabel").queryAs(Label.class);
    }

    protected Button getAnswerGrammarCopyButton() {
        return lookup("#grammarAnswer #copyButton").queryButton();
    }

    protected Button getAnswerGrammarRegenerateButton() {
        return lookup("#grammarAnswer #regenerateButton").queryButton();
    }

    protected WebView getAnswerGrammarWebView() {
        return lookup("#grammarAnswer #webView").queryAs(WebView.class);
    }

    protected Circle getAnswerGrammarCircle() {
        return lookup("#grammarAnswer #statusCircle").queryAs(Circle.class);
    }

    protected Label getAnswerShortLabel() {
        return lookup("#shortAnswer #answerLabel").queryAs(Label.class);
    }

    protected WebView getAnswerShortWebView() {
        return lookup("#shortAnswer #webView").queryAs(WebView.class);
    }

    protected Button getAnswerShortCopyButton() {
        return lookup("#shortAnswer #copyButton").queryButton();
    }

    protected Button getAnswerShortRegenerateButton() {
        return lookup("#shortAnswer #regenerateButton").queryButton();
    }

    protected Circle getAnswerShortCircle() {
        return lookup("#shortAnswer #statusCircle").queryAs(Circle.class);
    }

    protected Label getAnswerLongLabel() {
        return lookup("#longAnswer #answerLabel").queryAs(Label.class);
    }

    protected Label getAnswerGcpLabel() {
        return lookup("#gcpAnswer #answerLabel").queryAs(Label.class);
    }

    protected WebView getAnswerLongWebView() {
        return lookup("#longAnswer #webView").queryAs(WebView.class);
    }

    protected WebView getAnswerGcpWebView() {
        return lookup("#gcpAnswer #webView").queryAs(WebView.class);
    }

    protected Button getAnswerLongCopyButton() {
        return lookup("#longAnswer #copyButton").queryButton();
    }

    protected Button getAnswerGcpCopyButton() {
        return lookup("#gcpAnswer #copyButton").queryButton();
    }

    protected Button getAnswerLongRegenerateButton() {
        return lookup("#longAnswer #regenerateButton").queryButton();
    }

    protected Button getAnswerGcpRegenerateButton() {
        return lookup("#gcpAnswer #regenerateButton").queryButton();
    }

    protected Circle getAnswerLongCircle() {
        return lookup("#longAnswer #statusCircle").queryAs(Circle.class);
    }

    protected Circle getAnswerGcpCircle() {
        return lookup("#gcpAnswer #statusCircle").queryAs(Circle.class);
    }

    private String extractWebViewContent(WebView webView) {
        return (String) webView.getEngine().executeScript("document.documentElement.outerHTML");
    }

    @SuppressWarnings("UnusedReturnValue")
    protected FxRobot overWrite(String text) {
        return press(CONTROL, A).release(A, CONTROL).write(text);
    }

    protected void verifyWebViewBody(WebView webView, String expContent) {
        interact(() -> assertThat(extractWebViewContent(webView))
                .isEqualTo("<html><head></head><body>" + expContent + "</body></html>"));
    }

    protected void verifyHtmlClipboardContent(String expContent) {
        interact(() -> assertThat(clipboardHelper.getTextFromClipboard())
                .isEqualTo("<html><head></head><body>" + expContent + "</body></html>"));
    }

    protected void executeSyncInFxThread(Runnable runnable) {
        try {
            WaitForAsyncUtils.asyncFx(runnable).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    protected WindowAssertion assertion() {
        return WindowAssertion.builder().app(this);
    }

}