import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
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

    /**
     * Find tags in non-structured text.
     */
    @Test
    void findImgInPlainText() {
        var html = """
                This text contains <img src=":/681c0cd67b3d44378f47acc06a5e234e" alt="013bb9bbbc51b7d2145153b7640e7fdc.png" width="324" height="243" class="jop-noMdConv">
                and <img class="jop-noMdConv2" width="284" height="179"
                alt="f7316cd7bda69f9f9649ce5c750eee2e.png"
                src=":/fe3aac71c1e44393a4e54666e697dfc8"
                >
                tags.
                """;
        var doc = Jsoup.parse(html);

        var images = doc.select("img");
        assertThat(images).hasSize(2);

        assertThat(images.get(0))
                .returns("""
                        <img src=":/681c0cd67b3d44378f47acc06a5e234e" alt="013bb9bbbc51b7d2145153b7640e7fdc.png" width="324" height="243" class="jop-noMdConv">""", Node::outerHtml)
                .returns(":/681c0cd67b3d44378f47acc06a5e234e", img -> img.attr("src"))
                .returns("013bb9bbbc51b7d2145153b7640e7fdc.png", img -> img.attr("alt"))
                .returns("324", img -> img.attr("width"))
                .returns("243", img -> img.attr("height"))
                .returns("jop-noMdConv", img -> img.attr("class"));

        assertThat(images.get(1))
                .returns("""
                        <img class="jop-noMdConv2" width="284" height="179" alt="f7316cd7bda69f9f9649ce5c750eee2e.png" src=":/fe3aac71c1e44393a4e54666e697dfc8">""", Node::outerHtml)
                .returns(":/fe3aac71c1e44393a4e54666e697dfc8", img -> img.attr("src"))
                .returns("f7316cd7bda69f9f9649ce5c750eee2e.png", img -> img.attr("alt"))
                .returns("284", img -> img.attr("width"))
                .returns("179", img -> img.attr("height"))
                .returns("jop-noMdConv2", img -> img.attr("class"));
    }
}
