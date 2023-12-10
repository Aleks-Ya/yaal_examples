package gptui.search;

import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import gptui.ui.TestingData.I3;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HistorySearchTest {
    private final HistorySearch historySearch = new HistorySearchImpl();

    @Test
    void searchEmptyIndex() {
        assertThat(historySearch.search("table")).isEmpty();
    }

    @Test
    void indexDocument() {
        assertThat(historySearch.search("theme")).isEmpty();
        historySearch.indexDocument(I1.INTERACTION);
        assertThat(historySearch.search("theme")).containsExactly(I1.INTERACTION.id());
    }

    @Test
    void indexDocuments() {
        assertThat(historySearch.search("theme")).isEmpty();
        historySearch.indexDocuments(List.of(I1.INTERACTION, I2.INTERACTION, I3.INTERACTION));
        assertThat(historySearch.search("theme"))
                .containsExactly(I1.INTERACTION.id(), I2.INTERACTION.id(), I3.INTERACTION.id());
    }

    @Test
    void search() {
        historySearch.indexDocuments(List.of(I1.INTERACTION, I2.INTERACTION, I3.INTERACTION));
        assertThat(historySearch.search("absent")).isEmpty();
        assertThat(historySearch.search("theme"))
                .containsExactly(I1.INTERACTION.id(), I2.INTERACTION.id(), I3.INTERACTION.id());
        assertThat(historySearch.search("Themes"))
                .containsExactly(I1.INTERACTION.id(), I2.INTERACTION.id(), I3.INTERACTION.id());
        assertThat(historySearch.search("question"))
                .containsExactly(I1.INTERACTION.id(), I2.INTERACTION.id(), I3.INTERACTION.id());
        assertThat(historySearch.search("Questions"))
                .containsExactly(I1.INTERACTION.id(), I2.INTERACTION.id(), I3.INTERACTION.id());
    }
}