package gptui.view;

import gptui.viewmodel.AnswerVM;
import gptui.viewmodel.GptUiVM;
import gptui.viewmodel.ViewModelModule;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GptUiController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);
    @Inject
    private GptUiVM vm;
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
    @Named(ViewModelModule.GRAMMAR)
    private AnswerVM grammarAnswerVM;
    @Inject
    @Named(ViewModelModule.SHORT)
    private AnswerVM shortAnswerVM;
    @Inject
    @Named(ViewModelModule.LONG)
    private AnswerVM longAnswerVM;
    @Inject
    @Named(ViewModelModule.GCP)
    private AnswerVM gcpAnswerVM;

    @Override
    protected void initialize() {
        log.trace("initialize");
        grammarAnswerController.setVm(grammarAnswerVM);
        shortAnswerController.setVm(shortAnswerVM);
        longAnswerController.setVm(longAnswerVM);
        gcpAnswerController.setVm(gcpAnswerVM);
        vm.initialize();
    }
}