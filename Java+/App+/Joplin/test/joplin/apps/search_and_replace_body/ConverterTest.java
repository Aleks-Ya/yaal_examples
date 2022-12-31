package joplin.apps.search_and_replace_body;

import joplin.Utils;
import joplin.common.note.NoteId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConverterTest {
    @Test
    void convert() {
        try (var facade = Utils.createFacadeFake()) {
            var noteId1 = new NoteId("bf941399ecf6497b98693f618d2798bd");
            var newText = "{width=100 height=200}";
            var changedNoteIds = List.of(noteId1);

            var noteNumber = 9;
            var allNotes = facade.fetchAllNotes();
            assertThat(allNotes).hasSize(noteNumber);
            var unchangedNotes = allNotes.stream().filter(note -> !changedNoteIds.contains(note.noteId())).toList();
            var changedNotes = allNotes.stream().filter(note -> changedNoteIds.contains(note.noteId())).toList();

            assertThat(facade.fetchNoteById(noteId1).orElseThrow().body()).doesNotContain(newText);

            var converter = new Converter(facade);
            converter.convert();
            assertThat(facade.fetchAllNotes()).hasSize(noteNumber).containsAll(unchangedNotes);
            assertThat(facade.fetchAllNotes()).hasSize(noteNumber).doesNotContainAnyElementsOf(changedNotes);

            assertThat(facade.fetchNoteById(noteId1).orElseThrow().body()).contains(newText);
        }
    }
}