package joplin.apps.evernote_link_to_joplin_link;

import joplin.common.Facade;
import joplin.common.link.Link;
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
        for (Link evernoteLink : note.links()) {
            var searchTitle = searchTitle(evernoteLink.text());
            var newTitle = newTitle(evernoteLink.text());
            var linkTargets = facade.getNoteService().findNotesByTitle(searchTitle);
            if (linkTargets.size() == 0) {
                log.warn("Target note was not found for: originalTitle='{}', newTitle='{}', matchedText='{}'",
                        evernoteLink.text(), newTitle, evernoteLink.element());
            } else if (linkTargets.size() == 1) {
                var matchedTextReplacement = format("[%s](:/%s)", newTitle, linkTargets.get(0).id().id());
                result.add(new Replacement(note.id(), evernoteLink.element(), matchedTextReplacement));
            } else {
                log.warn("Many target notes found: evernoteLink={}, targets={}", evernoteLink, linkTargets);
            }
        }
        return result;
    }
}
