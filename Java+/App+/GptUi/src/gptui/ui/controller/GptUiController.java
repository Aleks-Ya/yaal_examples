package gptui.ui.controller;

import gptui.format.ThemeHelper;
import gptui.storage.GptStorage;
import javafx.fxml.FXML;

import javax.inject.Inject;

import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;

class GptUiController extends BaseController {
    @Inject
    private GptStorage storage;
    @Inject
    private ThemeHelper themeHelper;
    @FXML
    private AnswerController longAnswerController;
    @FXML
    private AnswerController shortAnswerController;

    @Override
    protected void initializeChild() {
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