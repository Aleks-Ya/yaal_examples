package joplin.search_and_replace_body;

import joplin.common.db.SqliteService;
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
            var noteId1 = new NoteId("bf941399ecf6497b98693f618d2798bd");
            var newText = "{width=100 height=200}";
            var changedNoteIds = List.of(noteId1);

            var noteNumber = 9;
            var allNotes = sqliteService.fetchAllNotes();
            assertThat(allNotes).hasSize(noteNumber);
            var unchangedNotes = allNotes.stream().filter(note -> !changedNoteIds.contains(note.id())).toList();
            var changedNotes = allNotes.stream().filter(note -> changedNoteIds.contains(note.id())).toList();

            assertThat(sqliteService.fetchNoteById(noteId1).orElseThrow().body()).doesNotContain(newText);

            var converter = new Converter(sqliteService);
            converter.convert();
            assertThat(sqliteService.fetchAllNotes()).hasSize(noteNumber).containsAll(unchangedNotes);
            assertThat(sqliteService.fetchAllNotes()).hasSize(noteNumber).doesNotContainAnyElementsOf(changedNotes);

            assertThat(sqliteService.fetchNoteById(noteId1).orElseThrow().body()).contains(newText);
        }
    }
}