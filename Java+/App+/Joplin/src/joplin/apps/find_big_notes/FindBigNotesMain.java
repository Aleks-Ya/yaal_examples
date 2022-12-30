package joplin.apps.find_big_notes;

import joplin.common.Factory;
import joplin.common.link.LinkType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Prints the biggest notes including attachment sizes.
 */
public class FindBigNotesMain {
    private static final Logger log = LoggerFactory.getLogger(FindBigNotesMain.class);

    public static void main(String[] args) {
        log.info("Started");
        try (var facade = Factory.createFacadeProd(true)) {
            var resourceService = facade.getResourceService();
            System.out.println("\nBIGGEST NOTES");
            facade.findBiggestNotes(100).stream()
                    .map(note -> String.format("noteId='%s', title='%s', size=%,d, resourceNum=%d, biggestResourceSize=%,d)",
                            note.id().id(), note.title(),
                            resourceService.noteSizeWithResources(note),
                            resourceService.noteResourceNumber(note, LinkType.JOPLIN),
                            resourceService.biggestResource(note)
                                    .map(resource -> resource.resourceFile().length()).orElse(0L)))
                    .forEach(System.out::println);
            System.out.println("\n\nNOTES WITH BIGGEST SINGLE RESOURCE (IMAGES ONLY)");
            facade.findNotesWithBiggestSingleResource(100).stream()
                    .map(note -> {
                        var biggestResource = resourceService.biggestResource(note);
                        return String.format("noteId='%s', title='%s', noteSize=%,d, resourceNum=%d, " +
                                        "biggestResourceSize=%,d, biggestResourceFilename='%s')",
                                note.id().id(), note.title(),
                                resourceService.noteSizeWithResources(note),
                                resourceService.noteResourceNumber(note, LinkType.JOPLIN),
                                biggestResource.map(resource -> resource.resourceFile().length()).orElse(0L),
                                biggestResource.map(resource -> resource.resourceFile().getName()).orElse("-")
                        );
                    })
                    .forEach(System.out::println);
        }
        log.info("Finished");
    }
}
