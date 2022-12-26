package joplin.search_and_replace_link;

import joplin.LinkParser;
import joplin.NoteId;
import joplin.Replacement;
import joplin.SqliteService;
import org.junit.jupiter.api.Test;

import static joplin.SqliteUtils.populateDatabase;
import static org.assertj.core.api.Assertions.assertThat;

class LinkReplacerTest {

    @Test
    void replace() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var note = sqliteService.fetchNoteById(new NoteId("3ce4eb6d45d741718772f16c343b8ddd")).orElseThrow();
            var linkParser = new LinkParser();
            var linkReplacer = new LinkReplacer();
            var links = linkParser.parseLinks(note);
            var replacements = links.stream().map(linkReplacer::replace).toList();
            var id = note.id();
            assertThat(replacements).containsExactlyInAnyOrder(
                    new Replacement(id,
                            "[Joplin link    1   ](:/db65929324925ccbfa789f95cdd293ba)",
                            "[Joplin link 1](:/db65929324925ccbfa789f95cdd293ba)"),
                    new Replacement(id,
                            "[](https://winscp.net)",
                            "[](https://winscp.net)"),
                    new Replacement(id,
                            "[Русская ссылка 1](:/ddc9f19456f64ade383ecdd76cf5b90d)",
                            "[Русская ссылка 1](:/ddc9f19456f64ade383ecdd76cf5b90d)"),
                    new Replacement(id,
                            "[WinSCP](:/da4added37344f07a5ff2b9b2e1fdef3)",
                            "[WinSCP](:/da4added37344f07a5ff2b9b2e1fdef3)"),
                    new Replacement(id,
                            "[](evernote:///view/48821034/s241/5aa9d098-6c4c-4367-8358-77815b98774d/5aa9d098-6c4c-4367-8358-77815b98774d/)",
                            "[](evernote:///view/48821034/s241/5aa9d098-6c4c-4367-8358-77815b98774d/5aa9d098-6c4c-4367-8358-77815b98774d/)")
            );
        }
    }
}