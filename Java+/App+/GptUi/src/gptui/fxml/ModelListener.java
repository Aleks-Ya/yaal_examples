package gptui.fxml;

public interface ModelListener {
    default void modelChanged(Model model) {
    }

    default void interactionChosenFromHistory(Model model) {
        modelChanged(model);
    }

    default void stageWasShowed(Model model) {
    }
}
