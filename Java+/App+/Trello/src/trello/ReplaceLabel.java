package trello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import trello.common.TrelloService;

@Component
class ReplaceLabel {
    private static final Logger log = LoggerFactory.getLogger(ReplaceLabel.class);
    @Autowired
    private TrelloService trelloService;

    public void replace(String oldLabelId, String newLabelId) {
        var boards = trelloService.getInvolvedBoards();
        boards.forEach(board -> {
            var oldLabel = trelloService.getLabelById(oldLabelId);
            var newLabel = trelloService.getLabelById(newLabelId);
            var cards = trelloService.getCardsOnBoardWithLabel(board, oldLabel);
            cards.forEach(card -> {
                trelloService.replaceLabelOnCard(card, oldLabel, newLabel);
                log.info("Label was replaced: cardName={}, oldLabelId={}, newLabelId={}",
                        card.name(), oldLabel.id(), newLabel.id());
            });
        });
    }
}
