package gptui.viewmodel.theme;

public interface ThemeVmController {
    void onThemeComboBoxAction();

    void onThemeFilterHistoryCheckBoxClicked();

    void addNewTheme(String theme);

    ThemeVmProperties properties();
}
