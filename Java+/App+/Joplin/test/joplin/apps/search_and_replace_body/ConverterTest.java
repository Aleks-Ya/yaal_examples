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
            var noteService = facade.getNoteService();
            var noteId1 = new NoteId("bf941399ecf6497b98693f618d2798bd");
            var newText = "{width=100 height=200}";
            var changedNoteIds = List.of(noteId1);

            var noteNumber = 9;
            var allNotes = noteService.fetchAllNotes();
            assertThat(allNotes).hasSize(noteNumber);
            var unchangedNotes = allNotes.stream().filter(note -> !changedNoteIds.contains(note.id())).toList();
            var changedNotes = allNotes.stream().filter(note -> changedNoteIds.contains(note.id())).toList();

            assertThat(facade.fetchNoteByIdWithResources(noteId1).orElseThrow().body()).doesNotContain(newText);

            var converter = new Converter(facade);
            converter.convert();
            assertThat(noteService.fetchAllNotes()).hasSize(noteNumber).containsAll(unchangedNotes);
            assertThat(noteService.fetchAllNotes()).hasSize(noteNumber).doesNotContainAnyElementsOf(changedNotes);

            assertThat(facade.fetchNoteByIdWithResources(noteId1).orElseThrow().body()).contains(newText);
        }
    }
}