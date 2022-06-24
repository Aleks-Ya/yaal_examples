package output;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleHtmlSerializer;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class ToStreamTest {
    @Test
    void toOutputStream() throws IOException {
        var html = "<html>" +
                "<head>" +
                "<title>First parse</title>" +
                "</head>" +
                "<body>" +
                "<p>My HTML</p>" +
                "</body>" +
                "</html>";
        var cleaner = new HtmlCleaner();
        var htmlTag = cleaner.clean(html);
        var props = cleaner.getProperties();
        var serializer = new SimpleHtmlSerializer(props);
        OutputStream os = new ByteArrayOutputStream();
        serializer.writeToStream(htmlTag, os);

        var expHtml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<html>" +
                "<head><title>First parse</title></head>" +
                "<body><p>My HTML</p></body>" +
                "</html>";
        assertThat(os).hasToString(expHtml);
    }
}
