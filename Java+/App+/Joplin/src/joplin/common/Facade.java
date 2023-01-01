package joplin.common;

import joplin.common.link.Link;
import joplin.common.link.LinkService;
import joplin.common.note.Note;
import joplin.common.note.NoteId;
import joplin.common.note.NoteService;
import joplin.common.note.Replacement;
import joplin.common.resource.Resource;
import joplin.common.resource.ResourceService;
import util.Tuple2;

import java.io.File;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Facade implements AutoCloseable {
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

    public List<Resource> fetchAllResources() {
        return fetchAllNotes().stream()
                .map(Note::links)
                .flatMap(Collection::stream)
                .map(Link::resource)
                .filter(Objects::nonNull)
                .toList();
    }

    public Optional<Note> fetchNoteById(NoteId id) {
        return noteService.fetchNoteById(id)
                .map(linkService::parseLinks)
                .map(resourceService::addLinkResources);
    }

    public List<Note> fetchBiggestNotes(int noteNumber) {
        return fetchAllNotes().stream()
                .sorted(Comparator.comparing(Note::getNoteSize).reversed())
                .limit(noteNumber)
                .toList();
    }

    public List<Tuple2<Note, Resource>> fetchNotesWithBiggestSingleResource(int noteNumber, List<String> resourceExtensions) {
        return fetchAllNotes().stream()
                .map(note -> Tuple2.of(note, note.getBiggestResource(resourceExtensions)))
                .filter(entry -> entry.getRight().isPresent())
                .map(entry1 -> Tuple2.of(entry1.getLeft(), entry1.getRight().orElseThrow()))
                .sorted((entry1, entry2) -> Long.compare(entry2.getRight().getSize(), entry1.getRight().getSize()))
                .limit(noteNumber)
                .toList();
    }

    public List<File> findAllFilesInResourceDir() {
        return resourceService.findAllFilesInResourceDir().stream()
                .filter(file -> !file.getName().endsWith(".crypted"))
                .toList();
    }

    public void updateNote(Note note) {
        noteService.updateNote(note);
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
