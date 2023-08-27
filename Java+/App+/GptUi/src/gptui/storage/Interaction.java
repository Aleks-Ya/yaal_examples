package gptui.storage;

public record Interaction(InteractionId id,
                          String theme,
                          String question,
                          String askForQuestionCorrectness,
                          String questionCorrectnessAnswer,
                          String askForShortAnswer,
                          String shortAnswerMd,
                          String shortAnswerHtml,
                          String askForLongAnswer,
                          String longAnswerMd,
                          String longAnswerHtml) {

    public Interaction withTheme(String theme) {
        return new Interaction(id, theme, question, askForQuestionCorrectness, questionCorrectnessAnswer,
                askForShortAnswer, shortAnswerMd, shortAnswerHtml, askForLongAnswer, longAnswerMd, longAnswerHtml);
    }

    public Interaction withQuestion(String question) {
        return new Interaction(id, theme, question, askForQuestionCorrectness, questionCorrectnessAnswer,
                askForShortAnswer, shortAnswerMd, shortAnswerHtml, askForLongAnswer, longAnswerMd, longAnswerHtml);
    }

    public Interaction withAskForLongAnswer(String askForLongAnswer) {
        return new Interaction(id, theme, question, askForQuestionCorrectness, questionCorrectnessAnswer,
                askForShortAnswer, shortAnswerMd, shortAnswerHtml, askForLongAnswer, longAnswerMd, longAnswerHtml);
    }

    public Interaction withLongAnswerHtml(String longAnswerHtml) {
        return new Interaction(id, theme, question, askForQuestionCorrectness, questionCorrectnessAnswer,
                askForShortAnswer, shortAnswerMd, shortAnswerHtml, askForLongAnswer, longAnswerMd, longAnswerHtml);
    }

    public Interaction withLongAnswerMd(String longAnswerMd) {
        return new Interaction(id, theme, question, askForQuestionCorrectness, questionCorrectnessAnswer,
                askForShortAnswer, shortAnswerMd, shortAnswerHtml, askForLongAnswer, longAnswerMd, longAnswerHtml);
    }

    public Interaction withAskForShortAnswer(String askForShortAnswer) {
        return new Interaction(id, theme, question, askForQuestionCorrectness, questionCorrectnessAnswer,
                askForShortAnswer, shortAnswerMd, shortAnswerHtml, askForLongAnswer, longAnswerMd, longAnswerHtml);
    }

    public Interaction withShortAnswerHtml(String shortAnswerHtml) {
        return new Interaction(id, theme, question, askForQuestionCorrectness, questionCorrectnessAnswer,
                askForShortAnswer, shortAnswerMd, shortAnswerHtml, askForLongAnswer, longAnswerMd, longAnswerHtml);
    }

    public Interaction withShortAnswerMd(String shortAnswerMd) {
        return new Interaction(id, theme, question, askForQuestionCorrectness, questionCorrectnessAnswer,
                askForShortAnswer, shortAnswerMd, shortAnswerHtml, askForLongAnswer, longAnswerMd, longAnswerHtml);
    }

    public Interaction withAskForQuestionCorrectness(String askForQuestionCorrectness) {
        return new Interaction(id, theme, question, askForQuestionCorrectness, questionCorrectnessAnswer,
                askForShortAnswer, shortAnswerMd, shortAnswerHtml, askForLongAnswer, longAnswerMd, longAnswerHtml);
    }

    public Interaction withQuestionCorrectnessAnswer(String questionCorrectnessAnswer) {
        return new Interaction(id, theme, question, askForQuestionCorrectness, questionCorrectnessAnswer,
                askForShortAnswer, shortAnswerMd, shortAnswerHtml, askForLongAnswer, longAnswerMd, longAnswerHtml);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", theme, question);
    }
}
