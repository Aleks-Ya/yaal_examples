package joplin.common.note;

public record Replacement(NoteId noteId, String oldText, String newText) {
}
