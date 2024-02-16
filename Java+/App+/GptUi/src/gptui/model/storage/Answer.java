package gptui.model.storage;

public record Answer(AnswerType answerType, String prompt, Integer temperature,
                     String answerMd, String answerHtml, AnswerState answerState) {

    public Answer withPrompt(String prompt) {
        return new Answer(answerType, prompt, temperature, answerMd, answerHtml, answerState);
    }

    public Answer withTemperature(Integer temperature) {
        return new Answer(answerType, prompt, temperature, answerMd, answerHtml, answerState);
    }

    public Answer withAnswerMd(String answerMd) {
        return new Answer(answerType, prompt, temperature, answerMd, answerHtml, answerState);
    }

    public Answer withAnswerHtml(String answerHtml) {
        return new Answer(answerType, prompt, temperature, answerMd, answerHtml, answerState);
    }

    public Answer withState(AnswerState answerState) {
        return new Answer(answerType, prompt, temperature, answerMd, answerHtml, answerState);
    }
}
