package joplin.search_and_replace_body;

import joplin.NoteBodyUpdater;
import joplin.Replacement;
import joplin.SqliteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private final SqliteService sqliteService;

    Converter(SqliteService sqliteService) {
        this.sqliteService = sqliteService;
    }

    void convert() {
        var noteUpdater = new NoteBodyUpdater(sqliteService);
        var oldText = "\nheight=";
        var newText = " height=";
        var allNotes = sqliteService.fetchAllNotes();
        var updated = allNotes.stream()
                .filter(note -> note.body().contains(oldText))
                .map(note -> new Replacement(note.id(), oldText, newText))
                .peek(noteUpdater::updateNote)
                .toList().size();
        log.info("Finished (updated {} notes, total {} notes)", updated, allNotes.size());
    }
}
