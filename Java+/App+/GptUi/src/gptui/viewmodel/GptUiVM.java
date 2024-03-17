package gptui.viewmodel;

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
    private ViewModelMediator mediator;

    public void initialize() {
        log.trace("initialize");
        var history = mediator.getFullHistory();
        mediator.chooseFirstInteractionAsCurrent();
        if (!history.isEmpty()) {
            var currentInteraction = mediator.getCurrentInteraction();
            currentInteraction.getAnswer(GRAMMAR).ifPresent(answer -> mediator.setTemperature(GRAMMAR, answer.temperature()));
            currentInteraction.getAnswer(SHORT).ifPresent(answer -> mediator.setTemperature(SHORT, answer.temperature()));
            currentInteraction.getAnswer(LONG).ifPresent(answer -> mediator.setTemperature(LONG, answer.temperature()));
            currentInteraction.getAnswer(GCP).ifPresent(answer -> mediator.setTemperature(GCP, answer.temperature()));
        }
        mediator.chooseFirstThemeAsCurrent();
        mediator.displayCurrentInteraction();
    }
}

