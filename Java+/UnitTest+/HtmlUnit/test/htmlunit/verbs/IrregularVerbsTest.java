package htmlunit.verbs;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class IrregularVerbsTest {

    @Test
    void parse() throws IOException {
        try (var webClient = new WebClient()) {
            webClient.getOptions().setJavaScriptEnabled(false);
            var page = (HtmlPage) webClient.getPage("https://www.usingenglish.com/reference/irregular-verbs/");
            var table = (HtmlTable) page.getElementById("verbList");
            var links = table.getRows().stream()
                    .filter(row -> !"Base Form".equalsIgnoreCase(row.getCell(0).getVisibleText()))
                    .map(row -> row.getCell(0))
                    .map(cell -> cell.getElementsByTagName("a").get(0))
                    .map(element -> (HtmlAnchor) element)
                    .toList();
            for (var link : links) {
                var verbPage = (HtmlPage) link.click();
                var mainContentBodyDiv = (HtmlDivision) verbPage.getElementById("main_content_body");
                var verbTable = (HtmlTable) mainContentBodyDiv.getFirstByXPath("div[2]/div/div/table");
                var rows = verbTable.getRows();
                var v1 = rows.get(0).getCell(1).getVisibleText().toLowerCase().replaceFirst("to ", "");
                var v2 = rows.get(1).getCell(1).getVisibleText().toLowerCase();
                var v3 = rows.get(2).getCell(1).getVisibleText().toLowerCase();
                var v4 = rows.get(3).getCell(1).getVisibleText().toLowerCase();
                var v5 = rows.get(4).getCell(1).getVisibleText().toLowerCase();
                System.out.printf("%s|%s|%s|%s|%s\n", v1, v2, v3, v5, v4);
            }
        }
    }
}
