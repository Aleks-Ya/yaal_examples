package joplin.search_and_replace_link;

import joplin.common.db.SqliteService;
import joplin.common.link.LinkService;
import joplin.common.note.NoteBodyReplacer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private final SqliteService sqliteService;

    Converter(SqliteService sqliteService) {
        this.sqliteService = sqliteService;
    }

    void convert() {
        var linkService = new LinkService();
        var linkReplacer = new LinkReplacer();
        var noteUpdater = new NoteBodyReplacer(sqliteService);
        var allNotes = sqliteService.fetchAllNotes();
        var updatedNumber = linkService.parseLinks(allNotes).stream()
                .map(linkReplacer::replace)
                .flatMap(Collection::stream)
                .map(noteUpdater::updateNoteBody)
                .filter(updated -> updated)
                .count();
        log.info("Finished (updated {} notes, total {} notes)", updatedNumber, allNotes.size());
    }
}
