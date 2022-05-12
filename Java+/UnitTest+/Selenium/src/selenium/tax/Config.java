package selenium.tax;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class Config {
    public final String loginUrl;
    public final String loginUsername;
    public final String loginPassword;
    public final String declarationUrl;

    public Config(String loginUrl, String loginUsername, String loginPassword, String declarationUrl) {
        this.loginUrl = loginUrl;
        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
        this.declarationUrl = declarationUrl;
    }

    public static Config load(File propertiesFile) {
        try {
            var props = new Properties();
            props.load(new FileInputStream(propertiesFile));
            return new Config(
                    props.getProperty("login.url"),
                    props.getProperty("login.username"),
                    props.getProperty("login.password"),
                    props.getProperty("declaration.url"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
