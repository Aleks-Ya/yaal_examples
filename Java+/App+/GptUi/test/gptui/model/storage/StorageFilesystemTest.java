package gptui.model.storage;

import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.jimfs.Configuration.unix;
import static gptui.model.storage.InteractionType.QUESTION;
import static org.assertj.core.api.Assertions.assertThat;

class StorageFilesystemTest {
    private final StorageFilesystem storageFileSystem = new StorageFilesystem(Jimfs.newFileSystem(unix()));

    @Test
    void readAllInteractions() {
        var interactions = storageFileSystem.readAllInteractions();
        assertThat(interactions).isEmpty();
    }

    @Test
    void saveInteraction() {
        assertThat(storageFileSystem.readAllInteractions()).isEmpty();
        var interaction1 = new Interaction(new InteractionId(1L), QUESTION, new ThemeId(1L), "question1", null);
        var interaction2 = new Interaction(new InteractionId(2L), QUESTION, new ThemeId(2L), "question2", null);
        storageFileSystem.saveInteraction(interaction1);
        storageFileSystem.saveInteraction(interaction2);
        assertThat(storageFileSystem.readAllInteractions()).containsExactlyInAnyOrder(interaction1, interaction2);
    }

    @Test
    void deleteInteraction() {
        assertThat(storageFileSystem.readAllInteractions()).isEmpty();
        var interaction1 = new Interaction(new InteractionId(1L), QUESTION, new ThemeId(1L), "question1", null);
        var interaction2 = new Interaction(new InteractionId(2L), QUESTION, new ThemeId(2L), "question2", null);
        storageFileSystem.saveInteraction(interaction1);
        storageFileSystem.saveInteraction(interaction2);
        assertThat(storageFileSystem.readAllInteractions()).containsExactlyInAnyOrder(interaction1, interaction2);

        storageFileSystem.deleteInteraction(interaction1.id());
        assertThat(storageFileSystem.readAllInteractions()).containsExactlyInAnyOrder(interaction2);
    }

    @Test
    void saveReadThemes() {
        assertThat(storageFileSystem.readThemes()).isEmpty();
        var theme1 = new Theme(new ThemeId(1L), "Java");
        var theme2 = new Theme(new ThemeId(2L), "Scala");
        var themes = List.of(theme1, theme2);
        storageFileSystem.saveThemes(themes);
        assertThat(storageFileSystem.readThemes()).containsExactly(theme1, theme2);
    }

}