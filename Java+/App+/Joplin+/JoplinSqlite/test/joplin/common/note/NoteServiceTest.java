package joplin.common.note;

import joplin.Utils;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static joplin.Notes.NOTE_1;
import static joplin.Notes.NOTE_2;
import static joplin.common.note.MarkupLanguage.MD;
import static org.assertj.core.api.Assertions.assertThat;

class NoteServiceTest {
    @Test
    void findBiggestNotes() {
        try (var facade = Utils.createFacadeFake()) {
            var biggestNotes = facade.fetchBiggestNotes(2);
            assertThat(biggestNotes).containsExactlyInAnyOrder(NOTE_1, NOTE_2);
        }
    }

    @Test
    void updateNote() {
        try (var facade = Utils.createFacadeFake()) {
            var noteId = new NoteId("a2d7d7efe84a47bf8ffde18121477efd");
            var replacement = new Replacement(noteId,
                    "[Meal\\'s \\\"shopping\\\"\n" +
                            "list](evernote:///view/48821034/s241/f6970881-a927-49dc-a73b-a7cc5c9348b3/f6970881-a927-49dc-a73b-a7cc5c9348b3/)",
                    "[Meal\\'s \\\"shopping\\\" list](:/e6900575a9724851bdd8b02d2411967d)");

            var updated = facade.updateNoteBody(replacement);
            assertThat(updated).isTrue();
            var newNote = facade.fetchNoteById(noteId).orElseThrow();
            assertThat(newNote).satisfies(note1 -> {
                        assertThat(note1.noteId().id()).isEqualTo("a2d7d7efe84a47bf8ffde18121477efd");
                        assertThat(note1.title()).isEqualTo("Discourse marker list");
                        assertThat(note1.body())
                                .contains("[Meal\\'s \\\"shopping\\\" list](:/e6900575a9724851bdd8b02d2411967d)")
                                .doesNotContain("[Meal shopping\nlist](evernote:///view/48821034/s241/f6970881-a927-49dc-a73b-a7cc5c9348b3/f6970881-a927-49dc-a73b-a7cc5c9348b3/)");
                        assertThat(note1.markupLanguage()).isEqualTo(MD);
                        var expUpdatedTime = 1670158519744L + Duration.ofMinutes(1).toMillis();
                        assertThat(note1.updatedTime()).isEqualTo(expUpdatedTime);
                        assertThat(note1.userUpdatedTime()).isEqualTo(expUpdatedTime);
                    }
            );
        }
    }

    @Test
    void skipSameNewText() {
        try (var facade = Utils.createFacadeFake()) {
            var noteId = new NoteId("a2d7d7efe84a47bf8ffde18121477efd");
            var oldNote = facade.fetchNoteById(noteId).orElseThrow();

            var text = "aaa";
            var replacement1 = new Replacement(noteId, text, text);
            var updated1 = facade.updateNoteBody(replacement1);
            assertThat(updated1).isFalse();
            assertThat(facade.fetchNoteById(noteId).orElseThrow()).isEqualTo(oldNote);

            var replacement2 = new Replacement(noteId, text, "bbb");
            var updated2 = facade.updateNoteBody(replacement2);
            assertThat(updated2).isTrue();
            assertThat(facade.fetchNoteById(noteId).orElseThrow()).isNotEqualTo(oldNote);
        }
    }
}