package gptui.model.search;

import gptui.ui.BaseGptUiTest;
import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import gptui.ui.TestingData.I3;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HistorySearchModelTest extends BaseGptUiTest {
    @Override
    public void init() {
        storage.saveTheme(I1.THEME);
        storage.saveTheme(I2.THEME);
        storage.saveTheme(I3.THEME);
    }

    @Test
    void searchEmptyIndex() {
        assertThat(search.search("table")).isEmpty();
    }

    @Test
    void indexDocument() {
        assertThat(search.search("theme")).isEmpty();
        search.indexDocument(I1.INTERACTION);
        assertThat(search.search("theme")).containsExactly(I1.INTERACTION.id());
    }

    @Test
    void indexDocuments() {
        assertThat(search.search("theme")).isEmpty();
        search.indexDocuments(List.of(I1.INTERACTION, I2.INTERACTION, I3.INTERACTION));
        assertThat(search.search("theme"))
                .containsExactly(I1.INTERACTION.id(), I2.INTERACTION.id(), I3.INTERACTION.id());
    }

    @Test
    void search() {
        search.indexDocuments(List.of(I1.INTERACTION, I2.INTERACTION, I3.INTERACTION));
        assertThat(search.search("absent")).isEmpty();
        assertThat(search.search("theme"))
                .containsExactly(I1.INTERACTION.id(), I2.INTERACTION.id(), I3.INTERACTION.id());
        assertThat(search.search("Themes"))
                .containsExactly(I1.INTERACTION.id(), I2.INTERACTION.id(), I3.INTERACTION.id());
        assertThat(search.search("question"))
                .containsExactly(I1.INTERACTION.id(), I2.INTERACTION.id(), I3.INTERACTION.id());
        assertThat(search.search("Questions"))
                .containsExactly(I1.INTERACTION.id(), I2.INTERACTION.id(), I3.INTERACTION.id());
    }
}