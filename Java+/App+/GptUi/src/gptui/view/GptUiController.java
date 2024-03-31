package gptui.view;

import gptui.viewmodel.answer.AnswerVmController;
import gptui.viewmodel.answer.AnswerVmModule;
import gptui.viewmodel.ui.GptUiVmController;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GptUiController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);
    @Inject
    private GptUiVmController vm;
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
    @Inject
    @Named(AnswerVmModule.GRAMMAR)
    private AnswerVmController grammarAnswerVM;
    @Inject
    @Named(AnswerVmModule.SHORT)
    private AnswerVmController shortAnswerVM;
    @Inject
    @Named(AnswerVmModule.LONG)
    private AnswerVmController longAnswerVM;
    @Inject
    @Named(AnswerVmModule.GCP)
    private AnswerVmController gcpAnswerVM;

    @Override
    protected void initialize() {
        log.trace("initialize");
        grammarAnswerController.initializeController(grammarAnswerVM);
        shortAnswerController.initializeController(shortAnswerVM);
        longAnswerController.initializeController(longAnswerVM);
        gcpAnswerController.initializeController(gcpAnswerVM);
        vm.initialize();
    }
}