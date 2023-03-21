package sardine.yandexdisk.forkjoin;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

class DownloadFolderAction extends RecursiveAction {
    private static final Logger log = LoggerFactory.getLogger(DownloadFolderAction.class);
    private static final String WEBDAV_ENDPOINT = "https://webdav.yandex.com";
    private final Sardine sardine;
    private final DavResource resource;
    private final Path downloadPath;
    private final Statistics statistics;

    DownloadFolderAction(DavResource resource, Path downloadPath, Statistics statistics) {
        this.sardine = Sardines.get();
        this.resource = resource;
        this.downloadPath = downloadPath;
        this.statistics = statistics;
    }

    DownloadFolderAction(String folder, Path downloadPath, Statistics statistics) throws URISyntaxException, IOException {
        this.sardine = Sardines.get();
        this.resource = sardine.list(makeUrl(folder), 0).get(0);
        this.downloadPath = downloadPath;
        this.statistics = statistics;
    }

    @Override
    public void compute() {
        try {
            statistics.printStatistics();
            log.info("Downloading folder: {}", resource.getPath());
            Files.createDirectories(downloadPath);
            var url = makeUrl(resource.getPath());
            var resources = sardine.list(url, 1);
            var actions = new ArrayList<RecursiveAction>();
            for (var resource : resources) {
                if (resource.getHref().getPath().equals(URI.create(url).getPath())) {
                    continue;
                }
                if (resource.isDirectory()) {
                    var subOutputPath = downloadPath.resolve(resource.getName());
                    var action = new DownloadFolderAction(resource, subOutputPath, statistics);
                    actions.add(action);
                } else {
                    var action = new DownloadFileAction(resource, downloadPath, statistics);
                    actions.add(action);
                    statistics.incrementSubmitted();
                }
            }
            invokeAll(actions);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String makeUrl(String directoryPath) throws URISyntaxException {
        return new URIBuilder(WEBDAV_ENDPOINT).setPath(directoryPath).build().toString();
    }
}
