package joplin.search_and_replace_link;

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

    void update(LinkReplacement replacement) {
        var oldNote = sqliteService.fetchNoteById(replacement.link().note().id()).orElseThrow();
        var newBody = oldNote.body().replace(replacement.link().element(), replacement.newSubstring());
        var newNote = new NoteEntity(oldNote.id(), oldNote.title(), newBody, oldNote.markupLanguage(), oldNote.updatedTime());
        log.info("Update link: id={}, text='{}', element='{}', replacement='{}'",
                oldNote.id(), oldNote.title(), replacement.link().element(), replacement.newSubstring());
        sqliteService.updateNote(newNote);
    }
}
