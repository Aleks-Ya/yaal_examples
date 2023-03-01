package trello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import trello.common.AreaLabel;
import trello.common.Card;
import trello.common.Label;
import trello.common.TrelloService;

import java.util.ArrayList;

import static java.util.stream.Collectors.joining;

@Component
class TuneLabels {
    private static final Logger log = LoggerFactory.getLogger(TuneLabels.class);
    @Autowired
    private TrelloService trelloService;

    void createAbsentLabels() {
        var boards = trelloService.getInvolvedBoards();
        for (var board : boards) {
            var absentLabels = trelloService.getAbsentLabels(board);
            log.info("Board: '{}', absent labels: {}", board.name(), absentLabels);
            for (var absentLabel : absentLabels) {
                trelloService.createLabel(board, absentLabel);
            }
        }
    }

    void findCardsHavingManyLabels() {
        var manyLabelsCard = new ArrayList<Card>();
        var noLabelsCard = new ArrayList<Card>();
        var boards = trelloService.getInvolvedBoards();
        for (var board : boards) {
            var boardLabels = trelloService.getInvolvedLabelsOnBoard(board).stream()
                    .map(Label::name).toList();
            var cards = trelloService.getAllCardsOnBoard(board);
            for (var card : cards) {
                var labels = card.labels();
                var labelCounter = 0;
                for (var label : labels) {
                    if (boardLabels.contains(label.name())) {
                        labelCounter++;
                    }
                }
                if (labelCounter == 0 && ObjectUtils.isEmpty(card.cover().idPlugin())) {
                    noLabelsCard.add(card);
                }
                if (labelCounter > 1) {
                    manyLabelsCard.add(card);
                }

            }
        }
        if (!noLabelsCard.isEmpty()) {
            log.error("Found cards without labels:\n{}", noLabelsCard.stream().map(Card::toString).collect(joining("\n")));
        }
        if (!manyLabelsCard.isEmpty()) {
            log.error("Found cards with many labels:\n{}", manyLabelsCard.stream().map(Card::toString).collect(joining("\n")));
        }
        if (!noLabelsCard.isEmpty() || !manyLabelsCard.isEmpty()) {
            throw new IllegalStateException();
        }
    }

    void tune() {
        var boards = trelloService.getInvolvedBoards();
        for (var board : boards) {
            for (var areaLabel : AreaLabel.values()) {
                var label = trelloService.getLabelOnBoard(board, areaLabel);
                var cards = trelloService.getCardsOnBoardWithLabel(board, label);
                var desiredCoverColor = areaLabel.getCoverColor();
                for (var card : cards) {
                    var isImageCover = !ObjectUtils.isEmpty(card.cover().idAttachment());
                    var wrongColor = !desiredCoverColor.getColor().equals(card.cover().color());
                    var wrongSize = !"normal".equals(card.cover().size());
                    if (!isImageCover && (wrongColor || wrongSize)) {
                        trelloService.setCardCoverColor(card, areaLabel.getCoverColor());
                    }
                }
            }
        }
    }
}
