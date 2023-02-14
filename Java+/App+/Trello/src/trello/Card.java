package trello;

import java.util.List;

record Card(String id, String name, Cover cover, List<Label> labels) {
}
