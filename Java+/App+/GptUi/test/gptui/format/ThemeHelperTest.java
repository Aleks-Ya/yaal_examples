package gptui.format;

import gptui.storage.Interaction;
import gptui.storage.InteractionId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ThemeHelperTest {
    private final ThemeHelper sorter = new ThemeHelper();

    @Test
    void interactionsToThemeList() {
        var theme1 = "AAA";
        var theme2 = "BBB";
        var theme4 = "CCC";
        var id1 = 1693929900L;
        var id2 = id1 - 1;
        var id3 = id1 + 1;
        var id4 = id1 + 2;
        var id5 = id1 - 2;
        var interactions = List.of(
                newInteraction(id1, theme1),
                newInteraction(id2, theme2),
                newInteraction(id3, theme2),
                newInteraction(id4, theme4),
                newInteraction(id5, theme2)
        );
        var sorted = sorter.interactionsToThemeList(interactions);
        assertThat(sorted).containsExactly(theme4, theme2, theme1);
    }

    @Test
    void singleInteraction() {
        var theme = "AAA";
        var interactions = List.of(newInteraction(1693929900L, theme));
        var sorted = sorter.interactionsToThemeList(interactions);
        assertThat(sorted).containsExactly(theme);
    }

    @Test
    void noInteractions() {
        assertThat(sorter.interactionsToThemeList(List.of())).isEmpty();
    }

    private static Interaction newInteraction(long id, String theme) {
        return new Interaction(new InteractionId(id), null, theme, null, null);
    }
}