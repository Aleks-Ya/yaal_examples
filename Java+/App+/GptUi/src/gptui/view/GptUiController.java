package gptui.view;

import gptui.viewmodel.AnswersVM;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static gptui.model.storage.AnswerType.GCP;
import static gptui.model.storage.AnswerType.GRAMMAR;
import static gptui.model.storage.AnswerType.LONG;
import static gptui.model.storage.AnswerType.SHORT;

public class GptUiController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);
    @Inject
    private AnswersVM vm;
    @FXML
    @SuppressWarnings("unused")
    private AnswerController grammarAnswerController;
    @FXML
    @SuppressWarnings("unused")
    private AnswerController longAnswerController;
    @FXML
    @SuppressWarnings("unused")
    private AnswerController shortAnswerController;
    @FXML
    @SuppressWarnings("unused")
    private AnswerController gcpAnswerController;

    @Override
    protected void initialize() {
        log.trace("initialize");
        grammarAnswerController.setAnswerType(GRAMMAR);
        shortAnswerController.setAnswerType(SHORT);
        longAnswerController.setAnswerType(LONG);
        gcpAnswerController.setAnswerType(GCP);
        vm.initialize();
    }
}