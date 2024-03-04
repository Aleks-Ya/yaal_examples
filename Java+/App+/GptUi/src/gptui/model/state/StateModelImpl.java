package gptui.model.state;

import gptui.Mdc;
import gptui.model.storage.Answer;
import gptui.model.storage.AnswerType;
import gptui.model.storage.Interaction;
import gptui.model.storage.InteractionId;
import gptui.model.storage.InteractionType;
import gptui.model.storage.StorageModel;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static gptui.model.storage.AnswerState.NEW;
import static gptui.model.storage.AnswerType.GCP;
import static gptui.model.storage.AnswerType.GRAMMAR;
import static gptui.model.storage.AnswerType.LONG;
import static gptui.model.storage.AnswerType.SHORT;

@Singleton
class StateModelImpl implements StateModel {
    private static final Logger log = LoggerFactory.getLogger(StateModelImpl.class);
    @Inject
    private StorageModel storage;
    private InteractionId currentInteractionId;
    private String currentTheme;
    private String editedQuestion;
    private Boolean isHistoryFilteringEnabled = false;
    private final Map<AnswerType, Integer> temperatures = new HashMap<>(Map.of(
            AnswerType.GRAMMAR, 50,
            AnswerType.SHORT, 60,
            AnswerType.LONG, 70,
            AnswerType.GCP, 90
    ));

    @Override
    public synchronized boolean isEnteringNewQuestion() {
        return getCurrentInteractionOpt()
                .map(interaction -> !Objects.equals(interaction.question(), getEditedQuestion()))
                .orElse(false);
    }

    @Override
    public synchronized List<Interaction> getFullHistory() {
        return storage.readAllInteractions();
    }

    @Override
    public synchronized List<Interaction> getFilteredHistory() {
        var historyFilteringEnabled = isHistoryFilteringEnabled();
        return getFullHistory().stream()
                .filter(interaction -> !historyFilteringEnabled || Objects.equals(getCurrentTheme(), interaction.theme()))
                .toList();
    }

    @Override
    public synchronized InteractionId getCurrentInteractionId() {
        log.trace("getCurrentInteractionId: '{}'", currentInteractionId);
        return currentInteractionId;
    }

    @Override
    public synchronized Interaction getCurrentInteraction() {
        return getCurrentInteractionOpt().orElseThrow();
    }

    @Override
    public synchronized Optional<Interaction> getCurrentInteractionOpt() {
        var result = storage.readInteraction(currentInteractionId);
        log.trace("getCurrentInteractionOpt: '{}'", result);
        return result;
    }

    @Override
    public synchronized void setCurrentInteractionId(InteractionId currentInteractionId) {
        log.trace("setCurrentInteractionId: {}", currentInteractionId);
        this.currentInteractionId = currentInteractionId;
    }

    @Override
    public InteractionId createInteraction(InteractionType interactionType) {
        var interactionId = storage.newInteractionId();
        Mdc.run(interactionId, () -> {
            var theme = getCurrentTheme();
            var question = getEditedQuestion();
            var interaction = new Interaction(interactionId, interactionType, theme, null, question, Map.of(
                    GRAMMAR, new Answer(GRAMMAR, "", getTemperature(GRAMMAR), "", "", NEW),
                    SHORT, new Answer(SHORT, "", getTemperature(SHORT), "", "", NEW),
                    LONG, new Answer(LONG, "", getTemperature(LONG), "", "", NEW),
                    GCP, new Answer(GCP, "", getTemperature(GCP), "", "", NEW)
            ));
            storage.saveInteraction(interaction);
            setCurrentInteractionId(interactionId);
        });
        return interactionId;
    }

    @Override
    public synchronized void deleteCurrentInteraction() {
        var currentInteractionOpt = getCurrentInteractionOpt();
        if (currentInteractionOpt.isPresent()) {
            var currentInteraction = currentInteractionOpt.get();
            var history = getFilteredHistory();
            var oldCurrentInteractionIndex = history.indexOf(currentInteraction);
            Interaction newCurrentInteraction = null;
            if (history.size() > 1) {
                if (oldCurrentInteractionIndex == 0) {
                    newCurrentInteraction = history.get(oldCurrentInteractionIndex + 1);
                } else {
                    newCurrentInteraction = history.get(oldCurrentInteractionIndex - 1);
                }
                var newCurrentTheme = newCurrentInteraction.theme();
                setCurrentTheme(newCurrentTheme);
            } else {
                var currentTheme = getCurrentTheme();
                if (currentTheme != null) {
                    var oldCurrentThemeIndex = getThemes().indexOf(currentTheme);
                    if (getThemes().size() > 1) {
                        String newCurrentTheme;
                        if (oldCurrentThemeIndex == 0) {
                            newCurrentTheme = getThemes().get(oldCurrentThemeIndex + 1);
                        } else {
                            newCurrentTheme = getThemes().get(oldCurrentThemeIndex - 1);
                        }
                        setCurrentTheme(newCurrentTheme);
                        var history2 = getFilteredHistory();
                        if (!history2.isEmpty()) {
                            newCurrentInteraction = history2.getFirst();
                        }
                    }
                }
            }
            storage.deleteInteraction(currentInteraction.id());
            setCurrentInteractionId(newCurrentInteraction != null ? newCurrentInteraction.id() : null);
        }
    }

    @Override
    public synchronized List<String> getThemes() {
        return storage.getThemes();
    }

    @Override
    public Long getInteractionCountInTheme(String theme) {
        return getFullHistory().stream().filter(interaction -> Objects.equals(interaction.theme(), theme)).count();
    }

    @Override
    public synchronized String getCurrentTheme() {
        return currentTheme;
    }

    @Override
    public synchronized void setCurrentTheme(String currentTheme) {
        log.trace("setCurrentTheme: '{}'", currentTheme);
        this.currentTheme = currentTheme;
    }

    @Override
    public synchronized void setFirstThemeAsCurrent() {
        setCurrentTheme(!getThemes().isEmpty() ? getThemes().getFirst() : null);
    }

    @Override
    public synchronized String getEditedQuestion() {
        return editedQuestion;
    }

    @Override
    public synchronized void setEditedQuestion(String question) {
        log.trace("setEditedQuestion: '{}'", question);
        this.editedQuestion = question;
    }

    @Override
    public Integer getTemperature(AnswerType answerType) {
        return temperatures.get(answerType);
    }

    @Override
    public void setTemperature(AnswerType answerType, Integer temperature) {
        temperatures.put(answerType, temperature);
    }

    @Override
    public Boolean isHistoryFilteringEnabled() {
        log.trace("isHistoryFilteringEnabled: {}", isHistoryFilteringEnabled);
        return isHistoryFilteringEnabled;
    }

    @Override
    public void setIsHistoryFilteringEnabled(Boolean isHistoryFilteringEnabled) {
        log.trace("setIsHistoryFilteringEnabled: {}", isHistoryFilteringEnabled);
        this.isHistoryFilteringEnabled = isHistoryFilteringEnabled;
    }

    @Override
    public void chooseFirstInteractionAsCurrent() {
        if (!getFilteredHistory().isEmpty()) {
            setCurrentInteractionId(getFilteredHistory().getFirst().id());
        } else {
            setCurrentInteractionId(null);
        }
    }
}
