package gptui.ui;

import com.google.inject.util.Modules;
import gptui.RootModule;
import gptui.model.clipboard.ClipboardModel;
import gptui.model.question.openai.MockGptApi;
import gptui.model.search.HistorySearchModel;
import gptui.model.state.StateModel;
import gptui.model.storage.StorageModel;
import gptui.model.storage.Theme;
import gptui.view.GptUiApplication;
import gptui.viewmodel.InteractionItem;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import org.assertj.core.api.SoftAssertions;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.ExecutionException;

import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.CONTROL;

public abstract class BaseGptUiTest extends ApplicationTest {
    private final GptUiApplication app = new GptUiApplication(Modules.override(new RootModule()).with(new TestRootModule()));
    protected final StateModel stateModel = app.getGuiceContext().getInstance(StateModel.class);
    protected final MockGptApi gptApi = app.getGuiceContext().getInstance(MockGptApi.class);
    protected final StorageModel storage = app.getGuiceContext().getInstance(StorageModel.class);
    protected final HistorySearchModel search = app.getGuiceContext().getInstance(HistorySearchModel.class);
    protected final ClipboardModel clipboardModel = app.getGuiceContext().getInstance(ClipboardModel.class);
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

    protected Scene scene() {
        return theme().comboBox().getScene();
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

    void verifyWebViewBody(SoftAssertions soft, String as, WebView webView, String expContent) {
        interact(() -> soft.assertThat(extractWebViewContent(webView)).as(as)
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
        public Label label() {
            return lookup("#historyLabel").queryAs(Label.class);
        }

        public ComboBox<InteractionItem> comboBox() {
            return lookup("#historyComboBox").queryComboBox();
        }

        public Button deleteButton() {
            return lookup("#historyDeleteButton").queryButton();
        }
    }

    protected class ThemeInfo {
        public Label label() {
            return lookup("#themeLabel").queryAs(Label.class);
        }

        public ComboBox<Theme> comboBox() {
            return lookup("#themeComboBox").queryComboBox();
        }

        public Node comboBoxNarrow() {
            return comboBox().lookup(".arrow-button");
        }

        public Button addThemeButton() {
            return lookup("#addButton").queryButton();
        }

        public CheckBox filterHistoryCheckBox() {
            return lookup("#filterHistoryCheckBox").queryAs(CheckBox.class);
        }
    }

    protected class QuestionInfo {
        public Label label() {
            return lookup("#questionLabel").queryAs(Label.class);
        }

        public TextArea textArea() {
            return lookup("#questionTextArea").queryAs(TextArea.class);
        }

        public Button questionButton() {
            return lookup("#questionButton").queryButton();
        }

        public Button definitionButton() {
            return lookup("#definitionButton").queryButton();
        }

        public Button grammarButton() {
            return lookup("#grammarButton").queryButton();
        }

        public Button factButton() {
            return lookup("#factButton").queryButton();
        }

        public Button regenerateButton() {
            return lookup("#regenerateButton").queryButton();
        }
    }

    protected class AnswerInfo {
        private final String tag;

        public AnswerInfo(String tag) {
            this.tag = tag;
        }

        public Label label() {
            return lookup(tag + " #answerLabel").queryAs(Label.class);
        }

        public Button copyButton() {
            return lookup(tag + " #copyButton").queryButton();
        }

        public Button regenerateButton() {
            return lookup(tag + " #regenerateButton").queryButton();
        }

        public WebView webView() {
            return lookup(tag + " #webView").queryAs(WebView.class);
        }

        public Circle circle() {
            return lookup(tag + " #statusCircle").queryAs(Circle.class);
        }

        public Text temperatureText() {
            return lookup(tag + " #temperatureText").queryText();
        }

        public Spinner<Integer> temperatureSpinner() {
            return lookup(tag + " #temperatureSpinner").query();
        }

        public Node temperatureIncrementButton() {
            return lookup(tag + " #temperatureSpinner .increment-arrow-button").query();
        }

        public Node temperatureDecrementButton() {
            return lookup(tag + " #temperatureSpinner .decrement-arrow-button").query();
        }
    }
}