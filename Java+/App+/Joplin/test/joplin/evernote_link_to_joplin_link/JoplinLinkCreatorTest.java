package joplin.evernote_link_to_joplin_link;

import joplin.common.db.SqliteService;
import joplin.common.link.LinkService;
import joplin.common.note.NoteId;
import joplin.common.note.Replacement;
import org.junit.jupiter.api.Test;

import static joplin.Utils.populateDatabase;
import static org.assertj.core.api.Assertions.assertThat;

class JoplinLinkCreatorTest {

    @Test
    void convertEvernoteLinksToJoplin() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var linkService = new LinkService();
            var joplinLinkCreator = new JoplinLinkCreator(sqliteService);
            var note = sqliteService.fetchNoteById(new NoteId("a2d7d7efe84a47bf8ffde18121477efd")).orElseThrow();
            var linkNote = linkService.parseLinks(note);
            var joplinLinks = joplinLinkCreator.convertEvernoteLinksToJoplin(linkNote);
            assertThat(joplinLinks).singleElement()
                    .extracting(Replacement::newText)
                    .isEqualTo("[Meal\\'s \\\"shopping\\\" list](:/e6900575a9724851bdd8b02d2411967d)");
        }
    }
}