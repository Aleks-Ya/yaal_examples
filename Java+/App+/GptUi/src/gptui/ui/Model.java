package gptui.ui;

import gptui.storage.AnswerType;
import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import gptui.storage.InteractionStorage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCodeCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

@Singleton
public class Model {
    private static final Logger log = LoggerFactory.getLogger(Model.class);
    @Inject
    private InteractionStorage storage;
    private InteractionId currentInteractionId;
    private String currentTheme;
    private String editedQuestion;
    private Scene scene;
    private final Temperatures temperatures = new Temperatures();
    private Boolean themeFilterHistory = false;
    private final Fire fire = new Fire();

    public void subscribe(ModelListener listener) {
        log.debug("ModelListener subscribed: {}", listener);
        fire.subscribe(listener);
    }

    public synchronized boolean isEnteringNewQuestion() {
        return storage.readInteraction(getCurrentInteractionId())
//                .filter(ignore -> getEditedQuestion() != null)
//                .filter(ignore -> !getEditedQuestion().isEmpty())
                .map(interaction -> !Objects.equals(interaction.question(), getEditedQuestion()))
                .orElse(false);
    }

    public synchronized List<Interaction> getFullHistory() {
        return storage.readAllInteractions();
    }

    public synchronized List<Interaction> getFilteredHistory() {
        return getFullHistory().stream()
                .filter(interaction -> !isThemeFilterHistory() || getCurrentTheme().trim().equals(interaction.theme().trim()))
                .toList();
    }

    public synchronized InteractionId getCurrentInteractionId() {
        return currentInteractionId;
    }

    public synchronized void setCurrentInteractionId(InteractionId currentInteractionId) {
        log.debug("setCurrentInteractionId: {}", currentInteractionId);
        this.currentInteractionId = currentInteractionId;
    }

    public synchronized String getCurrentTheme() {
        return currentTheme;
    }

    public synchronized void setCurrentTheme(String currentTheme) {
        this.currentTheme = currentTheme;
    }

    public synchronized String getEditedQuestion() {
        return editedQuestion;
    }

    public synchronized void setEditedQuestion(String question) {
        this.editedQuestion = question;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Temperatures getTemperatures() {
        return temperatures;
    }

    public void setTemperature(AnswerType answerType, Integer temperature) {
        temperatures.setTemperature(answerType, temperature);
    }

    public void addAccelerator(KeyCodeCombination keyCodeCombination, Runnable runnable) {
        scene.getAccelerators().put(keyCodeCombination, runnable);
    }

    public Boolean isThemeFilterHistory() {
        return themeFilterHistory;
    }

    public void setIsThemeFilterHistory(Boolean isThemeFilterHistory) {
        log.trace("setIsThemeFilterHistory: {}", isThemeFilterHistory);
        this.themeFilterHistory = isThemeFilterHistory;
    }

    public Fire fire() {
        return fire;
    }

    public class Fire {
        private final Set<ModelListener> listeners = new HashSet<>();

        public void stageShowed(EventSource source) {
            fire("StageShowed", source, listener -> listener.stageWasShowed(Model.this));
        }

        public void interactionChosenFromHistory(EventSource source) {
            fire("InteractionChosenFromHistory", source,
                    listener -> listener.interactionChosenFromHistory(Model.this));
        }

        public void themeWasChosen(EventSource source) {
            fire("ThemeWasChosen", source, listener -> listener.themeWasChosen(Model.this));
        }

        public void isThemeFilterHistoryChanged(EventSource source) {
            fire("IsThemeFilterHistoryChanged", source,
                    listener -> listener.isThemeFilterHistoryChanged(Model.this));
        }

        public void interactionsUpdated(EventSource source) {
            fire("InteractionsUpdated", source, listener -> listener.interactionsUpdated(Model.this));
        }

        public void answerUpdated(EventSource source) {
            fire("AnswerUpdated", source, listener -> listener.answerUpdated(Model.this));
        }

        void subscribe(ModelListener listener) {
            fire.listeners.add(listener);
        }

        private void fire(String eventName, EventSource source, Consumer<? super ModelListener> consumer) {
            var selfListeners = getSelfListeners(source);
            var notSelfListeners = getNotSelfListeners(source);
            log.debug("Firing interaction '{}' to {} listeners (skip {} self-listeners) by {}...",
                    eventName, notSelfListeners.size(), selfListeners.size(), source.getName());
            notSelfListeners.forEach(consumer);
        }

        private List<ModelListener> getNotSelfListeners(EventSource source) {
            return listeners.stream().filter(listener -> listener != source).toList();
        }

        private List<ModelListener> getSelfListeners(EventSource source) {
            return listeners.stream().filter(listener -> listener == source).toList();
        }
    }
}
