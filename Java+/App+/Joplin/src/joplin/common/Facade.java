package joplin.common;

import joplin.common.link.LinkService;
import joplin.common.link.LinkType;
import joplin.common.note.Note;
import joplin.common.note.NoteId;
import joplin.common.note.NoteService;
import joplin.common.note.Replacement;
import joplin.common.resource.Resource;
import joplin.common.resource.ResourceService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.entry;

public class Facade implements AutoCloseable {
    private static final List<String> IMAGE_FILE_EXTENSIONS = List.of("jpg", "jpeg", "png");
    private final NoteService noteService;
    private final LinkService linkService;
    private final ResourceService resourceService;

    public Facade(NoteService noteService, LinkService linkService, ResourceService resourceService) {
        this.noteService = noteService;
        this.linkService = linkService;
        this.resourceService = resourceService;
    }

    public List<Note> fetchAllNotes() {
        return resourceService.addLinkResources(linkService.parseLinks(noteService.fetchAllNotes()));
    }

    public Optional<Note> fetchNoteById(NoteId id) {
        return noteService.fetchNoteById(id)
                .map(linkService::parseLinks)
                .map(resourceService::addLinkResources);
    }

    public List<Note> findBiggestNotes(int noteNumber) {
        var allNotes = noteService.fetchAllNotes();
        var linkNotes = linkService.parseLinks(allNotes);
        var resourceNotes = resourceService.addLinkResources(linkNotes);
        return resourceNotes.stream()
                .sorted((note1, note2) -> noteSizeWithResources(note2)
                        .compareTo(noteSizeWithResources(note1)))
                .limit(noteNumber)
                .toList();
    }

    public Long noteResourceNumber(Note note, LinkType linkType) {
        return note.links().stream()
                .filter(link -> link.resource() != null)
                .filter(link -> link.type() == linkType)
                .count();
    }

    public List<Note> findNotesWithBiggestSingleResource(int noteNumber) {
        var allNotes = noteService.fetchAllNotes();
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

    public Long noteSizeWithResources(Note note) {
        var bodySize = note.body().length();
        var resourceSize = note.links().stream()
                .filter(link -> link.resource() != null)
                .mapToLong(link -> link.resource().resourceFile().length())
                .sum();
        return bodySize + resourceSize;
    }

    public void updateNote(Note note) {
        noteService.updateNote(note);
    }

    public Optional<Resource> biggestResource(Note note) {
        return resourceService.biggestResource(note);
    }

    public Optional<Resource> biggestResource(Note note, List<String> extensions) {
        return resourceService.biggestResource(note, extensions);
    }

    public boolean updateNoteBody(Replacement replacement) {
        return noteService.updateNoteBody(replacement);
    }

    public boolean updateNoteTitle(Replacement replacement) {
        return noteService.updateNoteTitle(replacement);
    }

    @Override
    public void close() {
        noteService.close();
    }
}
