package gptui.ui;

import com.google.inject.util.Modules;
import gptui.gpt.openai.MockGptApi;
import gptui.storage.GptStorage;
import gptui.storage.Interaction;
import gptui.ui.controller.ClipboardHelper;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
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
    private final HistoryInfo history = new HistoryInfo();
    private final ThemeInfo theme = new ThemeInfo();
    private final QuestionInfo question = new QuestionInfo();
    private final AnswerInfo answerGrammar = new AnswerInfo("#grammarAnswer");
    private final AnswerInfo answerShort = new AnswerInfo("#shortAnswer");
    private final AnswerInfo answerLong = new AnswerInfo("#longAnswer");
    private final AnswerInfo answerGcp = new AnswerInfo("#gcpAnswer");

    @Override
    public void start(Stage stage) throws Exception {
        app.start(stage);
    }

    protected HistoryInfo history() {
        return history;
    }

    protected ThemeInfo theme() {
        return theme;
    }

    protected QuestionInfo question() {
        return question;
    }

    protected AnswerInfo grammarAnswer() {
        return answerGrammar;
    }

    protected AnswerInfo shortAnswer() {
        return answerShort;
    }

    protected AnswerInfo longAnswer() {
        return answerLong;
    }

    protected AnswerInfo gcpAnswer() {
        return answerGcp;
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

    protected class HistoryInfo {
        protected Label label() {
            return lookup("#historyLabel").queryAs(Label.class);
        }

        protected ComboBox<Interaction> comboBox() {
            return lookup("#historyComboBox").queryComboBox();
        }

        protected Button deleteButton() {
            return lookup("#historyDeleteButton").queryButton();
        }
    }

    protected class ThemeInfo {
        protected Label label() {
            return lookup("#themeLabel").queryAs(Label.class);
        }

        protected ComboBox<String> comboBox() {
            return lookup("#themeComboBox").queryComboBox();
        }

        protected CheckBox filterHistoryCheckBox() {
            return lookup("#filterHistoryCheckBox").queryAs(CheckBox.class);
        }
    }

    protected class QuestionInfo {
        protected Label label() {
            return lookup("#questionLabel").queryAs(Label.class);
        }

        protected TextArea textArea() {
            return lookup("#questionTextArea").queryAs(TextArea.class);
        }

        protected Button questionButton() {
            return lookup("#questionButton").queryButton();
        }

        protected Button definitionButton() {
            return lookup("#definitionButton").queryButton();
        }

        protected Button grammarButton() {
            return lookup("#grammarButton").queryButton();
        }

        protected Button factButton() {
            return lookup("#factButton").queryButton();
        }

        protected Button regenerateButton() {
            return lookup("#regenerateButton").queryButton();
        }
    }

    protected class AnswerInfo {
        private final String tag;

        protected AnswerInfo(String tag) {
            this.tag = tag;
        }

        protected Label label() {
            return lookup(tag + " #answerLabel").queryAs(Label.class);
        }

        protected Button copyButton() {
            return lookup(tag + " #copyButton").queryButton();
        }

        protected Button regenerateButton() {
            return lookup(tag + " #regenerateButton").queryButton();
        }

        protected WebView webView() {
            return lookup(tag + " #webView").queryAs(WebView.class);
        }

        protected Circle circle() {
            return lookup(tag + " #statusCircle").queryAs(Circle.class);
        }

        protected Text temperatureText() {
            return lookup(tag + " #temperatureText").queryText();
        }

        protected Spinner<Integer> temperatureSpinner() {
            return lookup(tag + " #temperatureSpinner").query();
        }

        protected Node temperatureIncrementButton() {
            return lookup(tag + " #temperatureSpinner .increment-arrow-button").query();
        }

        protected Node temperatureDecrementButton() {
            return lookup(tag + " #temperatureSpinner .decrement-arrow-button").query();
        }
    }
}