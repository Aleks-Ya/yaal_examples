package joplin.format_dates_in_titles;

import joplin.common.date.DateParser;
import joplin.common.db.SqliteService;
import joplin.common.note.NoteBodyReplacer;
import joplin.common.note.Replacement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Collection;

class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private final SqliteService sqliteService;

    Converter(SqliteService sqliteService) {
        this.sqliteService = sqliteService;
    }

    private static String formatDate(LocalDate localDate) {
        var dayOfWeek = switch (localDate.getDayOfWeek()) {
            case MONDAY -> "MO";
            case TUESDAY -> "TU";
            case WEDNESDAY -> "WE";
            case THURSDAY -> "TH";
            case FRIDAY -> "FR";
            case SATURDAY -> "SA";
            case SUNDAY -> "SU";
        };
        return localDate + dayOfWeek;
    }

    void convert() {
        var dateParser = new DateParser();
        var noteUpdater = new NoteBodyReplacer(sqliteService);
        var allNotes = sqliteService.fetchAllNotes();
        var updatedNumber = allNotes.stream()
                .map(note -> dateParser.parseDates(note.title(), note.id()))
                .flatMap(Collection::stream)
                .map(date -> new Replacement(date.noteId(), date.element(), formatDate(date.localDate())))
                .map(noteUpdater::updateNoteTitle)
                .filter(updated -> updated)
                .count();
        log.info("Finished (updated {} notes, total {} notes)", updatedNumber, allNotes.size());
    }
}
