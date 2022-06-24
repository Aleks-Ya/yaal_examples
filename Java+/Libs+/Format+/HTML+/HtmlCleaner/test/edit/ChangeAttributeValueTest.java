package edit;

import org.htmlcleaner.HtmlCleaner;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ChangeAttributeValueTest {
    private final HtmlCleaner cleaner = new HtmlCleaner();

    @Test
    void changeAttributeValue() {
        var html = "<html><body><a href='http://google.com'>Google page</a></body></html>";
        var htmlTag = cleaner.clean(html);
        var bodyTag = htmlTag.getChildTags()[1];
        var aTag = bodyTag.getChildTags()[0];
        var hrefAttr = "href";
        var oldHref = aTag.getAttributeByName(hrefAttr);
        assertThat(oldHref).isEqualTo("http://google.com");
        var newHref = "google.ru";
        aTag.addAttribute(hrefAttr, newHref);
        assertThat(aTag.getAttributeByName(hrefAttr)).isEqualTo(newHref);
    }
}
