package sardine.yandexdisk.runnable;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import io.github.resilience4j.retry.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;

class DownloadFileRunnable implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(DownloadFileRunnable.class);
    private final Sardine sardine;
    private final DavResource resource;
    private final Path outputPath;
    private final Statistics statistics;

    DownloadFileRunnable(DavResource resource, Path outputPath, Statistics statistics) {
        this.sardine = Sardines.get();
        this.resource = resource;
        this.outputPath = outputPath;
        this.statistics = statistics;
    }

    @Override
    public void run() {
        try {
            var filePath = outputPath.resolve(resource.getName());
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void downloadFile(DavResource resource, Path outputPath) throws Exception {
        var url = Utils.makeUrl(resource.toString());
        log.info("Downloading file '{}', {}Mb", resource, resource.getContentLength() / 1024 / 1024);
        var retry = Retry.of(resource.getName(), Utils.retryConfig);
        retry.getEventPublisher().onError(event -> log.warn("Retry file: {}", event));
        retry.executeCallable(() -> {
            try (var inputStream = sardine.get(url);
                 var outputStream = Files.newOutputStream(outputPath)) {
                inputStream.transferTo(outputStream);
                return null;
            }
        });
        statistics.incrementDownloadedFile(Files.size(outputPath));
    }
}
