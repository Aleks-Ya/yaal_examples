package joplin.search_and_replace_link;

import joplin.Link;
import joplin.LinkParser;
import joplin.SqliteService;
import org.junit.jupiter.api.Test;

import static joplin.SqliteUtils.populateDatabase;
import static org.assertj.core.api.Assertions.assertThat;

class LinkReplacerTest {

    @Test
    void replace() {
        var dbFile = populateDatabase();
        try (var sqliteService = new SqliteService(dbFile)) {
            var note = sqliteService.fetchNoteById("3ce4eb6d45d741718772f16c343b8ddd").orElseThrow();
            var linkParser = new LinkParser();
            var linkReplacer = new LinkReplacer();
            var links = linkParser.parseLinks(note);
            var replacements = links.stream().map(linkReplacer::replace).toList();
            assertThat(replacements).containsExactlyInAnyOrder(
                    new LinkReplacement(new Link(note, "[Joplin link    1   ](:/db65929324925ccbfa789f95cdd293ba)",
                            "Joplin link    1   ", ":/db65929324925ccbfa789f95cdd293ba"),
                            "Joplin link 1", ":/db65929324925ccbfa789f95cdd293ba"),
                    new LinkReplacement(new Link(note, "[WinSCP](:/da4added37344f07a5ff2b9b2e1fdef3)",
                            "WinSCP", ":/da4added37344f07a5ff2b9b2e1fdef3"),
                            "WinSCP", ":/da4added37344f07a5ff2b9b2e1fdef3"),
                    new LinkReplacement(new Link(note, "[Русская ссылка 1](:/ddc9f19456f64ade383ecdd76cf5b90d)",
                            "Русская ссылка 1", ":/ddc9f19456f64ade383ecdd76cf5b90d"),
                            "Русская ссылка 1", ":/ddc9f19456f64ade383ecdd76cf5b90d"),
                    new LinkReplacement(new Link(note, "[](https://winscp.net)", "",
                            "https://winscp.net"), "", "https://winscp.net"),
                    new LinkReplacement(new Link(note, "[](evernote:///view/48821034/s241/5aa9d098-6c4c-4367-8358-77815b98774d/5aa9d098-6c4c-4367-8358-77815b98774d/)", "",
                            "evernote:///view/48821034/s241/5aa9d098-6c4c-4367-8358-77815b98774d/5aa9d098-6c4c-4367-8358-77815b98774d/"),
                            "", "evernote:///view/48821034/s241/5aa9d098-6c4c-4367-8358-77815b98774d/5aa9d098-6c4c-4367-8358-77815b98774d/")
            );
        }
    }
}