package gptui.fxml;

import gptui.storage.Interaction;
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
    private List<Interaction> interactionHistory = new ArrayList<>();
    private Interaction currentInteraction;
    private List<String> themeList;
    private String currentTheme;
    private String question;
    private Scene scene;

    public void subscribe(ModelListener listener) {
        log.debug("ModelListener subscribed: {}", listener);
        listeners.add(listener);
    }

    public void fireModelChanged() {
        log.debug("Firing model changed to {} listeners...", listeners.size());
        listeners.forEach(listener -> listener.modelChanged(this));
    }

    public void fireStageShowed() {
        log.debug("Firing stage was showed to {} listeners...", listeners.size());
        listeners.forEach(listener -> listener.stageWasShowed(this));
    }

    public void fireInteractionChosenFromHistory() {
        log.debug("Firing interaction chosen from history to {} listeners...", listeners.size());
        listeners.forEach(listener -> listener.interactionChosenFromHistory(this));
    }

    public List<Interaction> getInteractionHistory() {
        return interactionHistory;
    }

    public void setInteractionHistory(List<Interaction> interactionHistory) {
        this.interactionHistory = interactionHistory;
    }

    public List<String> getThemeList() {
        return themeList;
    }

    public void setThemeList(List<String> themeList) {
        this.themeList = themeList;
    }

    public Interaction getCurrentInteraction() {
        return currentInteraction;
    }

    public void setCurrentInteraction(Interaction currentInteraction) {
        this.currentInteraction = currentInteraction;
    }

    public String getCurrentTheme() {
        return currentTheme;
    }

    public void setCurrentTheme(String currentTheme) {
        this.currentTheme = currentTheme;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void addAccelerator(KeyCodeCombination keyCodeCombination, Runnable runnable) {
        scene.getAccelerators().put(keyCodeCombination, runnable);
    }
}
