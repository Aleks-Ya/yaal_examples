package gptui.viewmodel.question;

public interface QuestionVmController {
    void onRegenerateButtonClick();

    void onSendQuestionClick();

    void onSendDefinitionClick();

    void onSendGrammarClick();

    void onSendFactClick();

    void onKeyTypedQuestionTextArea();

    QuestionVmProperties properties();
}
