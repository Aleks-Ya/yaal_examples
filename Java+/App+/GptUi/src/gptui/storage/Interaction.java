package gptui.storage;

public record Interaction(InteractionId id,
                          String theme,
                          String question,
                          String questionCorrectnessPrompt,
                          String questionCorrectnessAnswer,
                          String shortAnswerPrompt,
                          String shortAnswerMd,
                          String shortAnswerHtml,
                          String longAnswerPrompt,
                          String longAnswerMd,
                          String longAnswerHtml) {

    public Interaction withTheme(String theme) {
        return new Interaction(id, theme, question, questionCorrectnessPrompt, questionCorrectnessAnswer,
                shortAnswerPrompt, shortAnswerMd, shortAnswerHtml, longAnswerPrompt, longAnswerMd, longAnswerHtml);
    }

    public Interaction withQuestion(String question) {
        return new Interaction(id, theme, question, questionCorrectnessPrompt, questionCorrectnessAnswer,
                shortAnswerPrompt, shortAnswerMd, shortAnswerHtml, longAnswerPrompt, longAnswerMd, longAnswerHtml);
    }

    public Interaction withLongAnswerPrompt(String longAnswerPrompt) {
        return new Interaction(id, theme, question, questionCorrectnessPrompt, questionCorrectnessAnswer,
                shortAnswerPrompt, shortAnswerMd, shortAnswerHtml, longAnswerPrompt, longAnswerMd, longAnswerHtml);
    }

    public Interaction withLongAnswerHtml(String longAnswerHtml) {
        return new Interaction(id, theme, question, questionCorrectnessPrompt, questionCorrectnessAnswer,
                shortAnswerPrompt, shortAnswerMd, shortAnswerHtml, longAnswerPrompt, longAnswerMd, longAnswerHtml);
    }

    public Interaction withLongAnswerMd(String longAnswerMd) {
        return new Interaction(id, theme, question, questionCorrectnessPrompt, questionCorrectnessAnswer,
                shortAnswerPrompt, shortAnswerMd, shortAnswerHtml, longAnswerPrompt, longAnswerMd, longAnswerHtml);
    }

    public Interaction withShortAnswerPrompt(String shortAnswerPrompt) {
        return new Interaction(id, theme, question, questionCorrectnessPrompt, questionCorrectnessAnswer,
                shortAnswerPrompt, shortAnswerMd, shortAnswerHtml, longAnswerPrompt, longAnswerMd, longAnswerHtml);
    }

    public Interaction withShortAnswerHtml(String shortAnswerHtml) {
        return new Interaction(id, theme, question, questionCorrectnessPrompt, questionCorrectnessAnswer,
                shortAnswerPrompt, shortAnswerMd, shortAnswerHtml, longAnswerPrompt, longAnswerMd, longAnswerHtml);
    }

    public Interaction withShortAnswerMd(String shortAnswerMd) {
        return new Interaction(id, theme, question, questionCorrectnessPrompt, questionCorrectnessAnswer,
                shortAnswerPrompt, shortAnswerMd, shortAnswerHtml, longAnswerPrompt, longAnswerMd, longAnswerHtml);
    }

    public Interaction withQuestionCorrectnessPrompt(String questionCorrectnessPrompt) {
        return new Interaction(id, theme, question, questionCorrectnessPrompt, questionCorrectnessAnswer,
                shortAnswerPrompt, shortAnswerMd, shortAnswerHtml, longAnswerPrompt, longAnswerMd, longAnswerHtml);
    }

    public Interaction withQuestionCorrectnessAnswer(String questionCorrectnessAnswer) {
        return new Interaction(id, theme, question, questionCorrectnessPrompt, questionCorrectnessAnswer,
                shortAnswerPrompt, shortAnswerMd, shortAnswerHtml, longAnswerPrompt, longAnswerMd, longAnswerHtml);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", theme, question);
    }
}
