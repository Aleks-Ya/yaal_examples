package joplin.search_and_replace_link;

import joplin.Link;
import joplin.Replacement;

import static java.lang.String.format;

class LinkReplacer {
    Replacement replace(Link link) {
        var newText = link.text()
                .replaceAll("\n", " ")
                .replaceAll("\\s{2,}", " ")
                .trim();
        return new Replacement(link.noteId(), link.element(), format("[%s](%s)", newText, link.url()));
    }
}
