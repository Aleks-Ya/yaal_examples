package cloud;


import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class DownloadObject {
    @Test
    public void name() throws IOException {

        // The ID of your GCP project
        String projectId = "enterprise-data-hub-dev";

        // The ID of your GCS bucket
        String bucketName = "iablokov-test-bucket";

        // The ID of your GCS object
        String objectName = "file1.txt";

        // The path to which the file should be downloaded
        String destFilePath = Files.createTempFile(DownloadObject.class.getSimpleName(), ".txt").toString();
        System.out.println("Destination file: " + destFilePath);

        Storage storage = StorageOptions.newBuilder()
//                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(configs.googleCredentials)))
                .setProjectId(projectId)
                .build().getService();

        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        blob.downloadTo(Paths.get(destFilePath));

        System.out.println(
                "Downloaded object "
                        + objectName
                        + " from bucket name "
                        + bucketName
                        + " to "
                        + destFilePath);

    }

}
