package trello;

import com.julienvey.trello.Trello;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.JDKTrelloHttpClient;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class Factory {
    public static Trello createApi() {
        try {
            var userHome = System.getProperty("user.home");
            var propertiesPath = Paths.get(userHome, ".trello", "trello-api.properties");
            var propertiesFileReader = new FileReader(propertiesPath.toString());
            var properties = new Properties();
            properties.load(propertiesFileReader);
            var key = properties.getProperty("key");
            var token = properties.getProperty("token");
            return new TrelloImpl(key, token, new JDKTrelloHttpClient());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
