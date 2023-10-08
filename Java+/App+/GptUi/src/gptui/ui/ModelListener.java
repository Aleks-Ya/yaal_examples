package gptui.ui;

public interface ModelListener {
    default void modelChanged(Model model, EventSource source) {
    }

    default void interactionChosenFromHistory(Model model, EventSource source) {
        modelChanged(model, source);
    }

    default void stageWasShowed(Model model, EventSource source) {
    }
}
