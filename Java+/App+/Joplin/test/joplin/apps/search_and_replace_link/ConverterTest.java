package joplin.apps.search_and_replace_link;

import joplin.Utils;
import joplin.common.note.NoteId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static joplin.Notes.NOTE_1;
import static joplin.Notes.NOTE_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;

class ConverterTest {
    @Test
    void convert() {
        try (var facade = Utils.createFacadeFake()) {
            var noteId1 = NOTE_1.noteId();
            var noteId2 = new NoteId("63551b448cf64362a1d747561b737c83");
            var noteId3 = new NoteId("a2d7d7efe84a47bf8ffde18121477efd");
            var noteId4 = new NoteId("ba9bdb7bc5444d5b85bdabfd9a211337");
            var link1 = "[Joplin link 1](:/db65929324925ccbfa789f95cdd293ba)";
            var link2 = "[Plan Risk Management](evernote:///view/48821034/s241/2fb4b9dd-7fbd-414c-831c-fc4c75c25d90/2fb4b9dd-7fbd-414c-831c-fc4c75c25d90/)";
            var link3 = "[Meal\\'s \\\"shopping\\\" list](evernote:///view/48821034/s241/f6970881-a927-49dc-a73b-a7cc5c9348b3/f6970881-a927-49dc-a73b-a7cc5c9348b3/)";
            var link4 = "[Русское название статьи 2, запятая](evernote:///view/48821034/s241/87b5042e-7d6e-40bc-b434-2f4daf68722a/87b5042e-7d6e-40bc-b434-2f4daf68722a/)";
            var changedNoteIds = List.of(noteId1, noteId2, noteId3, noteId4);

            var allNotes = facade.fetchAllNotes();
            assertThat(allNotes).hasSize(NOTE_NUMBER);
            var unchangedNotes = allNotes.stream().filter(note -> !changedNoteIds.contains(note.noteId())).toList();
            var changedNotes = allNotes.stream().filter(note -> changedNoteIds.contains(note.noteId())).toList();

            assertThat(facade.fetchNoteById(noteId1).orElseThrow().body()).doesNotContain(link1);
            assertThat(facade.fetchNoteById(noteId2).orElseThrow().body()).doesNotContain(link2);
            assertThat(facade.fetchNoteById(noteId3).orElseThrow().body()).doesNotContain(link3);
            assertThat(facade.fetchNoteById(noteId4).orElseThrow().body()).doesNotContain(link4);

            var converter = new Converter(facade);
            converter.convert();
            assertThat(facade.fetchAllNotes()).hasSize(NOTE_NUMBER).containsAll(unchangedNotes);
            assertThat(facade.fetchAllNotes()).hasSize(NOTE_NUMBER).doesNotContainAnyElementsOf(changedNotes);

            assertThat(facade.fetchNoteById(noteId1).orElseThrow().body()).contains(link1);
            assertThat(facade.fetchNoteById(noteId2).orElseThrow().body()).contains(link2);
            assertThat(facade.fetchNoteById(noteId3).orElseThrow().body()).contains(link3);
            assertThat(facade.fetchNoteById(noteId4).orElseThrow().body()).contains(link4);
        }
    }
}