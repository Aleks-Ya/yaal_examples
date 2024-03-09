package gptui.model.storage;

import com.google.common.jimfs.Jimfs;
import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import gptui.ui.TestingData.I3;
import org.junit.jupiter.api.Test;

import java.nio.file.FileSystem;

import static com.google.common.jimfs.Configuration.unix;
import static gptui.model.storage.AnswerType.GRAMMAR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StorageModelTest {
    private final FileSystem fileSystem = Jimfs.newFileSystem(unix());
    private final StorageModel storage = new StorageModelImpl(new StorageFilesystem(fileSystem));

    @Test
    void newInteractionId() {
        assertThat(storage.newInteractionId()).isNotNull();
    }

    @Test
    void updateInteraction() {
        assertThat(storage.readInteraction(I1.INTERACTION.id())).isEmpty();
        storage.saveTheme(I1.THEME);
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
        storage.saveTheme(I1.THEME);
        storage.saveTheme(I2.THEME);
        storage.saveInteraction(I1.INTERACTION);
        storage.saveInteraction(I2.INTERACTION);
        assertThat(storage.readAllInteractions()).containsExactlyInAnyOrder(I1.INTERACTION, I2.INTERACTION);
    }

    @Test
    void readInteraction() {
        var interactionId = new InteractionId(1L);
        assertThat(storage.readInteraction(interactionId)).isEmpty();
        storage.saveTheme(I1.THEME);
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
        storage.saveTheme(I1.THEME);
        storage.saveTheme(I2.THEME);
        storage.saveTheme(I3.THEME);
        storage.saveInteraction(I1.INTERACTION);
        storage.saveInteraction(I2.INTERACTION);
        storage.saveInteraction(I3.INTERACTION);
        assertThat(storage.readAllInteractions()).containsExactlyInAnyOrder(I1.INTERACTION, I2.INTERACTION, I3.INTERACTION);
    }

    @Test
    void deleteInteraction() {
        assertThat(storage.readAllInteractions()).isEmpty();
        storage.saveTheme(I1.THEME);
        storage.saveTheme(I2.THEME);
        storage.saveInteraction(I1.INTERACTION);
        storage.saveInteraction(I2.INTERACTION);
        assertThat(storage.readAllInteractions()).containsExactlyInAnyOrder(I1.INTERACTION, I2.INTERACTION);

        storage.deleteInteraction(I1.INTERACTION.id());
        assertThat(storage.readAllInteractions()).containsExactlyInAnyOrder(I2.INTERACTION);
    }

    @Test
    void getThemesSeveral() {
        var themeTitle1 = "AAA";
        var themeTitle2 = "BBB";
        var themeTitle4 = "CCC";
        var theme1 = new Theme(new ThemeId(1L), themeTitle1);
        var theme2 = new Theme(new ThemeId(2L), themeTitle2);
        var theme4 = new Theme(new ThemeId(4L), themeTitle4);
        var id1 = 1693929900L;
        var id2 = id1 - 1;
        var id3 = id1 + 1;
        var id4 = id1 + 2;
        var id5 = id1 - 2;
        storage.saveTheme(theme1);
        storage.saveTheme(theme2);
        storage.saveTheme(theme4);
        storage.saveInteraction(newInteraction(id1, theme1));
        storage.saveInteraction(newInteraction(id2, theme2));
        storage.saveInteraction(newInteraction(id3, theme2));
        storage.saveInteraction(newInteraction(id4, theme4));
        storage.saveInteraction(newInteraction(id5, theme2));
        assertThat(storage.getThemes().stream().map(Theme::title)).containsExactly(themeTitle2, themeTitle4, themeTitle1);
    }

    @Test
    void getThemesSingle() {
        var themeTitle = "AAA";
        var theme = new Theme(new ThemeId(1L), themeTitle);
        storage.saveTheme(theme);
        storage.saveInteraction(newInteraction(1693929900L, theme));
        assertThat(storage.getThemes().stream().map(Theme::title)).containsExactly(themeTitle);
    }

    @Test
    void getThemesEmpty() {
        assertThat(storage.getThemes()).isEmpty();
    }

    @Test
    void getThemesSeveralStart() {
        var storage1 = new StorageModelImpl(new StorageFilesystem(fileSystem));
        var themeTitle1 = "AAA";
        var themeTitle2 = "BBB";
        var themeTitle4 = "CCC";
        var theme1 = new Theme(new ThemeId(1L), themeTitle1);
        var theme2 = new Theme(new ThemeId(2L), themeTitle2);
        var theme4 = new Theme(new ThemeId(4L), themeTitle4);
        storage1.saveTheme(theme1);
        storage1.saveTheme(theme2);
        storage1.saveTheme(theme4);
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

        var storage2 = new StorageModelImpl(new StorageFilesystem(fileSystem));
        assertThat(storage2.getThemes().stream().map(Theme::title)).containsExactly(themeTitle4, themeTitle2, themeTitle1);
    }

    @Test
    void getThemesSingleStart() {
        var storage1 = new StorageModelImpl(new StorageFilesystem(fileSystem));
        var themeTitle = "AAA";
        var theme = new Theme(new ThemeId(1L), themeTitle);
        storage1.saveTheme(theme);
        storage1.saveInteraction(newInteraction(1693929900L, theme));

        var storage2 = new StorageModelImpl(new StorageFilesystem(fileSystem));
        assertThat(storage2.getThemes()).containsExactly(theme);
    }

    @Test
    void getThemesEmptyStart() {
        assertThat(storage.getThemes()).isEmpty();
    }

    @Test
    void addTheme() {
        var title = "Java";
        var theme1 = storage.addTheme(title);
        assertThat(theme1).isEqualTo(new Theme(new ThemeId(1L), title));
        var theme2 = storage.addTheme(title);
        assertThat(theme2).isEqualTo(theme1);
    }

    @Test
    void getTheme() {
        var themeId = new ThemeId(1L);
        assertThatThrownBy(() -> storage.getTheme(themeId))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Theme was not found by id: ThemeId[id=1]");
        var title = "Java";
        var theme1 = storage.addTheme(title);
        assertThat(theme1).isEqualTo(new Theme(new ThemeId(1L), title));
        var theme2 = storage.getTheme(themeId);
        assertThat(theme2).isEqualTo(theme1);
    }

    @Test
    void saveTheme() {
        var theme1 = new Theme(new ThemeId(5L), "Java");
        var theme2 = new Theme(new ThemeId(10L), "Scala");
        storage.saveTheme(theme1);
        storage.saveTheme(theme2);
        assertThat(storage.getTheme(theme1.id())).isEqualTo(theme1);
        assertThat(storage.getTheme(theme2.id())).isEqualTo(theme2);
    }

    private static Interaction newInteraction(long id, Theme theme) {
        return new Interaction(new InteractionId(id), null, theme.id(), null, null);
    }
}