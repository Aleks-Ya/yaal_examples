package joplin;

public record NoteEntity(NoteId id, String title, String body, MarkupLanguage markupLanguage, Long updatedTime) {
}
