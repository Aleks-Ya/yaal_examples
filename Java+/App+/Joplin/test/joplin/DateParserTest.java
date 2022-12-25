package joplin;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DateParserTest {

    @Test
    void parseDatesInTitle() {
        var title = "Positive: 2022-12-25 11.08.16 2015.08.24ПН 13.12.2017ср 11.12.2017 Negative: 17-23.11.14";
        var note = new NoteEntity(new NoteId("e6900575a9724851bdd8b02d2411967d"), title, "the body", MarkupLanguage.MD,
                Instant.now().toEpochMilli());
        var dateParser = new DateParser();
        var dates = dateParser.parseDates(title, note);
        assertThat(dates).containsExactlyInAnyOrder(
                new Date(note, "2022-12-25", LocalDate.parse("2022-12-25")),
                new Date(note, "11.08.16", LocalDate.parse("2016-08-11")),
                new Date(note, "2015.08.24ПН", LocalDate.parse("2015-08-24")),
                new Date(note, "13.12.2017ср", LocalDate.parse("2017-12-13")),
                new Date(note, "11.12.2017", LocalDate.parse("2017-12-11"))
        );
    }
}