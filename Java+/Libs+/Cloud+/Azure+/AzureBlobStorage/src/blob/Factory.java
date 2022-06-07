package blob;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import util.RandomUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;

public class Factory {
    private static BlobServiceClient client;

    public synchronized static BlobServiceClient blobServiceClient() {
        if (client == null) {
            client = initClient();
        }
        return client;
    }

    public static String randomName() {
        return "azure-blob-storage-examples-" + RandomUtil.randomIntPositive();
    }

    private static BlobServiceClient initClient() {
        try {
            var properties = new Properties();
            var file = Paths.get(System.getProperty("user.home"), ".azure-examples", "credentials.properties").toFile();
            properties.load(new FileInputStream(file));
            var accountName = properties.getProperty("blob.storage.account");
            var accountKey = properties.getProperty("blob.storage.access.key");
            var credential = new StorageSharedKeyCredential(accountName, accountKey);
            var endpoint = String.format(Locale.ROOT, "https://%s.blob.core.windows.net", accountName);
            return new BlobServiceClientBuilder().endpoint(endpoint).credential(credential).buildClient();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
