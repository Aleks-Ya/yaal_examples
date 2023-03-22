package asciitable;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.asciitable.CWC_LongestWord;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TableTest {

    @Test
    void render() {
        var at = new AsciiTable();
        at.addRule();
        at.addRow("row 1 col 1", "row 1 col 2");
        at.addRule();
        at.addRow("row 2 col 1", "row 2 col 2");
        at.addRule();
        var rend = at.render();
        assertThat(rend).isEqualTo("""
                ┌───────────────────────────────────────┬──────────────────────────────────────┐
                │row 1 col 1                            │row 1 col 2                           │
                ├───────────────────────────────────────┼──────────────────────────────────────┤
                │row 2 col 1                            │row 2 col 2                           │
                └───────────────────────────────────────┴──────────────────────────────────────┘""");
    }

    @Test
    void columnSpan() {
        var at = new AsciiTable();
        at.addRule();
        at.addRow(null, null, null, null, "span all 5 columns");
        at.addRule();
        at.addRow(null, null, null, "span 4 columns", "just 1 column");
        at.addRule();
        at.addRow(null, null, "span 3 columns", null, "span 2 columns");
        at.addRule();
        at.addRow(null, "span 2 columns", null, null, "span 3 columns");
        at.addRule();
        at.addRow("just 1 column", null, null, null, "span 4 columns");
        at.addRule();
        at.addRow("just 1 column", "just 1 column", "just 1 column", "just 1 column", "just 1 column");
        at.addRule();
        var rend = at.render();
        assertThat(rend).isEqualTo("""
                ┌──────────────────────────────────────────────────────────────────────────────┐
                │span all 5 columns                                                            │
                ├───────────────────────────────────────────────────────────────┬──────────────┤
                │span 4 columns                                                 │just 1 column │
                ├───────────────────────────────────────────────┬───────────────┴──────────────┤
                │span 3 columns                                 │span 2 columns                │
                ├───────────────────────────────┬───────────────┴──────────────────────────────┤
                │span 2 columns                 │span 3 columns                                │
                ├───────────────┬───────────────┴──────────────────────────────────────────────┤
                │just 1 column  │span 4 columns                                                │
                ├───────────────┼───────────────┬───────────────┬───────────────┬──────────────┤
                │just 1 column  │just 1 column  │just 1 column  │just 1 column  │just 1 column │
                └───────────────┴───────────────┴───────────────┴───────────────┴──────────────┘""");
    }

    @Test
    void columnWidth_LongestLine() {
        var at = new AsciiTable();
        at.addRule();
        at.addRow("row 1 col 1", "row 1 col 2");
        at.addRule();
        at.addRow("row 2 col 1", "row 2 col 2");
        at.addRule();
        at.getRenderer().setCWC(new CWC_LongestLine());
        var rend = at.render();
        assertThat(rend).isEqualTo("""
                ┌───────────┬───────────┐
                │row 1 col 1│row 1 col 2│
                ├───────────┼───────────┤
                │row 2 col 1│row 2 col 2│
                └───────────┴───────────┘""");
    }

    @Test
    void columnWidth_LongestWord() {
        var at = new AsciiTable();
        at.addRule();
        at.addRow("row 1 col 1", "row 1 col 2");
        at.addRule();
        at.addRow("row 2 col 1", "row 2 col 2");
        at.addRule();
        at.getRenderer().setCWC(new CWC_LongestWord());
        var rend = at.render();
        assertThat(rend).isEqualTo("""
                ┌───┬───┐
                │row│row│
                │col│1  │
                │1  │col│
                │1  │2  │
                ├───┼───┤
                │row│row│
                │2  │col│
                │col│2  │
                │1  │2  │
                └───┴───┘""");
    }
}
