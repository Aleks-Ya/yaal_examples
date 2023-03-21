package sardine.yandexdisk.runnable;

import com.github.sardine.Sardine;
import io.github.resilience4j.retry.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static sardine.yandexdisk.runnable.Utils.makeUrl;

/**
 * Configuration file <code>~/.yandex-disk-credentials/webdav.properties</code> example:
 * <pre>
 * username=aleks-iablokov
 * password=abc
 * folder=/no_sync/backup/
 * output_dir=/media/aleks/ADATA/yandex-disk-webdav
 * thread_number=3
 * queue_size=100
 * </pre>
 */
public class YandexDiskDownloader {
    private static final Logger log = LoggerFactory.getLogger(YandexDiskDownloader.class);
    private Sardine sardine;
    private ExecutorService executorService;
    private final Statistics statistics = new Statistics();

    public static void main(String[] args) throws Exception {
        var downloader = new YandexDiskDownloader();
        downloader.download();
    }

    public void download() throws Exception {
        log.info("Download started.");
        var settings = Settings.getSettings();
        settings.print();
        var poolSize = settings.threadNumber();
        var queueSize = settings.queueSize();
        executorService = new ThreadPoolExecutor(poolSize, poolSize, 0L, MILLISECONDS,
                new ArrayBlockingQueue<>(queueSize), new ThreadPoolExecutor.CallerRunsPolicy());
        var folder = settings.folder();
        var outputPath = settings.outputDir();

        sardine = Sardines.get();

        Files.createDirectories(outputPath);

        downloadDirectory(folder, outputPath);

        executorService.shutdown();

        log.info("Download complete.");

        statistics.printStatistics();
    }

    private void downloadDirectory(String folder, Path outputPath) throws Exception {
        statistics.printStatistics();
        log.info("Downloading directory: {}", folder);
        var folderUrl = makeUrl(folder);
        var retry = Retry.of(folder, Utils.retryConfig);
        retry.getEventPublisher().onError(event -> log.warn("Retry folder: {}", event));
        var resources = retry.executeCallable(() -> sardine.list(folderUrl, 1));
        for (var resource : resources) {
            if (resource.getHref().getPath().equals(URI.create(folderUrl).getPath())) {
                continue;
            }
            if (resource.isDirectory()) {
                var subDirectoryPath = outputPath.resolve(resource.getName());
                Files.createDirectories(subDirectoryPath);
                var subFolder = resource.getHref().getPath();
                downloadDirectory(subFolder, subDirectoryPath);
            } else {
                var callable = new DownloadFileRunnable(resource, outputPath, statistics);
                executorService.submit(callable);
                statistics.incrementSubmitted();
            }
        }
    }
}
