package gptui.storage;

import com.google.common.jimfs.Jimfs;
import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import gptui.ui.TestingData.I3;
import org.junit.jupiter.api.Test;

import java.nio.file.FileSystem;

import static com.google.common.jimfs.Configuration.unix;
import static gptui.storage.AnswerType.GRAMMAR;
import static org.assertj.core.api.Assertions.assertThat;

class InteractionStorageTest {
    private final FileSystem fileSystem = Jimfs.newFileSystem(unix());
    private final InteractionStorage storage = new InteractionStorageImpl(new InteractionStorageFilesystem(fileSystem));

    @Test
    void newInteractionId() {
        assertThat(storage.newInteractionId()).isNotNull();
    }

    @Test
    void updateInteraction() {
        assertThat(storage.readInteraction(I1.INTERACTION.id())).isEmpty();
        storage.saveInteraction(I1.INTERACTION);
        assertThat(storage.readInteraction(I1.INTERACTION.id())).contains(I1.INTERACTION);
        var newGrammarPrompt = "new GRAMMAR prompt";
        storage.updateInteraction(I1.INTERACTION.id(), i -> i.withAnswer(GRAMMAR, answer -> answer.withPrompt(newGrammarPrompt)));
        assertThat(storage.readInteraction(I1.INTERACTION.id()))
                .map(interaction -> interaction.getAnswer(GRAMMAR))
                .map(answer -> answer.orElseThrow().prompt())
                .contains(newGrammarPrompt);
    }

    @Test
    void saveInteraction() {
        assertThat(storage.readAllInteractions()).isEmpty();
        storage.saveInteraction(I1.INTERACTION);
        storage.saveInteraction(I2.INTERACTION);
        assertThat(storage.readAllInteractions()).containsExactlyInAnyOrder(I1.INTERACTION, I2.INTERACTION);
    }

    @Test
    void readInteraction() {
        var interactionId = new InteractionId(1L);
        assertThat(storage.readInteraction(interactionId)).isEmpty();
        storage.saveInteraction(I1.INTERACTION);
        assertThat(storage.readInteraction(I1.INTERACTION.id())).contains(I1.INTERACTION);
    }

    @Test
    void readNullInteraction() {
        assertThat(storage.readInteraction(null)).isEmpty();
    }

    @Test
    void readAllInteractions() {
        assertThat(storage.readAllInteractions()).isEmpty();
        storage.saveInteraction(I1.INTERACTION);
        storage.saveInteraction(I2.INTERACTION);
        storage.saveInteraction(I3.INTERACTION);
        assertThat(storage.readAllInteractions()).containsExactlyInAnyOrder(I1.INTERACTION, I2.INTERACTION, I3.INTERACTION);
    }

    @Test
    void deleteInteraction() {
        assertThat(storage.readAllInteractions()).isEmpty();
        storage.saveInteraction(I1.INTERACTION);
        storage.saveInteraction(I2.INTERACTION);
        assertThat(storage.readAllInteractions()).containsExactlyInAnyOrder(I1.INTERACTION, I2.INTERACTION);

        storage.deleteInteraction(I1.INTERACTION.id());
        assertThat(storage.readAllInteractions()).containsExactlyInAnyOrder(I2.INTERACTION);
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
        var storage1 = new InteractionStorageImpl(new InteractionStorageFilesystem(fileSystem));
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

        var storage2 = new InteractionStorageImpl(new InteractionStorageFilesystem(fileSystem));
        assertThat(storage2.getThemes()).containsExactly(theme4, theme2, theme1);
    }

    @Test
    void getThemesSingleStart() {
        var storage1 = new InteractionStorageImpl(new InteractionStorageFilesystem(fileSystem));
        var theme = "AAA";
        storage1.saveInteraction(newInteraction(1693929900L, theme));

        var storage2 = new InteractionStorageImpl(new InteractionStorageFilesystem(fileSystem));
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