package gptui.viewmodel.history;

public interface HistoryVmMediator {
    void displayCurrentInteraction();

    void selectPreviousItem();

    void selectNextItem();
}
