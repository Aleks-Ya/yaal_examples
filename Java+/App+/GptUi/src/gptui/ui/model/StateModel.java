package gptui.ui.model;

import gptui.storage.AnswerType;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import gptui.storage.InteractionStorage;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import javafx.scene.Scene;
import javafx.scene.input.KeyCodeCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static gptui.storage.AnswerType.GCP;
import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;

@Singleton
public class StateModel {
    private static final Logger log = LoggerFactory.getLogger(StateModel.class);
    @Inject
    private InteractionStorage storage;
    private InteractionId currentInteractionId;
    private String currentTheme;
    private String editedQuestion;
    private Scene scene;
    private Boolean isHistoryFilteringEnabled = false;
    private final Map<AnswerType, Integer> temperatures = new HashMap<>(Map.of(
            GRAMMAR, 50,
            SHORT, 60,
            LONG, 70,
            GCP, 30
    ));

    public synchronized boolean isEnteringNewQuestion() {
        return getCurrentInteractionOpt()
                .map(interaction -> !Objects.equals(interaction.question(), getEditedQuestion()))
                .orElse(false);
    }

    public synchronized List<Interaction> getFullHistory() {
        return storage.readAllInteractions();
    }

    public synchronized List<Interaction> getFilteredHistory() {
        return getFullHistory().stream()
                .filter(interaction -> !isHistoryFilteringEnabled() || getCurrentTheme().trim().equals(interaction.theme().trim()))
                .toList();
    }

    public synchronized InteractionId getCurrentInteractionId() {
        return currentInteractionId;
    }

    public synchronized Interaction getCurrentInteraction() {
        return getCurrentInteractionOpt().orElseThrow();
    }

    public synchronized Optional<Interaction> getCurrentInteractionOpt() {
        var result = storage.readInteraction(currentInteractionId);
        log.trace("getCurrentInteractionOpt: '{}'", result);
        return result;
    }

    public synchronized void setCurrentInteractionId(InteractionId currentInteractionId) {
        log.trace("setCurrentInteractionId: {}", currentInteractionId);
        this.currentInteractionId = currentInteractionId;
    }

    public synchronized void deleteCurrentInteraction() {
        storage.deleteInteraction(getCurrentInteractionId());
    }

    public synchronized List<Interaction> getAllInteractions() {
        return storage.readAllInteractions();
    }

    public synchronized List<String> getThemes() {
        return storage.getThemes();
    }

    public synchronized String getCurrentTheme() {
        return currentTheme;
    }

    public synchronized void setCurrentTheme(String currentTheme) {
        log.trace("setCurrentTheme: '{}'", currentTheme);
        this.currentTheme = currentTheme;
    }

    public synchronized void setFirstThemeAsCurrent() {
        setCurrentTheme(!getThemes().isEmpty() ? getThemes().getFirst() : null);
    }

    public synchronized String getEditedQuestion() {
        return editedQuestion;
    }

    public synchronized void setEditedQuestion(String question) {
        log.trace("setEditedQuestion: '{}'", question);
        this.editedQuestion = question;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Integer getTemperature(AnswerType answerType) {
        return temperatures.get(answerType);
    }

    public void setTemperature(AnswerType answerType, Integer temperature) {
        temperatures.put(answerType, temperature);
    }

    public void addAccelerator(KeyCodeCombination keyCodeCombination, Runnable runnable) {
        scene.getAccelerators().put(keyCodeCombination, runnable);
    }

    public Boolean isHistoryFilteringEnabled() {
        log.trace("isHistoryFilteringEnabled: {}", isHistoryFilteringEnabled);
        return isHistoryFilteringEnabled;
    }

    public void setIsHistoryFilteringEnabled(Boolean isHistoryFilteringEnabled) {
        log.trace("setIsHistoryFilteringEnabled: {}", isHistoryFilteringEnabled);
        this.isHistoryFilteringEnabled = isHistoryFilteringEnabled;
    }

    public void chooseFirstInteractionAsCurrent() {
        if (!getFilteredHistory().isEmpty()) {
            setCurrentInteractionId(getFilteredHistory().getFirst().id());
        } else {
            setCurrentInteractionId(null);
        }
    }
}
