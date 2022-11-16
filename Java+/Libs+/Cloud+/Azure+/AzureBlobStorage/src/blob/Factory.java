package blob;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import util.FileUtil;
import util.RandomUtil;

import java.nio.file.Paths;
import java.util.Locale;

public class Factory {
    public static final String DELIMITER = "/";
    private static BlobServiceClient realClient;
    private static BlobServiceClient azuriteClient;

    public synchronized static BlobServiceClient realBlobServiceClient() {
        if (realClient == null) {
            realClient = initRealClient();
        }
        return realClient;
    }

    public synchronized static BlobServiceClient azuriteBlobServiceClient() {
        if (azuriteClient == null) {
            azuriteClient = initAzuriteClient();
        }
        return azuriteClient;
    }

    public static String randomName() {
        return "azure-blob-storage-examples-" + RandomUtil.randomIntPositive();
    }

    private static BlobServiceClient initRealClient() {
        var path = Paths.get(System.getProperty("user.home"), ".azure-examples", "credentials.properties");
        var properties = FileUtil.pathToProperties(path);
        var accountName = properties.getProperty("blob.storage.account");
        var accountKey = properties.getProperty("blob.storage.access.key");
        var credential = new StorageSharedKeyCredential(accountName, accountKey);
        var endpoint = String.format(Locale.ROOT, "https://%s.blob.core.windows.net", accountName);
        return new BlobServiceClientBuilder().endpoint(endpoint).credential(credential).buildClient();
    }

    private static BlobServiceClient initAzuriteClient() {
        var accountName = "devstoreaccount1";
        var credential = new StorageSharedKeyCredential(accountName,
                "Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==");
        return new BlobServiceClientBuilder()
                .endpoint("http://127.0.0.1:10000/" + accountName)
                .credential(credential)
                .buildClient();
    }

    public static String createBlob(BlobContainerClient blobContainerClient, String... path) {
        var blobName = String.join(DELIMITER, path);
        var blobClient = blobContainerClient.getBlobClient(blobName);
        blobClient.upload(BinaryData.fromString("content of " + blobName));
        return blobName;
    }
}
