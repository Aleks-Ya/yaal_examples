package gptui.viewmodel.answer;

public interface AnswerVmController {
    void onCopyButtonClick();

    void onRegenerateButtonClick();

    AnswerVmProperties properties();

    void ctrlAltUpHotkeyPressed();

    void ctrlAltDownHotkeyPressed();
}
