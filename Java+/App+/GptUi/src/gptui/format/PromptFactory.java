package gptui.format;

import gptui.storage.AnswerType;

import javax.inject.Singleton;

@Singleton
public class PromptFactory {
    public String getPrompt(String theme, String question, AnswerType answerType) {
        return switch (answerType) {
            case QUESTION_CORRECTNESS -> String.format("""
                            I will give you a sentence.
                            Check if the sentence has grammatical mistakes.
                            It is not a mistake if the sentence starts with "How to".
                            The sentence is `%s`.""".stripIndent().replace("\n", " "),
                    question);
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
    }
}
