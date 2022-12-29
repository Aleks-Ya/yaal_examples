package joplin.format_dates_in_titles;

import joplin.common.db.SqliteService;
import joplin.common.note.Note;
import joplin.common.note.NoteId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static joplin.Utils.populateDatabase;
import static org.assertj.core.api.Assertions.assertThat;

class ConverterTest {
    @Test
    void convert() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var noteId1 = new NoteId("373b03cd772f451db9a96972d81ac6f6");
            var oldTitle1 = "Title with date 27.08.16";
            var newTitle1 = "Title with date 2016-08-27SA";
            var noteId2 = new NoteId("7e65ab82bf7841a09368df73a5114453");
            var oldTitle2 = "2015.09.24ЧТ";
            var newTitle2 = "2015-09-24TH";
            var noteId3 = new NoteId("6ded77a0daca4ff3828a9241dd0ae0ed");
            var oldTitle3 = "2016-09-26 email";
            var newTitle3 = "2016-09-26MO email";
            var oldBody1 = sqliteService.fetchNoteById(noteId1).orElseThrow().body();
            var oldBody2 = sqliteService.fetchNoteById(noteId2).orElseThrow().body();
            var oldBody3 = sqliteService.fetchNoteById(noteId3).orElseThrow().body();
            var changedNoteIds = List.of(noteId1, noteId2, noteId3);

            var noteNumber = 9;
            var allNotes = sqliteService.fetchAllNotes();
            assertThat(allNotes).hasSize(noteNumber);
            var unchangedNotes = allNotes.stream().filter(note -> !changedNoteIds.contains(note.id())).toList();
            var changedNotes = allNotes.stream().filter(note -> changedNoteIds.contains(note.id())).toList();

            assertThat(sqliteService.fetchNoteById(noteId1).orElseThrow())
                    .extracting(Note::title, Note::body)
                    .containsExactly(oldTitle1, oldBody1);
            assertThat(sqliteService.fetchNoteById(noteId2).orElseThrow())
                    .extracting(Note::title, Note::body)
                    .containsExactly(oldTitle2, oldBody2);
            assertThat(sqliteService.fetchNoteById(noteId3).orElseThrow())
                    .extracting(Note::title, Note::body)
                    .containsExactly(oldTitle3, oldBody3);

            var converter = new Converter(sqliteService);
            converter.convert();
            assertThat(sqliteService.fetchAllNotes()).hasSize(noteNumber).containsAll(unchangedNotes);
            assertThat(sqliteService.fetchAllNotes()).hasSize(noteNumber).doesNotContainAnyElementsOf(changedNotes);

            assertThat(sqliteService.fetchNoteById(noteId1).orElseThrow())
                    .extracting(Note::title, Note::body)
                    .containsExactly(newTitle1, oldBody1);
            assertThat(sqliteService.fetchNoteById(noteId2).orElseThrow())
                    .extracting(Note::title, Note::body)
                    .containsExactly(newTitle2, oldBody2);
            assertThat(sqliteService.fetchNoteById(noteId3).orElseThrow())
                    .extracting(Note::title, Note::body)
                    .containsExactly(newTitle3, oldBody3);
        }
    }
}