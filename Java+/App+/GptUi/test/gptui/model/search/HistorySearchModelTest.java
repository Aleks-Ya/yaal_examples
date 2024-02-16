package gptui.model.search;

import gptui.ui.TestingData.I1;
import gptui.ui.TestingData.I2;
import gptui.ui.TestingData.I3;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HistorySearchModelTest {
    private final HistorySearchModel historySearchModel = new HistorySearchModelImpl();

    @Test
    void searchEmptyIndex() {
        assertThat(historySearchModel.search("table")).isEmpty();
    }

    @Test
    void indexDocument() {
        assertThat(historySearchModel.search("theme")).isEmpty();
        historySearchModel.indexDocument(I1.INTERACTION);
        assertThat(historySearchModel.search("theme")).containsExactly(I1.INTERACTION.id());
    }

    @Test
    void indexDocuments() {
        assertThat(historySearchModel.search("theme")).isEmpty();
        historySearchModel.indexDocuments(List.of(I1.INTERACTION, I2.INTERACTION, I3.INTERACTION));
        assertThat(historySearchModel.search("theme"))
                .containsExactly(I1.INTERACTION.id(), I2.INTERACTION.id(), I3.INTERACTION.id());
    }

    @Test
    void search() {
        historySearchModel.indexDocuments(List.of(I1.INTERACTION, I2.INTERACTION, I3.INTERACTION));
        assertThat(historySearchModel.search("absent")).isEmpty();
        assertThat(historySearchModel.search("theme"))
                .containsExactly(I1.INTERACTION.id(), I2.INTERACTION.id(), I3.INTERACTION.id());
        assertThat(historySearchModel.search("Themes"))
                .containsExactly(I1.INTERACTION.id(), I2.INTERACTION.id(), I3.INTERACTION.id());
        assertThat(historySearchModel.search("question"))
                .containsExactly(I1.INTERACTION.id(), I2.INTERACTION.id(), I3.INTERACTION.id());
        assertThat(historySearchModel.search("Questions"))
                .containsExactly(I1.INTERACTION.id(), I2.INTERACTION.id(), I3.INTERACTION.id());
    }
}