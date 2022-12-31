package joplin.apps.find_big_notes;

import joplin.common.Factory;
import joplin.common.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Prints the biggest notes including attachment sizes.
 */
public class FindBigNotesMain {
    private static final Logger log = LoggerFactory.getLogger(FindBigNotesMain.class);

    public static void main(String[] args) {
        log.info("Started");
        try (var facade = Factory.createFacadeProd(true)) {
            System.out.println("\nBIGGEST NOTES");
            facade.fetchBiggestNotes(10).stream()
                    .map(note -> String.format("noteId='%s', title='%s', size=%,d, resourceNum=%d, biggestResourceSize=%,d)",
                            note.noteId().id(), note.title(),
                            note.getNoteSize(),
                            note.getResourceNumber(),
                            note.getBiggestResource().map(Resource::getSize).orElse(0L)))
                    .forEach(System.out::println);

            System.out.println("\n\nNOTES WITH BIGGEST SINGLE RESOURCE (IMAGES ONLY)");
            facade.fetchNotesWithBiggestSingleResource(10, List.of("jpg", "jpeg")).stream()
                    .map(tuple -> {
                        var note = tuple.getLeft();
                        var biggestResource = tuple.getRight();
                        return String.format("noteId='%s', title='%s', noteSize=%,d, resourceNum=%d, " +
                                        "biggestResourceSize=%,d, biggestResourceFilename='%s')",
                                note.noteId().id(), note.title(),
                                note.getNoteSize(),
                                note.getResourceNumber(),
                                biggestResource.getSize(),
                                biggestResource.resourceFile().getName()
                        );
                    })
                    .forEach(System.out::println);

            System.out.println("\n\nNOTES WITH BIGGEST SINGLE RESOURCE (IMAGES ONLY) FOR COPYING");
            facade.fetchNotesWithBiggestSingleResource(10, List.of("jpg", "jpeg")).stream()
                    .map(tuple -> {
                        var biggestResource = tuple.getRight();
                        return String.format("cp '%s' /home/aleks/JoplinAttacheResourcesForReplace/",
                                biggestResource.resourceFile().getAbsolutePath());
                    })
                    .forEach(System.out::println);
        }
        log.info("Finished");
    }
}
