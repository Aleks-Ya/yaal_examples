package joplin.common.note;

import joplin.common.db.SqliteService;
import joplin.common.link.LinkService;
import joplin.common.link.LinkType;
import joplin.common.resource.ResourceService;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class NoteService {
    private static final List<String> IMAGE_FILE_EXTENSIONS = List.of("jpg", "jpeg", "png");
    private final SqliteService sqliteService;
    private final LinkService linkService;
    private final ResourceService resourceService;

    public NoteService(SqliteService sqliteService, LinkService linkService, ResourceService resourceService) {
        this.sqliteService = sqliteService;
        this.linkService = linkService;
        this.resourceService = resourceService;
    }

    public List<Note> findBiggestNotes(int noteNumber) {
        var allNotes = sqliteService.fetchAllNotes();
        var linkNotes = linkService.parseLinks(allNotes, LinkType.JOPLIN);
        var resourceNotes = resourceService.addLinkResources(linkNotes);
        return resourceNotes.stream()
                .sorted((note1, note2) -> resourceService.noteSizeWithResources(note2)
                        .compareTo(resourceService.noteSizeWithResources(note1)))
                .limit(noteNumber)
                .toList();
    }

    public List<Note> findNotesWithBiggestSingleResource(int noteNumber) {
        var allNotes = sqliteService.fetchAllNotes();
        var linkNotes = linkService.parseLinks(allNotes, LinkType.JOPLIN);
        var resourceNotes = resourceService.addLinkResources(linkNotes);
        return resourceNotes.stream()
                .map(note -> entry(note, resourceService.biggestResource(note, IMAGE_FILE_EXTENSIONS)))
                .filter(entry -> entry.getValue().isPresent())
                .map(entry -> entry(entry.getKey(), entry.getValue().get()))
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue().resourceFile().length(), entry1.getValue().resourceFile().length()))
                .limit(noteNumber)
                .map(Map.Entry::getKey)
                .toList();
    }
}
