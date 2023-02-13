package trello;


import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

class TrelloClient {
    private final HttpClient client = HttpClient.newHttpClient();
    private String baseUrl;
    private String key;
    private String token;

    private void init() {
        try {
            var propertiesPath = Paths.get(System.getProperty("user.home"), ".trello", "trello-api.properties");
            var propertiesFileReader = new FileReader(propertiesPath.toString());
            var properties = new Properties();
            properties.load(propertiesFileReader);
            baseUrl = properties.getProperty("baseUrl");
            key = properties.getProperty("key");
            token = properties.getProperty("token");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String request(String method, String endpoint){
        return null;
    }

    public List<Board> getAllBoards() {
        try {
            var uri = URI.create(baseUrl + "/members/me/boards" + "?key=" + key + "&token=" + token);
            var request = HttpRequest.newBuilder().uri(uri).GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException();
            }
            var body = response.body();
            return null;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCoverToCards(List<String> cards, String cover) {

    }
}
