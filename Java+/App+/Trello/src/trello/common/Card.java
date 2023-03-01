package trello.common;

import java.util.List;

public record Card(String id, String name, Cover cover, List<Label> labels) {
}
