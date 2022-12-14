package joplin.search_and_replace;

import joplin.NoteEntity;
import joplin.SqliteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find and replace in Joplin note body.
 */
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Started");
        var sqliteDbFile = "/home/aleks/.config/joplin-desktop/database.sqlite";
        var target = "\nheight=";
        var replacement = " height=";
        try (var sqliteService = new SqliteService(sqliteDbFile)) {
            var allNotes = sqliteService.fetchAllNotes();
            var updated = allNotes.stream()
                    .filter(note -> note.body().contains(target))
                    .map(note -> {
                        var newBody = note.body().replaceAll(target, replacement);
                        log.info("Replacing in note: id={}, title='{}'", note.id(), note.title());
                        return new NoteEntity(note.id(), note.title(), newBody, note.markupLanguage(), note.updatedTime());
                    })
                    .peek(sqliteService::updateNote)
                    .toList().size();
            log.info("Finished (updated {} notes, total {} notes)", updated, allNotes.size());
        }
    }
}
