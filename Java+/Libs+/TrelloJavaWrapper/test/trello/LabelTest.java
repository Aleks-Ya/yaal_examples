package trello;

import com.julienvey.trello.domain.Label;
import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.joining;

class LabelTest {
    @Test
    void getAllLabelsInBoard1() {
        var trelloApi = Factory.createApi();
        var boardId = "63c215679f4b4d011cc4fbe8";
        var board = trelloApi.getBoard(boardId);
        var labelNames = board.getLabelNames();
        System.out.println(labelNames);
    }

    @Test
    void getAllLabelsInBoard2() {
        var trelloApi = Factory.createApi();
        var boardId = "63c215679f4b4d011cc4fbe8";
        var labels = trelloApi.getBoardLabels(boardId);
        System.out.println(labels.stream()
                .map(Label::getName)
                .map(labelName -> "'" + labelName + "'")
                .collect(joining(", ")));
    }
}
