import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

public class ParseTest {

    @Test
    public void findDiv() {
        String html = "<html>" +
                "<head>" +
                "<title>First parse</title>" +
                "</head>"
                + "<body>" +
                "<p>Parsed HTML into a doc.</p>" +
                "<div>Part 1</div>" +
                "<div>Part 2</div>" +
                "</body>" +
                "</html>";
        Document doc = Jsoup.parse(html);

        assertThat(doc.title(), equalTo("First parse"));

        Elements divs = doc.body().getElementsByTag("div");
        assertThat(divs, hasSize(2));
        assertThat(divs.get(0).text(), equalTo("Part 1"));
        assertThat(divs.get(1).text(), equalTo("Part 2"));
    }
}
