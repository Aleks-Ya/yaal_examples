package gptui.storage;

public record Answer(AnswerType answerType, String prompt, String answerMd, String answerHtml) {
}
