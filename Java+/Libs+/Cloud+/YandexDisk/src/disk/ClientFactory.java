package disk;

import com.yandex.disk.rest.Credentials;
import com.yandex.disk.rest.RestClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class ClientFactory {
    private ClientFactory() {
    }

    private static class LazyHolder {
        static final RestClient INSTANCE = createRestClient();
    }

    public static RestClient getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static RestClient createRestClient() {
        try {
            var homeDir = System.getProperty("user.home");
            var properties = new Properties();
            properties.load(new FileInputStream(new File(homeDir, ".yandex-disk-credentials/disk.properties")));
            var user = properties.getProperty("user");
            var token = properties.getProperty("token");
            var credentials = new Credentials(user, token);
            return new RestClient(credentials);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
