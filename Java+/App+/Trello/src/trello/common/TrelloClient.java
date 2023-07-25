package trello.common;


import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.client.HttpClientErrorException.BadRequest;

@Service
class TrelloClient {
    private static final Logger log = LoggerFactory.getLogger(TrelloService.class);
    private final MultiValueMap<String, String> secretParams = new LinkedMultiValueMap<>();
    private String baseUrl;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TrelloProperties trelloProperties;

    @PostConstruct
    private void init() {
        baseUrl = trelloProperties.baseUrl();
        secretParams.put("key", List.of(trelloProperties.key()));
        secretParams.put("token", List.of(trelloProperties.token()));
    }

    public Optional<Board> getBoardByName(String boardName) {
        return getAllBoards().stream().filter(board -> boardName.equals(board.name())).findFirst();
    }

    public List<TList> getListsOnBoard(Board board) {
        var uri = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment("boards", board.id(), "lists")
                .queryParams(secretParams)
                .build()
                .toUri();
        var cards = restTemplate.getForObject(uri, TList[].class);
        if (cards == null) {
            return List.of();
        }
        return Arrays.asList(cards);
    }

    public List<Card> getCardsInList(TList list) {
        var uri = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment("lists", list.id(), "cards")
                .queryParams(secretParams)
                .build()
                .toUri();
        var cards = restTemplate.getForObject(uri, Card[].class);
        if (cards == null) {
            return List.of();
        }
        return Arrays.asList(cards);
    }

    public Optional<TList> getListByName(Board board, String listName) {
        return getListsOnBoard(board).stream().filter(list -> listName.equals(list.name())).findFirst();
    }

    public List<Card> getAllCardsOnBoard(Board board) {
        var uri = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment("boards", board.id(), "cards")
                .queryParams(secretParams)
                .build()
                .toUri();
        var cards = restTemplate.getForObject(uri, Card[].class);
        if (cards == null) {
            return List.of();
        }
        return Arrays.asList(cards);
    }

    public List<Label> getAllLabelsOnBoard(Board board) {
        var uri = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment("boards", board.id(), "labels")
                .queryParams(secretParams)
                .build()
                .toUri();
        var labels = restTemplate.getForObject(uri, Label[].class);
        if (labels == null) {
            return List.of();
        }
        return Arrays.asList(labels);
    }

    public List<Board> getAllBoards() {
        var uri = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment("members/me/boards")
                .queryParams(secretParams)
                .build()
                .toUri();
        var boards = restTemplate.getForObject(uri, Board[].class);
        if (boards == null) {
            return List.of();
        }
        return Arrays.asList(boards);
    }

    public Optional<Board> getBoardById(String boardId) {
        var uri = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment("boards", boardId)
                .queryParams(secretParams)
                .build()
                .toUri();
        return Optional.ofNullable(restTemplate.getForObject(uri, Board.class));
    }

    public Label createLabel(Board board, String labelName, Color color) {
        var uri = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment("labels")
                .queryParams(secretParams)
                .queryParam("name", labelName)
                .queryParam("color", color.getColor())
                .queryParam("idBoard", board.id())
                .build()
                .toUri();
        return restTemplate.postForObject(uri, null, Label.class);
    }

    public void setCardCoverColor(Card card, Color coverColor) {
        var uri = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment("cards", card.id())
                .queryParams(secretParams)
                .build()
                .toUri();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var body = String.format("""
                {"cover":{"color":"%s","size":"normal"}}
                """, coverColor.getColor());
        var entity = new HttpEntity<>(body, headers);
        restTemplate.put(uri, entity);
        log.info("Updated card color: cardName='{}', coverColor={}", card.name(), coverColor);
    }

    /**
     * @throws BadRequest if card has the label already.
     */
    public void addLabelToCard(Card card, Label label) {
        var uri = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment("cards")
                .pathSegment(card.id())
                .pathSegment("idLabels")
                .queryParams(secretParams)
                .queryParam("value", label.id())
                .build()
                .toUri();
        restTemplate.postForObject(uri, null, Object.class);
        log.info("Added label to card: cardName='{}', labelId={}", card.name(), label.name());
    }

    public void removeLabelFromCard(Card card, Label label) {
        var uri = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment("cards")
                .pathSegment(card.id())
                .pathSegment("idLabels")
                .pathSegment(label.id())
                .queryParams(secretParams)
                .build()
                .toUri();
        restTemplate.delete(uri);
        log.info("Removed label from card: cardName='{}', labelId={}", card.name(), label.id());
    }

    public Label getLabelById(String labelId) {
        var uri = UriComponentsBuilder.fromUriString(baseUrl)
                .pathSegment("labels")
                .pathSegment(labelId)
                .queryParams(secretParams)
                .build()
                .toUri();
        return restTemplate.getForObject(uri, Label.class);
    }

}
