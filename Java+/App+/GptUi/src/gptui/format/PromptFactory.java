package gptui.format;

import gptui.storage.AnswerType;
import gptui.storage.InteractionType;

import javax.inject.Singleton;

@Singleton
public class PromptFactory {
    public String getPrompt(InteractionType interactionType, String theme, String question, AnswerType answerType) {
        return switch (interactionType) {
            case QUESTION -> switch (answerType) {
                case GRAMMAR -> grammarPrompt(question);
                case SHORT -> String.format("""
                                I will ask you a question about "%s".
                                You should answer with a short response.
                                Format your answer into Markdown.
                                The question is `%s`.
                                """.stripIndent().replace("\n", " "),
                        theme, question);
                case LONG -> String.format("""
                                I will ask you a question about "%s".
                                You should answer with a detailed response.
                                Format your answer into Markdown.
                                The question is `%s`.
                                """.stripIndent().replace("\n", " "),
                        theme, question);
            };
            case DEFINITION -> switch (answerType) {
                case GRAMMAR -> grammarPrompt(question);
                case SHORT -> String.format("""
                                Provide a single-sentence definition of '%s' in the context of '%s', as short as possible.
                                Format your answer into Markdown.
                                """.stripIndent().replace("\n", " "),
                        question, theme, question);
                case LONG -> String.format("""
                                Provide a detailed, single-sentence definition of '%s' in the context of '%s'.
                                Format your answer into Markdown.
                                """.stripIndent().replace("\n", " "),
                        question, theme, question);
            };
        };
    }

    private static String grammarPrompt(String question) {
        return String.format("""
                        I will give you a sentence.
                        Check if the sentence has grammatical mistakes.
                        It is not a mistake if the sentence starts with "How to".
                        The sentence is `%s`.""".stripIndent().replace("\n", " "),
                question);
    }
}
