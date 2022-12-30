package joplin.common.note;

import joplin.Utils;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static joplin.common.note.MarkupLanguage.MD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class NoteBodyReplacerTest {

    @Test
    void updateNote() {
        try (var facade = Utils.createFacadeFake()) {
            var noteId = new NoteId("a2d7d7efe84a47bf8ffde18121477efd");
            var replacement = new Replacement(noteId,
                    "[Meal\\'s \\\"shopping\\\"\n" +
                            "list](evernote:///view/48821034/s241/f6970881-a927-49dc-a73b-a7cc5c9348b3/f6970881-a927-49dc-a73b-a7cc5c9348b3/)",
                    "[Meal\\'s \\\"shopping\\\" list](:/e6900575a9724851bdd8b02d2411967d)");

            var noteUpdater = new NoteBodyReplacer(facade);
            var updated = noteUpdater.updateNoteBody(replacement);
            assertThat(updated).isTrue();
            var newNote = facade.fetchNoteByIdWithResources(noteId).orElseThrow();
            assertThat(newNote).satisfies(note1 -> {
                        assertThat(note1.id().id()).isEqualTo("a2d7d7efe84a47bf8ffde18121477efd");
                        assertThat(note1.title()).isEqualTo("Discourse marker list");
                        assertThat(note1.body())
                                .contains("[Meal\\'s \\\"shopping\\\" list](:/e6900575a9724851bdd8b02d2411967d)")
                                .doesNotContain("[Meal shopping\nlist](evernote:///view/48821034/s241/f6970881-a927-49dc-a73b-a7cc5c9348b3/f6970881-a927-49dc-a73b-a7cc5c9348b3/)");
                        assertThat(note1.markupLanguage()).isEqualTo(MD);
                        assertThat(note1.updatedTime()).isEqualTo(1670158519744L + Duration.ofDays(1).toMillis());
                    }
            );
        }
    }

    @Test
    void skipSameNewText() {
        try (var facade = Utils.createFacadeFake()) {
            var noteService = facade.getNoteService();
            var noteId = new NoteId("a2d7d7efe84a47bf8ffde18121477efd");
            var oldNote = facade.fetchNoteByIdWithResources(noteId).orElseThrow();

            var noteUpdater = new NoteBodyReplacer(facade);

            var text = "aaa";
            var replacement1 = new Replacement(noteId, text, text);
            var updated1 = noteUpdater.updateNoteBody(replacement1);
            assertThat(updated1).isFalse();
            verify(noteService, never()).updateNote(any());
            assertThat(facade.fetchNoteByIdWithResources(noteId).orElseThrow()).isEqualTo(oldNote);

            var replacement2 = new Replacement(noteId, text, "bbb");
            var updated2 = noteUpdater.updateNoteBody(replacement2);
            assertThat(updated2).isTrue();
            verify(noteService).updateNote(any());
            assertThat(facade.fetchNoteByIdWithResources(noteId).orElseThrow()).isNotEqualTo(oldNote);
        }
    }
}