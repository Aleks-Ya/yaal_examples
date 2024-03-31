package gptui.viewmodel.question;

import gptui.model.storage.InteractionType;

public interface QuestionVmMediator {
    void displayCurrentInteraction();

    void focusOnQuestionAndSelect();

    void pasteQuestionFromClipboard();

    void createNewInteractionAndRequestAnswers(InteractionType interactionType);
}
