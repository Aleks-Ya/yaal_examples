package joplin.html_to_markdown;

import joplin.NoteId;
import joplin.SqliteService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static joplin.SqliteUtils.populateDatabase;
import static org.assertj.core.api.Assertions.assertThat;

class ConverterTest {
    @Test
    void convert() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var noteId1 = new NoteId("6ded77a0daca4ff3828a9241dd0ae0ed");
            var changedNoteIds = List.of(noteId1);

            var noteNumber = 9;
            var allNotes = sqliteService.fetchAllNotes();
            assertThat(allNotes).hasSize(noteNumber);
            var unchangedNotes = allNotes.stream().filter(note -> !changedNoteIds.contains(note.id())).toList();
            var changedNotes = allNotes.stream().filter(note -> changedNoteIds.contains(note.id())).toList();

            var htmlSubstring = "<en-note>";
            assertThat(sqliteService.fetchNoteById(noteId1).orElseThrow().body()).contains(htmlSubstring);

            var converter = new Converter(sqliteService);
            converter.convert("4b2503c75de64099b68262878dc240b8");
            assertThat(sqliteService.fetchAllNotes()).hasSize(noteNumber).containsAll(unchangedNotes);
            assertThat(sqliteService.fetchAllNotes()).hasSize(noteNumber).doesNotContainAnyElementsOf(changedNotes);

            assertThat(sqliteService.fetchNoteById(noteId1).orElseThrow().body()).doesNotContain(htmlSubstring);
            assertThat(sqliteService.fetchNoteById(noteId1).orElseThrow().body()).isEqualTo("Hello John,\n\n  \n\nBye John\n\nParagraph \\#2\n\nAnother paragraph\n\n  ");
        }
    }
}