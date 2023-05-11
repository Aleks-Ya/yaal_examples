package joplin.rest;

import joplin.rest.common.Item;
import joplin.rest.common.JoplinService;

import java.io.IOException;
import java.util.Comparator;

public class ListTagsMain {
    public static void main(String[] args) throws IOException {
        var joplinService = new JoplinService();
        var tags = joplinService.getAllItems();
        System.out.println("Tag number: " + tags.size());
        var sortedTags = tags.stream()
//                .filter(tag -> tag.title().startsWith("it/"))
//                .filter(tag -> tag.title().matches(".*[А-яЁё].*"))
//                .filter(tag -> tag.title().contains("management"))
//                .filter(tag -> {
//                    String[] notStartWithPrefixes = {"it", "health", "finance", "fitness", "management", "person",
//                            "place", "body", "year", "english", "travel"};
//                    return Arrays.stream(notStartWithPrefixes).noneMatch(prefix -> tag.title().startsWith(prefix));
//                })
                .sorted(Comparator.comparing(Item::title))
                .toList();
        sortedTags.forEach(tag -> System.out.println(tag.title()));
    }
}
