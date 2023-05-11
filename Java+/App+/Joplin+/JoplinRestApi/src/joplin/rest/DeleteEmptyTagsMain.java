package joplin.rest;

import joplin.rest.common.Item;
import joplin.rest.common.JoplinService;

import java.io.IOException;
import java.util.Comparator;

/**
 * Find tags not used on any note and delete them.
 */
public class DeleteEmptyTagsMain {
    public static void main(String[] args) throws IOException {
        var joplinService = new JoplinService();

        var tags = joplinService.getAllItems();
        System.out.println("All tag number: " + tags.size());
        var emptyTags = tags.stream()
                .filter(tag -> joplinService.isEmptyTag(tag.id()))
                .sorted(Comparator.comparing(Item::title))
                .toList();
        System.out.println("Empty tag number: " + emptyTags.size());
        System.out.println("Empty tags: ");
        emptyTags.forEach(tag -> System.out.println(tag.title()));

        System.out.println("\nDeleting empty tags: ");
        emptyTags.forEach(joplinService::deleteTag);
    }
}
