package download;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import fi.iki.elonen.NanoHTTPD;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

public class SinglePageTest {
    @Test
    public void main() throws Exception {
        final String content = "abcde";
        final Integer port = 40888;
        NanoHTTPD server = new NanoHTTPD(port) {
            @Override
            public Response serve(IHTTPSession session) {
                return newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, content);
            }
        };
        server.start(0, true);

        File storage = Files.createTempDirectory(getClass().getSimpleName() + "").toFile();
        System.out.println("Storage dir: " + storage);

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(storage.getAbsolutePath());
        config.setShutdownOnEmptyQueue(true);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed("http://localhost:" + port);
        int numberOfCrawlers = 1;
        controller.start(SinglePageCrawler.class, numberOfCrawlers);
        server.stop();
    }
}
