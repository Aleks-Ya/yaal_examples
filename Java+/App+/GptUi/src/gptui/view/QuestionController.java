package gptui.view;

import gptui.viewmodel.QuestionVM;
import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestionController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
    @Inject
    private QuestionVM vm;
    @FXML
    private TextArea questionTextArea;

    @FXML
    void sendQuestion(ActionEvent ignoredEvent) {
        log.debug("sendQuestion");
        vm.onSendQuestionClick();
    }

    @FXML
    void sendDefinition(ActionEvent ignoredEvent) {
        log.debug("sendDefinition");
        vm.onSendDefinitionClick();
    }

    @FXML
    void sendGrammar(ActionEvent ignoredEvent) {
        log.debug("sendGrammar");
        vm.onSendGrammarClick();
    }

    @FXML
    public void sendFact(ActionEvent ignoredEvent) {
        log.debug("sendFact");
        vm.onSendFactClick();
    }

    @FXML
    void onRegenerateButtonClick(ActionEvent ignoredEvent) {
        log.trace("onRegenerateButtonClick");
        vm.onRegenerateButtonClick();
    }

    @FXML
    void keyTypedQuestionTextArea(KeyEvent ignoredEvent) {
        log.trace("keyTypedQuestionTextArea");
        vm.onKeyTypedQuestionTextArea();
    }

    @Override
    protected void initialize() {
        vm.properties.questionTaText.bindBidirectional(questionTextArea.textProperty());
        vm.properties.questionTaStyle.bindBidirectional(questionTextArea.styleProperty());
        vm.properties.questionTaFocused.addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        Platform.runLater(() -> questionTextArea.requestFocus());
                    }
                }
        );
        vm.properties.questionTaSelectAll.addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        Platform.runLater(() -> questionTextArea.selectAll());
                    }
                }
        );
        vm.properties.questionTaPositionCaretToEnd.addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        Platform.runLater(() -> questionTextArea.positionCaret(questionTextArea.getText().length()));
                    }
                }
        );
    }
}

