package joplin.apps.img_tag_to_resource_link;

import joplin.Utils;
import joplin.common.note.NoteId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static joplin.Notes.NOTE_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;

class ConverterTest {
    @Test
    void convert() {
        try (var facade = Utils.createFacadeFake()) {
            var noteId = new NoteId("fb1d8d649980448389a4ba3749eb6856");
            var noteBefore = facade.fetchNoteById(noteId).orElseThrow();
            var imgTag1 = """
                    | Normal attributes order<br><img src=":/681c0cd67b3d44378f47acc06a5e234e" alt="013bb9bbbc51b7d2145153b7640e7fdc.png" width="324" height="243" class="jop-noMdConv"> |""";
            var imgTag2 = """
                    | Different attributes order<br><img alt="f7316cd7bda69f9f9649ce5c750eee2e.png" width="284" height="179" class="jop-noMdConv" src=":/fe3aac71c1e44393a4e54666e697dfc8"> |""";
            var imgTag3 = """
                    | One more IMG resource link<br><img src=":/c0e8303145e04dfe8d5b908a88945f0c" alt="A picture" width="233" height="233" class="jop-noMdConv"> |""";
            var notImgTag4 = """
                    | Not a IMG resource link<br><img src="https://images.com/abc" width="304" height="171" class="jop-noMdConv"> |""";
            var expLink1 = "![013bb9bbbc51b7d2145153b7640e7fdc.png](:/681c0cd67b3d44378f47acc06a5e234e)";
            var expLink2 = "![f7316cd7bda69f9f9649ce5c750eee2e.png](:/fe3aac71c1e44393a4e54666e697dfc8)";
            var expLink3 = "![A picture](:/c0e8303145e04dfe8d5b908a88945f0c)";
            assertThat(noteBefore.body()).contains(imgTag1, imgTag2, imgTag3, notImgTag4).doesNotContain(expLink1, expLink2, expLink3);
            assertThat(noteBefore.links()).isEmpty();

            var changedNoteIds = List.of(noteId);
            var allNotes = facade.fetchAllNotes();
            assertThat(allNotes).hasSize(NOTE_NUMBER);
            var unchangedNotes = allNotes.stream().filter(note -> !changedNoteIds.contains(note.noteId())).toList();
            var changedNotes = allNotes.stream().filter(note -> changedNoteIds.contains(note.noteId())).toList();

            var converter = new Converter(facade);
            converter.convert();
            assertThat(facade.fetchAllNotes()).hasSize(NOTE_NUMBER).containsAll(unchangedNotes);
            assertThat(facade.fetchAllNotes()).hasSize(NOTE_NUMBER).doesNotContainAnyElementsOf(changedNotes);

            var noteAfter = facade.fetchNoteById(noteId).orElseThrow();
            assertThat(noteAfter.body()).doesNotContain(imgTag1, imgTag2).contains(expLink1, expLink2, expLink3, notImgTag4);
            assertThat(noteAfter.links()).hasSize(3);
        }
    }
}