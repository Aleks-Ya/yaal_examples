package joplin.apps.search_and_replace_link;

import joplin.common.note.Note;
import joplin.common.note.Replacement;

import java.util.List;

import static java.lang.String.format;

class LinkReplacer {
    List<Replacement> replace(Note note) {
        return note.links().stream().map(link -> new Replacement(note.id(), link.element(), format("[%s](%s)", link.text()
                .replaceAll("\n", " ")
                .replaceAll("\\s{2,}", " ")
                .trim(), link.url()))).toList();
    }
}
