package gptui.ui;

import gptui.storage.Interaction;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCodeCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Singleton
public class Model {
    private static final Logger log = LoggerFactory.getLogger(Model.class);
    private final Set<ModelListener> listeners = new HashSet<>();
    private List<Interaction> history = new ArrayList<>();
    private Interaction currentInteraction;
    private List<String> themeList;
    private String currentTheme;
    private String editedQuestion;
    private Scene scene;

    public void subscribe(ModelListener listener) {
        log.debug("ModelListener subscribed: {}", listener);
        listeners.add(listener);
    }

    public void fireModelChanged(EventSource source) {
        checkJavaFxThread();
        var selfListeners = getSelfListeners(source);
        var notSelfListeners = getNotSelfListeners(source);
        log.debug("Firing model changed to {} listeners (skip {} self-listeners)...", notSelfListeners.size(), selfListeners.size());
        notSelfListeners.forEach(listener -> listener.modelChanged(this, source));
    }

    public void fireStageShowed(EventSource source) {
        checkJavaFxThread();
        var selfListeners = getSelfListeners(source);
        var notSelfListeners = getNotSelfListeners(source);
        log.debug("Firing stage was showed to {} listeners (skip {} self-listeners)...", notSelfListeners.size(), selfListeners.size());
        notSelfListeners.forEach(listener -> listener.stageWasShowed(this, source));
    }

    public void fireInteractionChosenFromHistory(EventSource source) {
        checkJavaFxThread();
        var selfListeners = getSelfListeners(source);
        var notSelfListeners = getNotSelfListeners(source);
        log.debug("Firing interaction chosen from history to {} listeners (skip {} self-listeners of {})...",
                notSelfListeners.size(), selfListeners.size(), source.getClass().getSimpleName());
        notSelfListeners.forEach(listener -> listener.interactionChosenFromHistory(this, source));
    }

    private void checkJavaFxThread() {
        if (!Platform.isFxApplicationThread()) {
            throw new IllegalStateException("Must be JavaFX Application Thread, but actual thread is " +
                    Thread.currentThread().getName());
        }
    }

    private List<ModelListener> getNotSelfListeners(EventSource source) {
        return listeners.stream().filter(listener -> listener != source).toList();
    }

    private List<ModelListener> getSelfListeners(EventSource source) {
        return listeners.stream().filter(listener -> listener == source).toList();
    }

    public synchronized List<Interaction> getHistory() {
        return history;
    }

    public synchronized void setHistory(List<Interaction> history) {
        this.history = history;
    }

    public synchronized List<String> getThemeList() {
        return themeList;
    }

    public synchronized void setThemeList(List<String> themeList) {
        this.themeList = themeList;
    }

    public synchronized Interaction getCurrentInteraction() {
        return currentInteraction;
    }

    public synchronized void setCurrentInteraction(Interaction currentInteraction) {
        log.debug("setCurrentInteraction: {}", currentInteraction);
        this.currentInteraction = currentInteraction;
    }

    public synchronized String getEditedTheme() {
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

    public void addAccelerator(KeyCodeCombination keyCodeCombination, Runnable runnable) {
        scene.getAccelerators().put(keyCodeCombination, runnable);
    }
}
