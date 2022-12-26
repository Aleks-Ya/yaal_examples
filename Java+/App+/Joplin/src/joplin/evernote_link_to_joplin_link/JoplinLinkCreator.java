package joplin.evernote_link_to_joplin_link;

import joplin.Link;
import joplin.NoteEntity;
import joplin.Replacement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

class JoplinLinkCreator {
    private static final Logger log = LoggerFactory.getLogger(JoplinLinkCreator.class);

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

    Optional<Replacement> createJoplinLink(Link evernoteLink, List<NoteEntity> allNoteEntities) {
        var searchTitle = searchTitle(evernoteLink.text());
        var newTitle = newTitle(evernoteLink.text());
        var linkTargets = allNoteEntities.stream()
                .filter(note -> searchTitle.equalsIgnoreCase(note.title()))
                .toList();
        if (linkTargets.size() == 0) {
            log.warn("Target note was not found for: id={}, originalTitle='{}', newTitle='{}', matchedText='{}'",
                    evernoteLink.noteId().id(), evernoteLink.text(), newTitle, evernoteLink.element());
            return Optional.empty();
        } else if (linkTargets.size() == 1) {
            var matchedTextReplacement = format("[%s](:/%s)", newTitle, linkTargets.get(0).id().id());
            return Optional.of(new Replacement(evernoteLink.noteId(), evernoteLink.element(), matchedTextReplacement));
        } else {
            log.warn("Many target notes found: evernoteLink={}, targets={}", evernoteLink, linkTargets);
            return Optional.empty();
        }
    }
}
