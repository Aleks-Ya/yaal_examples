package joplin.rest;

import joplin.rest.common.Item;
import joplin.rest.common.JoplinService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class AddPrefixToTagNameMain {
    public static void main(String[] args) throws IOException {
        var newPrefix = "it/";
        var tagTitlesToAppend = "error,exception,gantt";

        var joplinService = new JoplinService();

        var originalTags = joplinService.getAllItems();
        System.out.println("Original tag number: " + originalTags.size());
        System.out.println("Original tags: " + originalTags);

        var tagTilesToRename = Arrays.asList(tagTitlesToAppend.split(","));

        var filteredOriginalTags = originalTags.stream()
                .filter(tag -> tagTilesToRename.contains(tag.title()))
                .toList();
        System.out.println("Filtered tag number: " + filteredOriginalTags.size());
        System.out.println("Filtered tags: " + filteredOriginalTags);

        var renamedTags = filteredOriginalTags.stream()
                .map(tag -> new Item(tag.id(), newPrefix + tag.title()))
                .toList();
        System.out.println("Renamed tags: " + renamedTags);

        var renamedTagMap = renamedTags.stream().collect(toMap(Item::id, identity()));
        filteredOriginalTags.stream().sorted(Comparator.comparing(Item::title))
                .forEach(tag -> System.out.printf("%s: %s -> %s\n", tag.id(), tag.title(), renamedTagMap.get(tag.id()).title()));

        for (var renamedTag : renamedTags) {
            joplinService.renameTag(renamedTag);
        }
    }
}
