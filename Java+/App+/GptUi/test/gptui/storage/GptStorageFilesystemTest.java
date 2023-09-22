package gptui.storage;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GptStorageFilesystemTest {
    @Test
    void readAllInteractions() {
        var storageFileSystem = new GptStorageFilesystem(Jimfs.newFileSystem(Configuration.unix()));
        var interactions = storageFileSystem.readAllInteractions();
        assertThat(interactions).isEmpty();
    }

    @Test
    void saveInteraction() {
        var storageFileSystem = new GptStorageFilesystem(Jimfs.newFileSystem(Configuration.unix()));
        assertThat(storageFileSystem.readAllInteractions()).isEmpty();
        var interaction1 = new Interaction(new InteractionId(1L), "theme1", "question1", null);
        var interaction2 = new Interaction(new InteractionId(2L), "theme2", "question2", null);
        storageFileSystem.saveInteraction(interaction1);
        storageFileSystem.saveInteraction(interaction2);
        assertThat(storageFileSystem.readAllInteractions()).containsExactlyInAnyOrder(interaction1, interaction2);
    }
}