package joplin.apps.html_to_markdown;

import joplin.Utils;
import joplin.common.note.NoteId;
import joplin.common.note.NotebookId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConverterTest {
    @Test
    void convert() {
        try (var facade = Utils.createFacadeFake()) {
            var noteId1 = new NoteId("6ded77a0daca4ff3828a9241dd0ae0ed");
            var changedNoteIds = List.of(noteId1);

            var noteNumber = 9;
            var allNotes = facade.fetchAllNotes();
            assertThat(allNotes).hasSize(noteNumber);
            var unchangedNotes = allNotes.stream().filter(note -> !changedNoteIds.contains(note.noteId())).toList();
            var changedNotes = allNotes.stream().filter(note -> changedNoteIds.contains(note.noteId())).toList();

            var htmlSubstring = "<en-note>";
            assertThat(facade.fetchNoteById(noteId1).orElseThrow().body()).contains(htmlSubstring);

            var converter = new Converter(facade);
            converter.convert(new NotebookId("4b2503c75de64099b68262878dc240b8"));
            assertThat(facade.fetchAllNotes()).hasSize(noteNumber).containsAll(unchangedNotes);
            assertThat(facade.fetchAllNotes()).hasSize(noteNumber).doesNotContainAnyElementsOf(changedNotes);

            assertThat(facade.fetchNoteById(noteId1).orElseThrow().body()).doesNotContain(htmlSubstring);
            assertThat(facade.fetchNoteById(noteId1).orElseThrow().body()).isEqualTo("Hello John,\n\n  \n\nBye John\n\nParagraph \\#2\n\nAnother paragraph\n\n  ");
        }
    }
}