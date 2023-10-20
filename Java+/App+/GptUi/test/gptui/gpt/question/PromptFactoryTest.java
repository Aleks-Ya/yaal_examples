package gptui.gpt.question;

import gptui.storage.InteractionType;
import org.junit.jupiter.api.Test;

import static gptui.storage.AnswerType.GCP;
import static gptui.storage.AnswerType.GRAMMAR;
import static gptui.storage.AnswerType.LONG;
import static gptui.storage.AnswerType.SHORT;
import static gptui.storage.InteractionType.DEFINITION;
import static gptui.storage.InteractionType.FACT;
import static gptui.storage.InteractionType.QUESTION;
import static org.assertj.core.api.Assertions.assertThat;

class PromptFactoryTest {
    private final PromptFactory factory = new PromptFactory();

    @Test
    void question() {
        assertThat(factory.getPrompt(QUESTION, "Theme A", "Question A", GRAMMAR))
                .contains("I will give you a sentence. " +
                        "Check if the sentence has grammatical mistakes. " +
                        "It is not a mistake if the sentence starts with \"How to\". " +
                        "The sentence is `Question A`.");
        assertThat(factory.getPrompt(QUESTION, "Theme A", "Question A", SHORT))
                .contains("I will ask you a question about \"Theme A\". " +
                        "You should answer with a short response. " +
                        "Format your answer into Markdown. " +
                        "The question is `Question A`. ");
        assertThat(factory.getPrompt(QUESTION, "Theme A", "Question A", LONG))
                .contains("I will ask you a question about \"Theme A\". " +
                        "You should answer with a detailed response. " +
                        "Format your answer into Markdown. " +
                        "The question is `Question A`. ");
        assertThat(factory.getPrompt(QUESTION, "Theme A", "Question A", GCP))
                .contains("Answer question about `Theme A`: `Question A` ");
    }

    @Test
    void definition() {
        assertThat(factory.getPrompt(DEFINITION, "Theme A", "Question A", GRAMMAR))
                .contains("I will give you a phrase related to `Theme A`. " +
                        "Check if the phrase has grammatical mistakes. " +
                        "It is not a mistake if the phrase starts with \"How to\". " +
                        "The phrase is `Question A`.");
        assertThat(factory.getPrompt(DEFINITION, "Theme A", "Question A", SHORT))
                .contains("Provide a single-sentence definition of 'Question A' in the context of 'Theme A', as short as possible. " +
                        "Format your answer into Markdown. ");
        assertThat(factory.getPrompt(DEFINITION, "Theme A", "Question A", LONG))
                .contains("Provide a detailed, single-sentence definition of 'Question A' in the context of 'Theme A'. " +
                        "Format your answer into Markdown. ");
        assertThat(factory.getPrompt(DEFINITION, "Theme A", "Question A", GCP))
                .contains("Provide a single-sentence definition of 'Question A' in the context of 'Theme A'. ");
    }

    @Test
    void grammar() {
        assertThat(factory.getPrompt(InteractionType.GRAMMAR, "Theme A", "Question A", GRAMMAR))
                .contains("I will give you a sentence. " +
                        "Check if the sentence has grammatical mistakes. " +
                        "It is not a mistake if the sentence starts with \"How to\". " +
                        "The sentence is `Question A`.");
        assertThat(factory.getPrompt(InteractionType.GRAMMAR, "Theme A", "Question A", SHORT)).isEmpty();
        assertThat(factory.getPrompt(InteractionType.GRAMMAR, "Theme A", "Question A", LONG)).isEmpty();
        assertThat(factory.getPrompt(InteractionType.GRAMMAR, "Theme A", "Question A", GCP)).isEmpty();
    }

    @Test
    void fact() {
        assertThat(factory.getPrompt(FACT, "Theme A", "Question A", GRAMMAR))
                .contains("Check is this sentence factually correct in context of `Theme A`: `Question A`? " +
                        "Format your answer into Markdown. ");
        assertThat(factory.getPrompt(InteractionType.GRAMMAR, "Theme A", "Question A", SHORT)).isEmpty();
        assertThat(factory.getPrompt(InteractionType.GRAMMAR, "Theme A", "Question A", LONG)).isEmpty();
        assertThat(factory.getPrompt(InteractionType.GRAMMAR, "Theme A", "Question A", GCP)).isEmpty();
    }
}