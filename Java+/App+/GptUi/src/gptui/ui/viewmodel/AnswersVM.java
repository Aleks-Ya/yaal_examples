package gptui.ui.viewmodel;

import gptui.ui.model.StateModel;
import gptui.ui.model.event.EventModel;
import gptui.ui.model.event.EventSource;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static gptui.storage.AnswerType.GCP;
import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;

public class AnswersVM implements EventSource {
    private static final Logger log = LoggerFactory.getLogger(AnswersVM.class);
    @Inject
    private StateModel stateModel;
    @Inject
    private EventModel eventModel;

    public void initialize() {
        log.trace("initialize");
        var history = stateModel.getAllInteractions();
        stateModel.chooseFirstInteractionAsCurrent();
        if (!history.isEmpty()) {
            var currentInteraction = stateModel.getCurrentInteraction();
            currentInteraction.getAnswer(GRAMMAR).ifPresent(answer -> stateModel.getTemperatures().setTemperature(GRAMMAR, answer.temperature()));
            currentInteraction.getAnswer(SHORT).ifPresent(answer -> stateModel.getTemperatures().setTemperature(SHORT, answer.temperature()));
            currentInteraction.getAnswer(LONG).ifPresent(answer -> stateModel.getTemperatures().setTemperature(LONG, answer.temperature()));
            currentInteraction.getAnswer(GCP).ifPresent(answer -> stateModel.getTemperatures().setTemperature(GCP, answer.temperature()));
        }
        stateModel.setFirstThemeAsCurrent();
        eventModel.fire().interactionChosenFromHistory(this);
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}

