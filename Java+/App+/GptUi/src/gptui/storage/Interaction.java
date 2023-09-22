package gptui.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record Interaction(InteractionId id,
                          String theme,
                          String question,
                          Map<AnswerType, Answer> answers) {

    public Interaction(InteractionId id, String theme, String question, Map<AnswerType, Answer> answers) {
        this.id = id;
        this.theme = theme;
        this.question = question;
        this.answers = answers != null ? answers : new HashMap<>();
    }

    public Optional<Answer> getAnswer(AnswerType answerType) {
        return Optional.ofNullable(answers.get(answerType));
    }

    public Interaction withTheme(String theme) {
        return new Interaction(id, theme, question, answers);
    }

    public Interaction withQuestion(String question) {
        return new Interaction(id, theme, question, answers);
    }

    public Interaction withAnswer(Answer answer) {
        var map = new HashMap<>(answers);
        map.put(answer.answerType(), answer);
        return new Interaction(id, theme, question, Map.copyOf(map));
    }

    @Override
    public String toString() {
        return String.format("%s: %s", theme, question);
    }
}
