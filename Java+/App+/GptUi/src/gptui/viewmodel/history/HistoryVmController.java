package gptui.viewmodel.history;

public interface HistoryVmController {
    void onHistoryComboBoxAction();

    void onClickHistoryDeleteButton();

    HistoryVmProperties properties();
}
