package gptui.storage;

import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.Test;

import java.nio.file.FileSystem;

import static com.google.common.jimfs.Configuration.unix;
import static gptui.storage.InteractionType.QUESTION;
import static org.assertj.core.api.Assertions.assertThat;

class GptStorageTest {
    private final FileSystem fileSystem = Jimfs.newFileSystem(unix());
    private final GptStorage storage = new GptStorageImpl(new GptStorageFilesystem(fileSystem));

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
    void readNullInteraction() {
        assertThat(storage.readInteraction(null)).isEmpty();
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

    @Test
    void getThemesSeveral() {
        var theme1 = "AAA";
        var theme2 = "BBB";
        var theme4 = "CCC";
        var id1 = 1693929900L;
        var id2 = id1 - 1;
        var id3 = id1 + 1;
        var id4 = id1 + 2;
        var id5 = id1 - 2;
        storage.saveInteraction(newInteraction(id1, theme1));
        storage.saveInteraction(newInteraction(id2, theme2));
        storage.saveInteraction(newInteraction(id3, theme2));
        storage.saveInteraction(newInteraction(id4, theme4));
        storage.saveInteraction(newInteraction(id5, theme2));
        assertThat(storage.getThemes()).containsExactly(theme2, theme4, theme1);
    }

    @Test
    void getThemesSingle() {
        var theme = "AAA";
        storage.saveInteraction(newInteraction(1693929900L, theme));
        assertThat(storage.getThemes()).containsExactly(theme);
    }

    @Test
    void getThemesEmpty() {
        assertThat(storage.getThemes()).isEmpty();
    }

    @Test
    void getThemesSeveralStart() {
        var storage1 = new GptStorageImpl(new GptStorageFilesystem(fileSystem));
        var theme1 = "AAA";
        var theme2 = "BBB";
        var theme4 = "CCC";
        var id1 = 1693929900L;
        var id2 = id1 - 1;
        var id3 = id1 + 1;
        var id4 = id1 + 2;
        var id5 = id1 - 2;
        storage1.saveInteraction(newInteraction(id1, theme1));
        storage1.saveInteraction(newInteraction(id2, theme2));
        storage1.saveInteraction(newInteraction(id3, theme2));
        storage1.saveInteraction(newInteraction(id4, theme4));
        storage1.saveInteraction(newInteraction(id5, theme2));

        var storage2 = new GptStorageImpl(new GptStorageFilesystem(fileSystem));
        assertThat(storage2.getThemes()).containsExactly(theme4, theme2, theme1);
    }

    @Test
    void getThemesSingleStart() {
        var storage1 = new GptStorageImpl(new GptStorageFilesystem(fileSystem));
        var theme = "AAA";
        storage1.saveInteraction(newInteraction(1693929900L, theme));

        var storage2 = new GptStorageImpl(new GptStorageFilesystem(fileSystem));
        assertThat(storage2.getThemes()).containsExactly(theme);
    }

    @Test
    void getThemesEmptyStart() {
        assertThat(storage.getThemes()).isEmpty();
    }

    private static Interaction newInteraction(long id, String theme) {
        return new Interaction(new InteractionId(id), null, theme, null, null);
    }
}