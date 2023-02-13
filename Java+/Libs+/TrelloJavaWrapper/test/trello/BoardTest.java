package trello;

import com.julienvey.trello.domain.Board;
import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.joining;

class BoardTest {
    @Test
    void listAllBoards() {
        var trelloApi = Factory.createApi();
        var boards = trelloApi.getMemberBoards("me");
        System.out.println(boards.stream()
                .map(Board::getName)
                .map(name -> "'" + name + "'")
                .collect(joining(", ")));
    }

    @Test
    void getBoardById() {
        var trelloApi = Factory.createApi();
        var boardId = "63c21ac0acaf280133e6ae1b";
        var board = trelloApi.getBoard(boardId);
        System.out.println(board.getName());
    }
}
