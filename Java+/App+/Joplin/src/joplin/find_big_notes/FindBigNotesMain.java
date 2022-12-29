package joplin.find_big_notes;

import joplin.common.db.SqliteService;
import joplin.common.link.LinkService;
import joplin.common.link.LinkType;
import joplin.common.note.NoteService;
import joplin.common.resource.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Prints the biggest notes including attachment sizes.
 */
public class FindBigNotesMain {
    private static final Logger log = LoggerFactory.getLogger(FindBigNotesMain.class);

    public static void main(String[] args) {
        log.info("Started");
        var joplinDir = "/home/aleks/.config/joplin-desktop";
        var sqliteDbFile = joplinDir + "/database.sqlite";
        var resourceService = new ResourceService(joplinDir);
        var linkService = new LinkService();
        try (var sqliteService = new SqliteService(sqliteDbFile, true)) {
            var noteService = new NoteService(sqliteService, linkService, resourceService);
            System.out.println("\nBIGGEST NOTES");
            noteService.findBiggestNotes(100).stream()
                    .map(note -> String.format("noteId='%s', title='%s', size=%,d, resourceNum=%d, biggestResourceSize=%,d)",
                            note.id().id(), note.title(),
                            resourceService.noteSizeWithResources(note),
                            resourceService.noteResourceNumber(note, LinkType.JOPLIN),
                            resourceService.biggestResource(note)
                                    .map(resource -> resource.resourceFile().length()).orElse(0L)))
                    .forEach(System.out::println);
            System.out.println("\n\nNOTES WITH BIGGEST SINGLE RESOURCE (IMAGES ONLY)");
            noteService.findNotesWithBiggestSingleResource(100).stream()
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
