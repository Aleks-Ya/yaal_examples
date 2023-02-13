package trello;

import com.julienvey.trello.domain.Card;
import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.joining;

class CardTest {
    @Test
    void listAllCardsOnBoard1() {
        var trelloApi = Factory.createApi();
        var boardId = "63c215679f4b4d011cc4fbe8";
        var board = trelloApi.getBoard(boardId);
        var cards = board.fetchCards();
        System.out.println(cards.stream()
                .map(Card::getName)
                .map(name -> "'" + name + "'")
                .collect(joining(", ")));
    }

    @Test
    void listAllCardsOnBoard2() {
        var trelloApi = Factory.createApi();
        var boardId = "63c215679f4b4d011cc4fbe8";
        var cards = trelloApi.getBoardCards(boardId);
        System.out.println(cards.stream()
                .map(Card::getName)
                .map(name -> "'" + name + "'")
                .collect(joining(", ")));
    }

    @Test
    void getCardById() {
        var trelloApi = Factory.createApi();
        var cardId = "63c21ec2e181c102d5eb0f46";
        var card = trelloApi.getCard(cardId);
        System.out.println(card.getName());
    }

    @Test
    void listAllCardsOnBoardHavingLabel() {
        var trelloApi = Factory.createApi();
        var boardId = "63c215679f4b4d011cc4fbe8";
        var labelName = "A: Work";
        var label = trelloApi.getBoardLabels(boardId).stream()
                .filter(label1 -> labelName.equals(label1.getName()))
                .findFirst()
                .orElseThrow();
        var cards = trelloApi.getBoardCards(boardId).stream()
                .filter(card -> card.getLabels().contains(label))
                .toList();
        System.out.println("Label: " + label);
        System.out.println(cards.stream()
                .map(Card::getName)
                .map(name -> "'" + name + "'")
                .collect(joining(", ")));
    }

    /**
     * "cover" field is not supported by the library
     */
    @Test
    void getCardCover() {
        var trelloApi = Factory.createApi();
        var cardId = "63c21ec2e181c102d5eb0f46";
        var card = trelloApi.getCard(cardId);
        var idAttachmentCover = card.getIdAttachmentCover();
        var manualCoverAttachment = card.isManualCoverAttachment();
        System.out.println("manualCoverAttachment: " + manualCoverAttachment);
        System.out.println("idAttachmentCover: " + idAttachmentCover);
    }
}
