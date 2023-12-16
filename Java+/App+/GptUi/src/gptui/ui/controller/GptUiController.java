package gptui.ui.controller;

import gptui.storage.InteractionStorage;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static gptui.storage.AnswerType.GCP;
import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;

public class GptUiController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);
    @Inject
    private InteractionStorage storage;
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
    protected void initializeChild() {
        log.trace("initializeChild");
        grammarAnswerController.setAnswerType(GRAMMAR);
        shortAnswerController.setAnswerType(SHORT);
        longAnswerController.setAnswerType(LONG);
        gcpAnswerController.setAnswerType(GCP);
        var history = storage.readAllInteractions();
        model.setCurrentInteractionId(!history.isEmpty() ? history.getFirst().id() : null);
        if (!history.isEmpty()) {
            var currentInteraction = storage.readInteraction(model.getCurrentInteractionId()).orElseThrow();
            currentInteraction.getAnswer(GRAMMAR).ifPresent(answer -> model.getTemperatures().setTemperature(GRAMMAR, answer.temperature()));
            currentInteraction.getAnswer(SHORT).ifPresent(answer -> model.getTemperatures().setTemperature(SHORT, answer.temperature()));
            currentInteraction.getAnswer(LONG).ifPresent(answer -> model.getTemperatures().setTemperature(LONG, answer.temperature()));
            currentInteraction.getAnswer(GCP).ifPresent(answer -> model.getTemperatures().setTemperature(GCP, answer.temperature()));
        }
        model.setCurrentTheme(!storage.getThemes().isEmpty() ? storage.getThemes().getFirst() : null);
        model.fire().interactionsUpdated(this);
        model.fire().interactionChosenFromHistory(this);
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}