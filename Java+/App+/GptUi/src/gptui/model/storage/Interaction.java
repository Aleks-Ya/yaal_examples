package gptui.model.storage;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;

public record Interaction(InteractionId id,
                          InteractionType type,
                          String theme,
                          ThemeId themeId,
                          String question,
                          Map<AnswerType, Answer> answers) {

    public Interaction(InteractionId id, InteractionType type, String theme, ThemeId themeId, String question,
                       Map<AnswerType, Answer> answers) {
        this.id = id;
        this.type = type;
        this.theme = theme;
        this.themeId = themeId;
        this.question = question;
        this.answers = answers != null ? answers : new TreeMap<>();
    }

    public Optional<Answer> getAnswer(AnswerType answerType) {
        return Optional.ofNullable(answers.get(answerType));
    }

    public Interaction withAnswer(Answer answer) {
        var map = new TreeMap<>(answers);
        map.put(answer.answerType(), answer);
        return new Interaction(id, type, theme, themeId, question, Map.copyOf(map));
    }

    public Interaction withThemeId(ThemeId themeId) {
        return new Interaction(id, type, theme, themeId, question, answers);
    }

    public Interaction withAnswer(AnswerType answerType, Function<Answer, Answer> update) {
        var currentAnswer = answers.getOrDefault(answerType,
                new Answer(answerType, null, null, null, null, null));
        var newAnswer = update.apply(currentAnswer);
        return withAnswer(newAnswer);
    }

    @SuppressWarnings("unused")
    public Interaction withAnswerDeleted(AnswerType answerType) {
        var map = new TreeMap<>(answers);
        map.remove(answerType);
        return new Interaction(id, type, theme, themeId, question, Map.copyOf(map));
    }

    @Override
    public String toString() {
        var typeStr = "";
        if (type != null) {
            var typeSymbol = switch (type) {
                case QUESTION -> "Q";
                case DEFINITION -> "D";
                case GRAMMAR -> "G";
                case FACT -> "F";
            };
            typeStr = String.format("[%s] ", typeSymbol);
        }
        return String.format("%s%s: %s", typeStr, theme, question);
    }

    public String toStringFull() {
        return "Interaction{" +
                "id=" + id +
                ", type=" + type +
                ", theme='" + theme + '\'' +
                ", question='" + question + '\'' +
                ", answers=" + answers +
                '}';
    }
}
