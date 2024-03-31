package gptui.viewmodel.theme;

public interface ThemeVmMediator {
    void updateComboBoxSelectedItemFromCurrentInteraction();

    void updateComboBoxSelectedItemFromStateModel();

    void updateComboBoxItems();

    void setLabel();

    void initialize();
}
