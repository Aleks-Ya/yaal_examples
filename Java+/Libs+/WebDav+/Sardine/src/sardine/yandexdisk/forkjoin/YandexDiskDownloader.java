package sardine.yandexdisk.forkjoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ForkJoinPool;

/**
 * Configuration file <code>~/.yandex-disk-credentials/webdav.properties</code> example:
 * <pre>
 * username=aleks-iablokov
 * password=abc
 * folder=/no_sync/backup/
 * output_dir=/media/aleks/ADATA/yandex-disk-webdav
 * thread_number=3
 * </pre>
 */
public class YandexDiskDownloader {
    private static final Logger log = LoggerFactory.getLogger(YandexDiskDownloader.class);

    public static void main(String[] args) throws Exception {
        var downloader = new YandexDiskDownloader();
        downloader.download();
    }

    public void download() throws Exception {
        log.info("Download started.");
        var settings = Settings.getSettings();
        settings.print();
        var poolSize = settings.threadNumber();
        var pool = new ForkJoinPool(poolSize);
        var folder = settings.folder();
        var outputDir = settings.outputDir();
        var statistics = new Statistics();
        var action = new DownloadFolderAction(folder, outputDir, statistics);
        pool.invoke(action);
        pool.shutdown();
        log.info("Download complete.");
        statistics.printStatistics();
    }

}
