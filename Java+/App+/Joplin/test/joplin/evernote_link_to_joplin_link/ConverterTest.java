package joplin.evernote_link_to_joplin_link;

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
            var noteId1 = new NoteId("ba9bdb7bc5444d5b85bdabfd9a211337");
            var noteId2 = new NoteId("a2d7d7efe84a47bf8ffde18121477efd");
            var link1 = "[Русское название статьи 2](:/bf941399ecf6497b98693f618d2798bd)";
            var link2 = "[Meal\\'s \\\"shopping\\\" list](:/e6900575a9724851bdd8b02d2411967d)";
            var changedNoteIds = List.of(noteId1, noteId2);

            var noteNumber = 9;
            var allNotes = sqliteService.fetchAllNotes();
            assertThat(allNotes).hasSize(noteNumber);
            var unchangedNotes = allNotes.stream().filter(note -> !changedNoteIds.contains(note.id())).toList();
            var changedNotes = allNotes.stream().filter(note -> changedNoteIds.contains(note.id())).toList();

            assertThat(sqliteService.fetchNoteById(noteId1).orElseThrow().body()).doesNotContain(link1);
            assertThat(sqliteService.fetchNoteById(noteId2).orElseThrow().body()).doesNotContain(link2);

            var converter = new Converter(sqliteService);
            converter.convert();
            assertThat(sqliteService.fetchAllNotes()).hasSize(noteNumber).containsAll(unchangedNotes);
            assertThat(sqliteService.fetchAllNotes()).hasSize(noteNumber).doesNotContainAnyElementsOf(changedNotes);

            assertThat(sqliteService.fetchNoteById(noteId1).orElseThrow().body()).contains(link1);
            assertThat(sqliteService.fetchNoteById(noteId2).orElseThrow().body()).contains(link2);
        }
    }
}