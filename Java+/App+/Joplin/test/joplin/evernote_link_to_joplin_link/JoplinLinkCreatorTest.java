package joplin.evernote_link_to_joplin_link;

import joplin.SqliteService;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Optional;

import static joplin.SqliteUtils.populateDatabase;
import static org.assertj.core.api.Assertions.assertThat;

class JoplinLinkCreatorTest {

    @Test
    void createJoplinLink() throws SQLException {
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
            assertThat(joplinLinks).map(Optional::orElseThrow)
                    .extracting(JoplinLink::matchedTextReplacement)
                    .containsExactlyInAnyOrder("[Meal\\'s \\\"shopping\\\" list](:/e6900575a9724851bdd8b02d2411967d)");
        }
    }
}