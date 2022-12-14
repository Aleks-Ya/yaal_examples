package joplin.evernote_link_to_joplin_link;

import joplin.SqliteService;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static joplin.MarkupLanguage.MD;
import static joplin.SqliteUtils.populateDatabase;
import static org.assertj.core.api.Assertions.assertThat;

class NoteUpdaterTest {

    @Test
    void updateNote() throws SQLException {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var wrapper = new EvernoteLinkParser();
            var joplinLinkCreator = new JoplinLinkCreator();
            var allNotes = sqliteService.fetchAllNotes();
            var note = sqliteService.fetchNoteById("a2d7d7efe84a47bf8ffde18121477efd").orElseThrow();
            var evernoteLinks = wrapper.parseLinks(note);
            var joplinLinks = evernoteLinks.stream()
                    .map(evernoteLink -> joplinLinkCreator.createJoplinLink(evernoteLink, allNotes))
                    .filter(Optional::isPresent)
                    .toList();
            var joplinLink = joplinLinks.get(0).orElseThrow();

            var noteUpdater = new NoteUpdater(sqliteService);
            noteUpdater.updateNote(joplinLink);
            var newNote = sqliteService.fetchNoteById("a2d7d7efe84a47bf8ffde18121477efd").orElseThrow();
            assertThat(newNote).satisfies(note1 -> {
                        assertThat(note1.id()).isEqualTo("a2d7d7efe84a47bf8ffde18121477efd");
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