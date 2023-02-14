package trello;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
class TrelloService {
    private static final Logger log = LoggerFactory.getLogger(TrelloService.class);
    @Autowired
    private TrelloClient trelloClient;
    @Autowired
    private TrelloProperties trelloProperties;

    public List<AreaLabel> getAbsentLabels(Board board) {
        var labels = trelloClient.getAllLabelsOnBoard(board);
        var absentLabels = new ArrayList<>(Arrays.asList(AreaLabel.values()));
        for (var label : labels) {
            var areaLabelOpt = AreaLabel.byName(label.name());
            areaLabelOpt.ifPresent(absentLabels::remove);
        }
        return absentLabels;
    }

    public List<Board> getInvolvedBoards() {
        return trelloProperties.boardIds().stream()
                .map(boardId -> trelloClient.getBoardById(boardId)
                        .orElseThrow(() -> new IllegalStateException("Board not found by id: " + boardId)))
                .toList();
    }

    public void createLabel(Board board, AreaLabel areaLabel) {
        var label = trelloClient.createLabel(board, areaLabel.getTitle(), areaLabel.getCoverColor());
        log.info("Label created: {}", label);
    }

    public List<Card> getAllCardsOnBoard(Board board) {
        return trelloClient.getAllCardsOnBoard(board);
    }

    public List<Card> getCardsOnBoardWithLabel(Board board, Label label) {
        return getAllCardsOnBoard(board).stream()
                .filter(card -> card.labels().contains(label))
                .toList();
    }

    public List<Label> getInvolvedLabelsOnBoard(Board board) {
        return Arrays.stream(AreaLabel.values()).map(areaLabel -> getLabelOnBoard(board, areaLabel)).toList();
    }

    public Label getLabelOnBoard(Board board, AreaLabel areaLabel) {
        return trelloClient.getAllLabelsOnBoard(board).stream()
                .filter(label -> {
                    var areaLabelOpt = AreaLabel.byName(label.name());
                    return areaLabelOpt.isPresent() && areaLabelOpt.get() == areaLabel;
                })
                .findFirst().orElseThrow();
    }

    public void setCardCoverColor(Card card, Color coverColor) {
        trelloClient.setCardCoverColor(card, coverColor);
    }
}
