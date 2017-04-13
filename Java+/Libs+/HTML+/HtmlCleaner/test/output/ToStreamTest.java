package output;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleHtmlSerializer;
import org.htmlcleaner.TagNode;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ToStreamTest {
    @Test
    public void toOutputStream() throws IOException {
        String html = "<html>" +
                "<head>" +
                "<title>First parse</title>" +
                "</head>" +
                "<body>" +
                "<p>My HTML</p>" +
                "</body>" +
                "</html>";
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode htmlTag = cleaner.clean(html);
        CleanerProperties props = cleaner.getProperties();
        SimpleHtmlSerializer serializer = new SimpleHtmlSerializer(props);
        OutputStream os = new ByteArrayOutputStream();
        serializer.writeToStream(htmlTag, os);

        String expHtml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<html>" +
                "<head><title>First parse</title></head>" +
                "<body><p>My HTML</p></body>" +
                "</html>";
        assertThat(os, hasToString(equalTo(expHtml)));
    }
}
