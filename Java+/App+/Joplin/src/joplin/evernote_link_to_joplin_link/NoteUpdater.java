package joplin.evernote_link_to_joplin_link;

import joplin.NoteEntity;
import joplin.SqliteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class NoteUpdater {
    private static final Logger log = LoggerFactory.getLogger(NoteUpdater.class);
    private final SqliteService sqliteService;

    NoteUpdater(SqliteService sqliteService) {
        this.sqliteService = sqliteService;
    }

    void updateNote(JoplinLink joplinLink) {
        var id = joplinLink.evernoteLink().noteEntity().id();
        var note = sqliteService.fetchNoteById(id).orElseThrow();
        var oldBody = note.body();
        var target = joplinLink.evernoteLink().matchedText();
        var replacement = joplinLink.matchedTextReplacement();
        var newBody = oldBody.replace(target, replacement);
        log.info("Update link: id={}, title='{}', target='{}', replacement='{}'", id, note.title(), target, replacement);
        var newNote = new NoteEntity(note.id(), note.title(), newBody, note.markupLanguage(), note.updatedTime());
        sqliteService.updateNote(newNote);
    }
}
