package gptui.fxml.controller;

import gptui.format.ThemeHelper;
import gptui.storage.GptStorage;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCodeCombination;

import javax.inject.Inject;

import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCombination.CONTROL_DOWN;

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
        shortAnswerController.setCopyAccelerator(new KeyCodeCombination(DIGIT1, CONTROL_DOWN));
        longAnswerController.setAnswerType(LONG);
        longAnswerController.setCopyAccelerator(new KeyCodeCombination(DIGIT2, CONTROL_DOWN));
        var interactionHistory = storage.readAllInteractions();
        var themeList = themeHelper.interactionsToThemeList(interactionHistory);
        model.setInteractionHistory(interactionHistory);
        model.setThemeList(themeList);
        model.setCurrentInteraction(!interactionHistory.isEmpty() ? interactionHistory.get(0) : null);
        model.setCurrentTheme(!themeList.isEmpty() ? themeList.get(0) : null);
        model.fireInteractionChosenFromHistory(this);
    }
}