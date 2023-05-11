package joplin.common.note;

import joplin.common.db.SqliteRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class NoteService implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(NoteService.class);
    private final SqliteRepo sqliteRepo;

    public NoteService(SqliteRepo sqliteRepo) {
        this.sqliteRepo = sqliteRepo;
    }

    public Optional<Note> fetchNoteById(NoteId id) {
        return sqliteRepo.fetchNoteById(id);
    }

    public void updateNote(Note note) {
        sqliteRepo.updateNote(note);
    }

    public List<Note> fetchAllNotes() {
        return sqliteRepo.fetchAllNotes();
    }

    public boolean updateNoteBody(Replacement replacement) {
        return updateNote(replacement, true);
    }

    public boolean updateNoteTitle(Replacement replacement) {
        return updateNote(replacement, false);
    }

    private boolean updateNote(Replacement replacement, boolean updateBody) {
        var oldText = replacement.oldText();
        var newText = replacement.newText();
        var id = replacement.noteId();
        var note = fetchNoteById(id).orElseThrow();
        if (oldText.equals(newText)) {
            log.info("Skip update the same text: noteId={}, title='{}', text='{}'", id.id(), note.title(), oldText);
            return false;
        }
        var newNote = updateBody ? newBody(note, oldText, newText) : newTitle(note, oldText, newText);
        updateNote(newNote);
        return true;
    }

    private Note newTitle(Note note, String oldText, String newText) {
        var newTitle = note.title().replace(oldText, newText);
        log.info("Update note title: noteId={}, title='{}', oldText='{}', newText='{}'", note.noteId(), note.title(), oldText, newText);
        return note.withTitle(newTitle);
    }

    private Note newBody(Note note, String oldText, String newText) {
        var newBody = note.body().replace(oldText, newText);
        log.info("Update note body: noteId={}, title='{}', oldText='{}', newText='{}'", note.noteId(), note.title(), oldText, newText);
        return note.withBody(newBody);
    }

    @Override
    public void close() {
        sqliteRepo.close();
    }
}
