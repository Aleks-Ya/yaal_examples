package sardine.yandexdisk.forkjoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Statistics {
    private static final Logger log = LoggerFactory.getLogger(Statistics.class);
    private int submittedCounter = 0;
    private int downloadedFileCounter = 0;
    private int alreadyDownloadedFileCounter = 0;
    private int differentFileSizeCounter = 0;
    private long downloadedSizeByte = 0L;

    public synchronized void incrementSubmitted() {
        submittedCounter++;
    }

    public synchronized void incrementDownloadedFile(Long size) {
        downloadedFileCounter++;
        downloadedSizeByte += size;
    }

    public synchronized void incrementAlreadyDownloadedFile(Long size) {
        alreadyDownloadedFileCounter++;
        downloadedSizeByte += size;
    }

    public synchronized void incrementDifferentFileSize() {
        differentFileSizeCounter++;
    }

    public synchronized void printStatistics() {
        log.info("Different size: {}, Already downloaded: {}, Submitted: {}, Downloaded: {}, " +
                        "Total downloaded: {}, Total size: {}Mb",
                differentFileSizeCounter, alreadyDownloadedFileCounter, submittedCounter, downloadedFileCounter,
                alreadyDownloadedFileCounter + downloadedFileCounter, downloadedSizeByte / 1024 / 1024);
    }
}
