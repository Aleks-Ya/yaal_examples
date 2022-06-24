import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class ParseTest {

    @Test
    void findDiv() {
        var html = "<html>" +
                "<head>" +
                "<title>First parse</title>" +
                "</head>"
                + "<body>" +
                "<p>Parsed HTML into a doc.</p>" +
                "<div>Part 1</div>" +
                "<div>Part 2</div>" +
                "</body>" +
                "</html>";
        var doc = Jsoup.parse(html);

        assertThat(doc.title()).isEqualTo("First parse");

        var divs = doc.body().getElementsByTag("div");
        assertThat(divs).hasSize(2);
        assertThat(divs.get(0).text()).isEqualTo("Part 1");
        assertThat(divs.get(1).text()).isEqualTo("Part 2");
    }
}
