package joplin.common.note;

import joplin.common.link.Link;

import java.util.List;

public record Note(NoteId noteId,
                   NotebookId notebookId,
                   String title,
                   String body,
                   MarkupLanguage markupLanguage,
                   Long updatedTime,
                   List<Link> links) {

    public Note withLinks(List<Link> links) {
        return new Note(noteId, notebookId, title, body, markupLanguage, updatedTime, links);
    }

    public Note withTitle(String title) {
        return new Note(noteId, notebookId, title, body, markupLanguage, updatedTime, links);
    }

    public Note withBody(String body) {
        return new Note(noteId, notebookId, title, body, markupLanguage, updatedTime, links);
    }

    public Note withMarkupLanguage(MarkupLanguage markupLanguage) {
        return new Note(noteId, notebookId, title, body, markupLanguage, updatedTime, links);
    }
}
