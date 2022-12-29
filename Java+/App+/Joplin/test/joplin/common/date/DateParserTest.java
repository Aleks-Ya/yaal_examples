package joplin.common.date;

import joplin.common.note.NoteId;
import org.junit.jupiter.api.Test;

import static java.time.LocalDate.parse;
import static org.assertj.core.api.Assertions.assertThat;

class DateParserTest {
    public static final DateParser dateParser = new DateParser();
    private static final NoteId noteId = new NoteId("e6900575a9724851bdd8b02d2411967d");

    @Test
    void parseDatesInTitle() {
        var title = "Positive: 2022-12-25 11.08.16 2015.08.24ПН 13.12.2017ср 11.12.2017 Negative: 17-23.11.14";
        var dates = dateParser.parseDates(title, noteId);
        assertThat(dates).containsExactlyInAnyOrder(
                new Date(noteId, "2022-12-25", parse("2022-12-25")),
                new Date(noteId, "11.08.16", parse("2016-08-11")),
                new Date(noteId, "2015.08.24ПН", parse("2015-08-24")),
                new Date(noteId, "13.12.2017ср", parse("2017-12-13")),
                new Date(noteId, "11.12.2017", parse("2017-12-11"))
        );
    }

    @Test
    void specificTitles() {
        assertThat(dateParser.parseDates("2016-09-26 email", noteId))
                .containsExactly(new Date(noteId, "2016-09-26", parse("2016-09-26")));
        assertThat(dateParser.parseDates("Plan on 12/29/2016", noteId))
                .containsExactly(new Date(noteId, "12/29/2016", parse("2016-12-29")));
        assertThat(dateParser.parseDates("Plan on 12/4/2016", noteId))
                .containsExactly(new Date(noteId, "12/4/2016", parse("2016-12-04")));
        assertThat(dateParser.parseDates("Plan on 9/24/2016", noteId))
                .containsExactly(new Date(noteId, "9/24/2016", parse("2016-09-24")));
        assertThat(dateParser.parseDates("Plan on 9/4/2016", noteId))
                .containsExactly(new Date(noteId, "9/4/2016", parse("2016-09-04")));
        assertThat(dateParser.parseDates("Plan on 2022-12-25SU", noteId))
                .containsExactly(new Date(noteId, "2022-12-25SU", parse("2022-12-25")));
    }
}