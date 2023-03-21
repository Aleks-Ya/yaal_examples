package sardine.yandexdisk.forkjoin;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.RecursiveAction;

class DownloadFileAction extends RecursiveAction {
    private static final Logger log = LoggerFactory.getLogger(DownloadFileAction.class);
    private static final String WEBDAV_ENDPOINT = "https://webdav.yandex.com";
    private final Sardine sardine;
    private final DavResource resource;
    private final Path downloadPath;
    private final Statistics statistics;

    DownloadFileAction(DavResource resource, Path downloadPath, Statistics statistics) {
        this.sardine = Sardines.get();
        this.resource = resource;
        this.downloadPath = downloadPath;
        this.statistics = statistics;
    }

    @Override
    public void compute() {
        try {
            var filePath = downloadPath.resolve(resource.getName());
            if (!Files.exists(filePath)) {
                downloadFile(resource, filePath);
            } else {
                var downloadedSize = Files.size(filePath);
                var webDavSize = resource.getContentLength();
                if (downloadedSize == webDavSize) {
                    log.info("Already downloaded: {}", resource);
                    statistics.incrementAlreadyDownloadedFile(downloadedSize);
                } else {
                    log.info("Different size of '{}': downloaded={}, web={}", filePath, downloadedSize, webDavSize);
                    statistics.incrementDifferentFileSize();
                    downloadFile(resource, filePath);
                }
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void downloadFile(DavResource resource, Path filePath) throws URISyntaxException, IOException {
        var url = makeUrl(resource.toString());
        log.info("Downloading file '{}', {}Mb", resource, resource.getContentLength() / 1024 / 1024);
        try (var inputStream = sardine.get(url);
             var outputStream = Files.newOutputStream(filePath)) {
            inputStream.transferTo(outputStream);
        }
        statistics.incrementDownloadedFile(Files.size(filePath));
    }

    private String makeUrl(String directoryPath) throws URISyntaxException {
        return new URIBuilder(WEBDAV_ENDPOINT).setPath(directoryPath).build().toString();
    }
}
