package sardine;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SFactory {
    public static Sardine initYandexDiskClient() {
        try {
            var propertiesFile = new File(System.getProperty("user.home"), ".yandex-disk-credentials/webdav.properties");
            var properties = new Properties();
            properties.load(new FileReader(propertiesFile));
            var username = properties.getProperty("username");
            var password = properties.getProperty("password");
            return SardineFactory.begin(username, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
