package edit;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ChangeAttributeValueTest {
    private final HtmlCleaner cleaner = new HtmlCleaner();

    @Test
    public void changeAttributeValue() {
        String html = "<html><body><a href='http://google.com'>Google page</a></body></html>";
        TagNode htmlTag = cleaner.clean(html);
        TagNode bodyTag = htmlTag.getChildTags()[1];
        TagNode aTag = bodyTag.getChildTags()[0];
        String hrefAttr = "href";
        String oldHref = aTag.getAttributeByName(hrefAttr);
        assertThat(oldHref, equalTo("http://google.com"));
        String newHref = "google.ru";
        aTag.addAttribute(hrefAttr, newHref);
        assertThat(aTag.getAttributeByName(hrefAttr), equalTo(newHref));
    }
}
