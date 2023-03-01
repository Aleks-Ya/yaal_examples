package trello.common;

import java.util.List;

record TrelloProperties(String baseUrl, String key, String token, List<String> boardIds) {
}
