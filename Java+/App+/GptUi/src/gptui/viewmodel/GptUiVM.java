package gptui.viewmodel;

import gptui.model.state.StateModel;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static gptui.model.storage.AnswerType.GCP;
import static gptui.model.storage.AnswerType.GRAMMAR;
import static gptui.model.storage.AnswerType.LONG;
import static gptui.model.storage.AnswerType.SHORT;

@Singleton
public class GptUiVM {
    private static final Logger log = LoggerFactory.getLogger(GptUiVM.class);
    @Inject
    private StateModel stateModel;
    @Inject
    private ViewModelMediator mediator;

    public void initialize() {
        log.trace("initialize");
        var history = stateModel.getFullHistory();
        stateModel.chooseFirstInteractionAsCurrent();
        if (!history.isEmpty()) {
            var currentInteraction = stateModel.getCurrentInteraction();
            currentInteraction.getAnswer(GRAMMAR).ifPresent(answer -> stateModel.setTemperature(GRAMMAR, answer.temperature()));
            currentInteraction.getAnswer(SHORT).ifPresent(answer -> stateModel.setTemperature(SHORT, answer.temperature()));
            currentInteraction.getAnswer(LONG).ifPresent(answer -> stateModel.setTemperature(LONG, answer.temperature()));
            currentInteraction.getAnswer(GCP).ifPresent(answer -> stateModel.setTemperature(GCP, answer.temperature()));
        }
        stateModel.setFirstThemeAsCurrent();
        mediator.displayCurrentInteraction();
    }
}

