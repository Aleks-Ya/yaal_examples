package gptui.ui.model.question.question;

import gptui.storage.AnswerType;
import gptui.storage.InteractionType;
import jakarta.inject.Singleton;

import java.util.Optional;

import static java.lang.String.format;

@Singleton
@SuppressWarnings("TextBlockMigration")
class PromptFactory {
    public Optional<String> getPrompt(InteractionType interactionType, String theme, String question, AnswerType answerType) {
        return switch (interactionType) {
            case QUESTION -> switch (answerType) {
                case GRAMMAR -> grammarPrompt(question);
                case SHORT -> Optional.of(format(
                        "I will ask you a question about \"%s\". " +
                                "You should answer with a short response. " +
                                "Format your answer into Markdown. " +
                                "The question is:\n" +
                                "```\n" +
                                "%s\n" +
                                "```",
                        theme, question));
                case LONG -> Optional.of(format(
                        "I will ask you a question about \"%s\". " +
                                "Format your answer into Markdown. " +
                                "The question is:\n" +
                                "```\n" +
                                "%s\n" +
                                "```",
                        theme, question));
                case GCP -> Optional.of(format(
                        "Answer question about `%s`:\n" +
                                "```\n" +
                                "%s\n" +
                                "```", theme, question));
            };
            case DEFINITION -> switch (answerType) {
                case GRAMMAR -> Optional.of(format(
                        "I will give you a phrase related to `%s`. " +
                                "Check if the phrase has grammatical mistakes. " +
                                "It is not a mistake if the phrase starts with \"How to\". " +
                                "The phrase is:\n" +
                                "```\n" +
                                "%s\n" +
                                "```",
                        theme, question));
                case SHORT -> Optional.of(format(
                        "Provide a single-sentence definition of `%s` in the context of `%s`, as short as possible. " +
                                "Format your answer into Markdown.",
                        question, theme));
                case LONG -> Optional.of(format(
                        "Provide a detailed, single-sentence definition of `%s` in the context of `%s`. " +
                                "Format your answer into Markdown.",
                        question, theme));
                case GCP -> Optional.of(format(
                        "Provide a single-sentence definition of `%s` in the context of `%s`.",
                        question, theme));
            };
            case GRAMMAR -> switch (answerType) {
                case GRAMMAR -> grammarPrompt(question);
                case SHORT, LONG, GCP -> Optional.empty();
            };
            case FACT -> switch (answerType) {
                case GRAMMAR -> Optional.of(format(
                        "Check is this sentence factually correct in context of `%s`: `%s`? " +
                                "Format your answer into Markdown.",
                        theme, question));
                case SHORT, LONG, GCP -> Optional.empty();
            };
        };
    }

    private static Optional<String> grammarPrompt(String question) {
        return Optional.of(format(
                "I will give you a sentence or phrase. " +
                        "Check if the sentence or phrase has grammatical mistakes. " +
                        "It is not a mistake if the sentence or phrase starts with \"How to\". " +
                        "If the given sentence or phrase is correct, just answer \"Correct\". " +
                        "If the sentence or phrase has mistakes, just answer with correct sentence. " +
                        "The sentence or phrase is:\n" +
                        "```\n" +
                        "%s\n" +
                        "```",
                question));
    }
}

