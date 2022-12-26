package joplin;

import org.junit.jupiter.api.Test;

import static joplin.MarkupLanguage.MD;
import static joplin.SqliteUtils.populateDatabase;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class NoteBodyUpdaterTest {

    @Test
    void updateNote() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var noteId = new NoteId("a2d7d7efe84a47bf8ffde18121477efd");
            var replacement = new Replacement(noteId,
                    "[Meal\\'s \\\"shopping\\\"\n" +
                            "list](evernote:///view/48821034/s241/f6970881-a927-49dc-a73b-a7cc5c9348b3/f6970881-a927-49dc-a73b-a7cc5c9348b3/)",
                    "[Meal\\'s \\\"shopping\\\" list](:/e6900575a9724851bdd8b02d2411967d)");

            var noteUpdater = new NoteBodyUpdater(sqliteService);
            var updated = noteUpdater.updateNote(replacement);
            assertThat(updated).isTrue();
            var newNote = sqliteService.fetchNoteById(noteId).orElseThrow();
            assertThat(newNote).satisfies(note1 -> {
                        assertThat(note1.id().id()).isEqualTo("a2d7d7efe84a47bf8ffde18121477efd");
                        assertThat(note1.title()).isEqualTo("Discourse marker list");
                        assertThat(note1.body())
                                .contains("[Meal\\'s \\\"shopping\\\" list](:/e6900575a9724851bdd8b02d2411967d)")
                                .doesNotContain("[Meal shopping\nlist](evernote:///view/48821034/s241/f6970881-a927-49dc-a73b-a7cc5c9348b3/f6970881-a927-49dc-a73b-a7cc5c9348b3/)");
                        assertThat(note1.markupLanguage()).isEqualTo(MD);
                        assertThat(note1.updatedTime()).isEqualTo(1670158519744L + 1);
                    }
            );
        }
    }

    @Test
    void skipSameNewText() {
        var dbFile = populateDatabase();
        try (var sqliteService = spy(new SqliteService(dbFile))) {
            var noteId = new NoteId("a2d7d7efe84a47bf8ffde18121477efd");
            var oldNote = sqliteService.fetchNoteById(noteId).orElseThrow();

            var noteUpdater = new NoteBodyUpdater(sqliteService);

            var text = "aaa";
            var replacement1 = new Replacement(noteId, text, text);
            var updated1 = noteUpdater.updateNote(replacement1);
            assertThat(updated1).isFalse();
            verify(sqliteService, never()).updateNote(any());
            assertThat(sqliteService.fetchNoteById(noteId).orElseThrow()).isEqualTo(oldNote);

            var replacement2 = new Replacement(noteId, text, "bbb");
            var updated2 = noteUpdater.updateNote(replacement2);
            assertThat(updated2).isTrue();
            verify(sqliteService).updateNote(any());
            assertThat(sqliteService.fetchNoteById(noteId).orElseThrow()).isNotEqualTo(oldNote);
        }
    }
}