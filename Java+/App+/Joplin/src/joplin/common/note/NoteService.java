package joplin.common.note;

import joplin.common.db.SqliteService;

import java.util.List;
import java.util.Optional;

public class NoteService implements AutoCloseable {
    private final SqliteService sqliteService;

    public NoteService(SqliteService sqliteService) {
        this.sqliteService = sqliteService;
    }

    public Optional<Note> fetchNoteById(NoteId id) {
        return sqliteService.fetchNoteById(id);
    }

    public List<Note> findNotesByTitle(String title) {
        return sqliteService.fetchNotesByTitle(title);
    }

    public void updateNote(Note note) {
        sqliteService.updateNote(note);
    }

    public List<Note> fetchAllNotes() {
        return sqliteService.fetchAllNotes();
    }

    public List<Note> fetchNotes(String notebookId, MarkupLanguage markupLanguage) {
        return sqliteService.fetchNotes(notebookId, markupLanguage);
    }

    @Override
    public void close() {
        sqliteService.close();
    }
}
