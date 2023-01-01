package joplin.apps.search_and_replace_link;

import joplin.Utils;
import joplin.common.note.Replacement;
import org.junit.jupiter.api.Test;

import static joplin.Notes.NOTE_1;
import static joplin.Notes.NOTE_3;
import static org.assertj.core.api.Assertions.assertThat;

class LinkReplacerTest {

    @Test
    void replace() {
        try (var facade = Utils.createFacadeFake()) {
            var linkReplacer = new LinkReplacer();

            var note1 = facade.fetchNoteById(NOTE_1.noteId()).orElseThrow();
            var replacements1 = linkReplacer.replace(note1);
            var noteId1 = note1.noteId();
            assertThat(replacements1).containsExactlyInAnyOrder(
                    new Replacement(noteId1,
                            "[Joplin link    1   ](:/db65929324925ccbfa789f95cdd293ba)",
                            "[Joplin link 1](:/db65929324925ccbfa789f95cdd293ba)"),
                    new Replacement(noteId1,
                            "[](https://winscp.net)",
                            "[](https://winscp.net)"),
                    new Replacement(noteId1,
                            "[Русская ссылка 1](:/ddc9f19456f64ade383ecdd76cf5b90d)",
                            "[Русская ссылка 1](:/ddc9f19456f64ade383ecdd76cf5b90d)"),
                    new Replacement(noteId1,
                            "[WinSCP](:/da4added37344f07a5ff2b9b2e1fdef3)",
                            "[WinSCP](:/da4added37344f07a5ff2b9b2e1fdef3)"),
                    new Replacement(noteId1,
                            "[](evernote:///view/48821034/s241/5aa9d098-6c4c-4367-8358-77815b98774d/5aa9d098-6c4c-4367-8358-77815b98774d/)",
                            "[](evernote:///view/48821034/s241/5aa9d098-6c4c-4367-8358-77815b98774d/5aa9d098-6c4c-4367-8358-77815b98774d/)")
            );

            var note2 = facade.fetchNoteById(NOTE_3.noteId()).orElseThrow();
            var replacements2 = linkReplacer.replace(note2);
            var noteId2 = note2.noteId();
            assertThat(replacements2).containsExactlyInAnyOrder(
                    new Replacement(noteId2,
                            "[Русское название\nстатьи 2, запятая](evernote:///view/48821034/s241/87b5042e-7d6e-40bc-b434-2f4daf68722a/87b5042e-7d6e-40bc-b434-2f4daf68722a/)",
                            "[Русское название статьи 2, запятая](evernote:///view/48821034/s241/87b5042e-7d6e-40bc-b434-2f4daf68722a/87b5042e-7d6e-40bc-b434-2f4daf68722a/)"),
                    new Replacement(noteId2,
                            "[Android\napp](https://play.google.com/store/apps/details?id=net.cubux.android_v2&hl=ru&gl=US)",
                            "[Android app](https://play.google.com/store/apps/details?id=net.cubux.android_v2&hl=ru&gl=US)"),
                    new Replacement(noteId2,
                            "[web\nversion](https://app.pomodoneapp.com \"https://app.pomodoneapp.com\")",
                            "[web version](https://app.pomodoneapp.com)"),
                    new Replacement(noteId2,
                            "[ticket is in<br>progress](https://github.com/laurent22/joplin/issues/375)",
                            "[ticket is in progress](https://github.com/laurent22/joplin/issues/375)")
            );
        }
    }
}