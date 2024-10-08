package trello.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TrelloService {
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

    public Map<String, List<Label>> getLabelsWithDuplicatingNameOnBoard(Board board) {
        return trelloClient.getAllLabelsOnBoard(board).stream()
                .collect(Collectors.groupingBy(Label::name))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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

    public Board getBoard(KaizenBoard kaizenBoard) {
        return trelloClient.getBoardByName(kaizenBoard.getName()).orElseThrow();
    }

    public TList getListByName(Board board, KaizenList kaizenList) {
        return trelloClient.getListByName(board, kaizenList.name()).orElseThrow();
    }

    public List<Card> getCardsInList(TList list) {
        return trelloClient.getCardsInList(list);
    }

    public Label getLabelById(String labelId) {
        return trelloClient.getLabelById(labelId);
    }

    public void replaceLabelOnCard(Card card, Label oldLabel, Label newLabel) {
        if (!card.labels().contains(newLabel)) {
            trelloClient.addLabelToCard(card, newLabel);
        }
        if (card.labels().contains(oldLabel)) {
            trelloClient.removeLabelFromCard(card, oldLabel);
        }
    }

}
