package joplin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoteBodyReplacer {
    private static final Logger log = LoggerFactory.getLogger(NoteBodyReplacer.class);
    private final SqliteService sqliteService;

    public NoteBodyReplacer(SqliteService sqliteService) {
        this.sqliteService = sqliteService;
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
        var note = sqliteService.fetchNoteById(id).orElseThrow();
        if (oldText.equals(newText)) {
            log.info("Skip update the same text: id={}, title='{}', text='{}'", id.id(), note.title(), oldText);
            return false;
        }
        var newNote = updateBody ? newBody(note, oldText, newText) : newTitle(note, oldText, newText);
        sqliteService.updateNote(newNote);
        return true;
    }

    private NoteEntity newTitle(NoteEntity note, String oldText, String newText) {
        var newTitle = note.title().replace(oldText, newText);
        log.info("Update note title: id={}, title='{}', oldText='{}', newText='{}'", note.id(), note.title(), oldText, newText);
        return new NoteEntity(note.id(), newTitle, note.body(), note.markupLanguage(), note.updatedTime());
    }

    private NoteEntity newBody(NoteEntity note, String oldText, String newText) {
        var newBody = note.body().replace(oldText, newText);
        log.info("Update note body: id={}, title='{}', oldText='{}', newText='{}'", note.id(), note.title(), oldText, newText);
        return new NoteEntity(note.id(), note.title(), newBody, note.markupLanguage(), note.updatedTime());
    }
}
