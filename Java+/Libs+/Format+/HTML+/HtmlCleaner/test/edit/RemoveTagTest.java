package edit;

import org.htmlcleaner.HtmlCleaner;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class RemoveTagTest {
    private final HtmlCleaner cleaner = new HtmlCleaner();

    @Test
    void removeTag() {
        var html = "<html><head><title>First parse</title></head></html>";
        var htmlTag = cleaner.clean(html);
        var headTag = htmlTag.getChildTags()[0];
        assertThat(headTag.getChildTags()).hasSize(1);
        var titleTag = headTag.getChildTags()[0];
        assertThat(titleTag.getName()).isEqualTo("title");
        assertThat(titleTag.removeFromTree()).isTrue();
        assertThat(headTag.getChildTags()).isEmpty();
    }
}
