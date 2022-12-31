package joplin.apps.evernote_link_to_joplin_link;

import joplin.Utils;
import joplin.common.note.NoteId;
import joplin.common.note.Replacement;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JoplinLinkCreatorTest {

    @Test
    void convertEvernoteLinksToJoplin() {
        try (var facade = Utils.createFacadeFake()) {
            var joplinLinkCreator = new JoplinLinkCreator(facade);
            var note = facade.fetchNoteById(new NoteId("a2d7d7efe84a47bf8ffde18121477efd")).orElseThrow();
            var joplinLinks = joplinLinkCreator.convertEvernoteLinksToJoplin(note);
            assertThat(joplinLinks).singleElement()
                    .extracting(Replacement::newText)
                    .isEqualTo("[Meal\\'s \\\"shopping\\\" list](:/e6900575a9724851bdd8b02d2411967d)");
        }
    }
}