package download;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

public class SinglePageController {
    @Test
    public void main() throws Exception {
        File storage = Files.createTempDirectory(getClass().getSimpleName() + "").toFile();
        System.out.println("Storage dir: " + storage);

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(storage.getAbsolutePath());

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed("http://www.contrib.andrew.cmu.edu/~shadow/sql/sql1992.txt");
        int numberOfCrawlers = 1;
        controller.start(SinglePageCrawler.class, numberOfCrawlers);
    }
}
