package edit;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemoveTagTest {
    private final HtmlCleaner cleaner = new HtmlCleaner();

    @Test
    public void removeTag() {
        String html = "<html><head><title>First parse</title></head></html>";
        TagNode htmlTag = cleaner.clean(html);
        TagNode headTag = htmlTag.getChildTags()[0];
        assertThat(headTag.getChildTags(), arrayWithSize(1));
        TagNode titleTag = headTag.getChildTags()[0];
        assertThat(titleTag.getName(), equalTo("title"));
        assertTrue(titleTag.removeFromTree());
        assertThat(headTag.getChildTags(), arrayWithSize(0));
    }
}
