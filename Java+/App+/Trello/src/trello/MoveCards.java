package trello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import trello.common.KaizenBoard;
import trello.common.KaizenList;
import trello.common.TrelloService;

@Component
class MoveCards {
    @Autowired
    private TrelloService trelloService;

    public void move() {
        var kizenMonthBoard = trelloService.getBoard(KaizenBoard.KAIZEN_MONTH);
        var needToDoList = trelloService.getListByName(kizenMonthBoard, KaizenList.NEED_TO_DO);
        var cards = trelloService.getCardsInList(needToDoList);

    }
}
