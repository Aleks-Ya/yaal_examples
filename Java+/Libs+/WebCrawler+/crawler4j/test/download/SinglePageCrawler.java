package download;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.TextParseData;

public class SinglePageCrawler extends WebCrawler {
    @Override
    public void visit(Page page) {
        TextParseData data = (TextParseData) page.getParseData();
        String content = data.getTextContent();
        System.out.println("Content: " + content);
    }
}
