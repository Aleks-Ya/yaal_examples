package joplin.search_and_replace_link;

import joplin.LinkParser;
import joplin.NoteBodyUpdater;
import joplin.SqliteService;
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
        var linkParser = new LinkParser();
        var linkReplacer = new LinkReplacer();
        var noteUpdater = new NoteBodyUpdater(sqliteService);
        var allNotes = sqliteService.fetchAllNotes();
        var modifiedReplacements = allNotes.stream()
                .map(linkParser::parseLinks)
                .flatMap(Collection::stream)
                .map(linkReplacer::replace)
                .filter(replacement -> !replacement.oldText().equals(replacement.newText()))
                .toList();
        modifiedReplacements.forEach(noteUpdater::updateNote);
        log.info("Finished (updated {} notes, total {} notes)", modifiedReplacements.size(), allNotes.size());
    }
}
