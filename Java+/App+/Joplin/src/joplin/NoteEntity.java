package joplin;

public record NoteEntity(String id, String title, String body, MarkupLanguage markupLanguage, Long updatedTime) {
}
