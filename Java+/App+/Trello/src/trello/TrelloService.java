package trello;


import java.net.http.HttpClient;
import java.util.List;

class TrelloService {
    private final HttpClient client = HttpClient.newHttpClient();
    private String baseUrl;
    private String key;
    private String token;


    public List<String> getCardsWithLabelFromAllBoards(String label) {
        return null;
    }

    public void setCoverToCards(List<String> cards, String cover) {

    }
}
