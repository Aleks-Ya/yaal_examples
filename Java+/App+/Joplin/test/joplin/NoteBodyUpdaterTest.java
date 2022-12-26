package joplin;

import joplin.NoteBodyUpdater;
import joplin.NoteId;
import joplin.Replacement;
import joplin.SqliteService;
import org.junit.jupiter.api.Test;

import static joplin.MarkupLanguage.MD;
import static joplin.SqliteUtils.populateDatabase;
import static org.assertj.core.api.Assertions.assertThat;

class NoteBodyUpdaterTest {

    @Test
    void updateNote() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var note = sqliteService.fetchNoteById(new NoteId("a2d7d7efe84a47bf8ffde18121477efd")).orElseThrow();
            var replacement = new Replacement(note.id(),
                    "[Meal\\'s \\\"shopping\\\"\n" +
                            "list](evernote:///view/48821034/s241/f6970881-a927-49dc-a73b-a7cc5c9348b3/f6970881-a927-49dc-a73b-a7cc5c9348b3/)",
                    "[Meal\\'s \\\"shopping\\\" list](:/e6900575a9724851bdd8b02d2411967d)");

            var noteUpdater = new NoteBodyUpdater(sqliteService);
            noteUpdater.updateNote(replacement);
            var newNote = sqliteService.fetchNoteById(new NoteId("a2d7d7efe84a47bf8ffde18121477efd")).orElseThrow();
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
}