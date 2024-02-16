package gptui.model.storage;

import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.Test;

import static com.google.common.jimfs.Configuration.unix;
import static gptui.model.storage.InteractionType.QUESTION;
import static org.assertj.core.api.Assertions.assertThat;

class StorageModelFilesystemTest {
    private final InteractionStorageFilesystem storageFileSystem = new InteractionStorageFilesystem(Jimfs.newFileSystem(unix()));

    @Test
    void readAllInteractions() {
        var interactions = storageFileSystem.readAllInteractions();
        assertThat(interactions).isEmpty();
    }

    @Test
    void saveInteraction() {
        assertThat(storageFileSystem.readAllInteractions()).isEmpty();
        var interaction1 = new Interaction(new InteractionId(1L), QUESTION, "theme1", "question1", null);
        var interaction2 = new Interaction(new InteractionId(2L), QUESTION, "theme2", "question2", null);
        storageFileSystem.saveInteraction(interaction1);
        storageFileSystem.saveInteraction(interaction2);
        assertThat(storageFileSystem.readAllInteractions()).containsExactlyInAnyOrder(interaction1, interaction2);
    }

    @Test
    void deleteInteraction() {
        assertThat(storageFileSystem.readAllInteractions()).isEmpty();
        var interaction1 = new Interaction(new InteractionId(1L), QUESTION, "theme1", "question1", null);
        var interaction2 = new Interaction(new InteractionId(2L), QUESTION, "theme2", "question2", null);
        storageFileSystem.saveInteraction(interaction1);
        storageFileSystem.saveInteraction(interaction2);
        assertThat(storageFileSystem.readAllInteractions()).containsExactlyInAnyOrder(interaction1, interaction2);

        storageFileSystem.deleteInteraction(interaction1.id());
        assertThat(storageFileSystem.readAllInteractions()).containsExactlyInAnyOrder(interaction2);
    }
}