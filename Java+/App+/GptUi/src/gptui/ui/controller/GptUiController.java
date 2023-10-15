package gptui.ui.controller;

import gptui.format.ThemeHelper;
import gptui.storage.GptStorage;
import javafx.fxml.FXML;

import javax.inject.Inject;

import static gptui.storage.AnswerType.*;

public class GptUiController extends BaseController {
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
        grammarAnswerController.setAnswerType(GRAMMAR);
        shortAnswerController.setAnswerType(SHORT);
        longAnswerController.setAnswerType(LONG);
        var history = storage.readAllInteractions();
        var themeList = themeHelper.interactionsToThemeList(history);
        model.setHistory(history);
        model.setThemeList(themeList);
        model.setCurrentInteraction(!history.isEmpty() ? history.get(0) : null);
        model.setCurrentTheme(!themeList.isEmpty() ? themeList.get(0) : null);
        model.fireInteractionChosenFromHistory(this);
    }
}