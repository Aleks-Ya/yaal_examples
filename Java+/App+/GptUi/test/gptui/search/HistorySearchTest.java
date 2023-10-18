package gptui.search;

import org.junit.jupiter.api.Test;

import java.util.List;

import static gptui.ui.TestingData.INTERACTION_1;
import static gptui.ui.TestingData.INTERACTION_2;
import static gptui.ui.TestingData.INTERACTION_3;
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
        historySearch.indexDocument(INTERACTION_1);
        assertThat(historySearch.search("theme")).containsExactly(INTERACTION_1.id());
    }

    @Test
    void indexDocuments() {
        assertThat(historySearch.search("theme")).isEmpty();
        historySearch.indexDocuments(List.of(INTERACTION_1, INTERACTION_2, INTERACTION_3));
        assertThat(historySearch.search("theme"))
                .containsExactly(INTERACTION_1.id(), INTERACTION_2.id(), INTERACTION_3.id());
    }

    @Test
    void search() {
        historySearch.indexDocuments(List.of(INTERACTION_1, INTERACTION_2, INTERACTION_3));
        assertThat(historySearch.search("absent")).isEmpty();
        assertThat(historySearch.search("theme"))
                .containsExactly(INTERACTION_1.id(), INTERACTION_2.id(), INTERACTION_3.id());
        assertThat(historySearch.search("Themes"))
                .containsExactly(INTERACTION_1.id(), INTERACTION_2.id(), INTERACTION_3.id());
        assertThat(historySearch.search("question"))
                .containsExactly(INTERACTION_1.id(), INTERACTION_2.id(), INTERACTION_3.id());
        assertThat(historySearch.search("Questions"))
                .containsExactly(INTERACTION_1.id(), INTERACTION_2.id(), INTERACTION_3.id());
    }
}