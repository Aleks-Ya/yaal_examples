package joplin.apps.format_dates_in_titles;

import joplin.common.Facade;
import joplin.common.date.DateParser;
import joplin.common.note.Replacement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Collection;

class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private final Facade facade;

    Converter(Facade facade) {
        this.facade = facade;
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
        var allNotes = facade.fetchAllNotes();
        var updatedNumber = allNotes.stream()
                .map(note -> dateParser.parseDates(note.title(), note.noteId()))
                .flatMap(Collection::stream)
                .map(date -> new Replacement(date.noteId(), date.element(), formatDate(date.localDate())))
                .map(facade::updateNoteTitle)
                .filter(updated -> updated)
                .count();
        log.info("Finished (updated {} notes, total {} notes)", updatedNumber, allNotes.size());
    }
}
