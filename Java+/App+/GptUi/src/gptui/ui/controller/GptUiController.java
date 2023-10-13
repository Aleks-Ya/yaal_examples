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
        var interactionHistory = storage.readAllInteractions();
        var themeList = themeHelper.interactionsToThemeList(interactionHistory);
        model.setInteractionHistory(interactionHistory);
        model.setThemeList(themeList);
        model.setCurrentInteraction(!interactionHistory.isEmpty() ? interactionHistory.get(0) : null);
        model.setCurrentTheme(!themeList.isEmpty() ? themeList.get(0) : null);
        model.fireInteractionChosenFromHistory(this);
    }
}