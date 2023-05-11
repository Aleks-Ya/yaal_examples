package joplin.apps.find_big_notes;

import joplin.common.Factory;
import joplin.common.link.Link;
import joplin.common.link.LinkType;
import joplin.common.note.Note;
import joplin.common.note.NoteId;
import joplin.common.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Find Joplin links with URL points to absent note or resource.
 */
public class FindOrphanJoplinLinksMain {
    private static final Logger log = LoggerFactory.getLogger(FindOrphanJoplinLinksMain.class);

    public static void main(String[] args) {
        log.info("Started");
        try (var facade = Factory.createFacadeProd(true)) {
            var allNotes = facade.fetchAllNotes();
            var allNoteIds = allNotes.stream().map(Note::noteId).map(NoteId::id).toList();
            var allResourceIds = facade.fetchAllResources().stream()
                    .map(Resource::resourceId)
                    .toList();
            var joplinLinks = allNotes.stream()
                    .map(Note::links)
                    .flatMap(Collection::stream)
                    .filter(link -> link.type() == LinkType.JOPLIN)
                    .toList();
            var orphanLinks = joplinLinks.stream()
                    .filter(link -> !allNoteIds.contains(link.url().replace(":/", "")))
                    .filter(link -> link.resource() == null || !allResourceIds.contains(link.resource().resourceId()))
                    .toList();
            log.info("Total orphan count: {}", orphanLinks.size());
            log.info("Orphan links:\n{}", orphanLinks.stream()
                    .map(Link::element)
                    .collect(Collectors.joining("\n")));
        }
        log.info("Finished");
    }
}
