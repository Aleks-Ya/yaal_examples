package joplin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoteBodyUpdater {
    private static final Logger log = LoggerFactory.getLogger(NoteBodyUpdater.class);
    private final SqliteService sqliteService;

    public NoteBodyUpdater(SqliteService sqliteService) {
        this.sqliteService = sqliteService;
    }

    public boolean updateNote(Replacement replacement) {
        var oldText = replacement.oldText();
        var newText = replacement.newText();
        var id = replacement.noteId();
        var note = sqliteService.fetchNoteById(id).orElseThrow();
        if (oldText.equals(newText)) {
            log.info("Skip update the same text: id={}, title='{}', text='{}'", id.id(), note.title(), oldText);
            return false;
        }
        var newBody = note.body().replace(oldText, newText);
        log.info("Update link: id={}, title='{}', oldText='{}', newText='{}'", id.id(), note.title(), oldText, newText);
        var newNote = new NoteEntity(note.id(), note.title(), newBody, note.markupLanguage(), note.updatedTime());
        sqliteService.updateNote(newNote);
        return true;
    }
}
