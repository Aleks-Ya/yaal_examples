package gptui.model.state;

import gptui.model.storage.AnswerType;
import gptui.model.storage.Interaction;
import gptui.model.storage.InteractionId;
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
                .filter(interaction -> !historyFilteringEnabled || getCurrentTheme().trim().equals(interaction.theme().trim()))
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
    public synchronized void deleteCurrentInteraction() {
        storage.deleteInteraction(getCurrentInteractionId());
    }

    @Override
    public synchronized List<String> getThemes() {
        return storage.getThemes();
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

    @Override
    public void choosePreviousInteractionAsCurrent(int currentInteractionIndex) {
        log.trace("choosePreviousInteractionAsCurrent: {}", currentInteractionIndex);
        var newHistory = getFilteredHistory();
        if (!newHistory.isEmpty()) {
            var newCurrentInteractionIndex = currentInteractionIndex - 1;
            Interaction newCurrentInteraction;
            if (newCurrentInteractionIndex >= 0 && newCurrentInteractionIndex < newHistory.size()) {
                newCurrentInteraction = newHistory.get(newCurrentInteractionIndex);
            } else {
                newCurrentInteraction = newHistory.getLast();
            }
            setCurrentInteractionId(newCurrentInteraction.id());
            setCurrentTheme(newCurrentInteraction.theme());
        } else {
            setCurrentInteractionId(null);
            setCurrentTheme(null);
        }
    }
}
