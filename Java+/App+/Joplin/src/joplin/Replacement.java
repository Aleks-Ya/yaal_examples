package joplin;

public record Replacement(NoteId noteId, String oldText, String newText) {
}
