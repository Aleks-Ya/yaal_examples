package gptui.ui.controller;

import gptui.storage.GptStorage;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;

public class GptUiController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);
    @Inject
    private GptStorage storage;
    @Inject
    private ThemeHelper themeHelper;
    @FXML
    @SuppressWarnings("unused")
    private AnswerController grammarAnswerController;
    @FXML
    @SuppressWarnings("unused")
    private AnswerController longAnswerController;
    @FXML
    @SuppressWarnings("unused")
    private AnswerController shortAnswerController;

    @Override
    protected void initializeChild() {
        log.trace("initializeChild");
        grammarAnswerController.setAnswerType(GRAMMAR);
        shortAnswerController.setAnswerType(SHORT);
        longAnswerController.setAnswerType(LONG);
        var history = storage.readAllInteractions();
        var themeList = themeHelper.interactionsToThemeList(history);
        model.setHistory(history);
        model.setCurrentInteractionId(!history.isEmpty() ? history.getFirst().id() : null);
        model.setCurrentTheme(!themeList.isEmpty() ? themeList.getFirst() : null);
        model.fireInteractionChosenFromHistory(this);
    }
}