package mock;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Upload, download and list objects in a bucket.
 */
public class TransferManagerTest extends AdobeBaseTest {

    @Test
    public void uploadFileList() throws IOException, InterruptedException {
        Bucket bucket = createRandomBucket(s3);

        var dir = Files.createTempDirectory(getClass().getSimpleName());
        var keyName1 = "file1.tmp";
        var file1 = dir.resolve(keyName1);
        var keyName2 = "file2.tmp";
        var file2 = dir.resolve(keyName2);

        var content1 = "content1";
        var content2 = "content2";

        Files.write(file1, content1.getBytes());
        Files.write(file2, content2.getBytes());

        var transferManager = TransferManagerBuilder.standard().withS3Client(s3).build();
        var upload = transferManager.uploadFileList(bucket.getName(), null, dir.toFile(),
                List.of(file1.toFile(), file2.toFile()));
        upload.waitForCompletion();
        transferManager.shutdownNow(false);

        String actContent1 = downloadObject(bucket, keyName1);
        assertThat(actContent1, equalTo(content1));

        String actContent2 = downloadObject(bucket, keyName2);
        assertThat(actContent2, equalTo(content2));
    }

    /**
     * Transfer#waitForCompletion() works very slow.
     */
    @Test
    public void uploadFileListPerformance() throws IOException, InterruptedException {
        Bucket bucket = createRandomBucket(s3);

        var dir = Files.createTempDirectory(getClass().getSimpleName());
        var random = new Random();
        var fileNum = 10;
        var fileList = new ArrayList<File>();
        for (int i = 0; i < fileNum; i++) {
            var n = random.nextInt(Integer.MAX_VALUE);
            var keyName = "key-" + n;
            var content = "content-" + n;
            var file = dir.resolve(keyName);
            Files.write(file, content.getBytes());
            fileList.add(file.toFile());
        }
        var transferManager = TransferManagerBuilder.standard().withS3Client(s3).build();
        var upload = transferManager.uploadFileList(bucket.getName(), null,
                dir.toFile(), fileList);
        System.out.println("Waiting for completion...");
        upload.waitForCompletion();
        System.out.println("Uploading is completed.");
        transferManager.shutdownNow(false);

        var objects = listObjects(bucket);
        assertThat(objects, hasSize(fileList.size()));
    }
}