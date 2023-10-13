package gptui.storage;

import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.Test;

import static com.google.common.jimfs.Configuration.unix;
import static gptui.storage.InteractionType.QUESTION;
import static org.assertj.core.api.Assertions.assertThat;

class GptStorageTest {
    private final GptStorage storage = new GptStorageImpl(new GptStorageFilesystem(Jimfs.newFileSystem(unix())));

    @Test
    void newInteractionId() {
        assertThat(storage.newInteractionId()).isNotNull();
    }

    @Test
    void updateInteraction() {
        var interactionId = new InteractionId(1L);
        assertThat(storage.readInteraction(interactionId)).isEmpty();
        var interaction = new Interaction(interactionId, QUESTION, "theme1", "question1", null);
        storage.saveInteraction(interaction);
        assertThat(storage.readInteraction(interaction.id())).contains(interaction);
        storage.updateInteraction(interaction.id(), i -> i.withTheme("theme2"));
        assertThat(storage.readInteraction(interaction.id())).contains(interaction.withTheme("theme2"));
    }

    @Test
    void saveInteraction() {
        assertThat(storage.readAllInteractions()).isEmpty();
        var interaction1 = new Interaction(new InteractionId(1L), QUESTION, "theme1", "question1", null);
        var interaction2 = new Interaction(new InteractionId(2L), QUESTION, "theme2", "question2", null);
        storage.saveInteraction(interaction1);
        storage.saveInteraction(interaction2);
        assertThat(storage.readAllInteractions()).containsExactlyInAnyOrder(interaction1, interaction2);
    }

    @Test
    void readInteraction() {
        var interactionId = new InteractionId(1L);
        assertThat(storage.readInteraction(interactionId)).isEmpty();
        var interaction = new Interaction(interactionId, QUESTION, "theme1", "question1", null);
        storage.saveInteraction(interaction);
        assertThat(storage.readInteraction(interaction.id())).contains(interaction);
    }

    @Test
    void readAllInteractions() {
        assertThat(storage.readAllInteractions()).isEmpty();
        var interaction1 = new Interaction(new InteractionId(1L), QUESTION, "theme1", "question1", null);
        var interaction2 = new Interaction(new InteractionId(2L), QUESTION, "theme2", "question2", null);
        var interaction3 = new Interaction(new InteractionId(3L), QUESTION, "theme3", "question3", null);
        storage.saveInteraction(interaction1);
        storage.saveInteraction(interaction2);
        storage.saveInteraction(interaction3);
        assertThat(storage.readAllInteractions()).containsExactlyInAnyOrder(interaction1, interaction2, interaction3);
    }

    @Test
    void deleteInteraction() {
        assertThat(storage.readAllInteractions()).isEmpty();
        var interaction1 = new Interaction(new InteractionId(1L), QUESTION, "theme1", "question1", null);
        var interaction2 = new Interaction(new InteractionId(2L), QUESTION, "theme2", "question2", null);
        storage.saveInteraction(interaction1);
        storage.saveInteraction(interaction2);
        assertThat(storage.readAllInteractions()).containsExactlyInAnyOrder(interaction1, interaction2);

        storage.deleteInteraction(interaction1.id());
        assertThat(storage.readAllInteractions()).containsExactlyInAnyOrder(interaction2);
    }
}