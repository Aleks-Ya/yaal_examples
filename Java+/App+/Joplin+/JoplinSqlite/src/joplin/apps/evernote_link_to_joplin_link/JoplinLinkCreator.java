package joplin.apps.evernote_link_to_joplin_link;

import joplin.common.Facade;
import joplin.common.link.LinkType;
import joplin.common.note.Note;
import joplin.common.note.Replacement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

class JoplinLinkCreator {
    private static final Logger log = LoggerFactory.getLogger(JoplinLinkCreator.class);
    private final Facade facade;

    JoplinLinkCreator(Facade facade) {
        this.facade = facade;
    }

    private static String newTitle(String evernoteTitle) {
        return evernoteTitle
                .replaceAll("\\n", " ")
                .replaceAll("\\s{2,}", " ")
                .trim();
    }

    private static String searchTitle(String evernoteTitle) {
        return newTitle(evernoteTitle)
                .replaceAll("\\\\'", "'")
                .replaceAll("\\\\\"", "\"")
                .trim();
    }

    List<Replacement> convertEvernoteLinksToJoplin(Note note) {
        var result = new ArrayList<Replacement>();
        for (var link : note.links()) {
            if (link.type() == LinkType.EVERNOTE) {
                var searchTitle = searchTitle(link.text());
                var newTitle = newTitle(link.text());
                var linkTargets = facade.fetchAllNotes().stream()
                        .filter(note1 -> note1.title().contains(searchTitle))
                        .toList();
                if (linkTargets.size() == 0) {
                    log.warn("Target note was not found for: originalTitle='{}', newTitle='{}', matchedText='{}'",
                            link.text(), newTitle, link.element());
                } else if (linkTargets.size() == 1) {
                    var matchedTextReplacement = format("[%s](:/%s)", newTitle, linkTargets.get(0).noteId().id());
                    result.add(new Replacement(note.noteId(), link.element(), matchedTextReplacement));
                } else {
                    log.warn("Many target notes found: link={}, targets={}", link, linkTargets);
                }
            }
        }
        return result;
    }
}
