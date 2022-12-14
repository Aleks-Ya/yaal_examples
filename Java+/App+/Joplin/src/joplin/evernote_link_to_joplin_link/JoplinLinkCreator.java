package joplin.evernote_link_to_joplin_link;

import joplin.NoteEntity;
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

    Optional<JoplinLink> createJoplinLink(EvernoteLink evernoteLink, List<NoteEntity> allNoteEntities) {
        var searchTitle = searchTitle(evernoteLink.title());
        var newTitle = newTitle(evernoteLink.title());
        var linkTargets = allNoteEntities.stream()
                .filter(note -> searchTitle.equalsIgnoreCase(note.title()))
                .toList();
        if (linkTargets.size() == 0) {
            log.warn("Target note was not found for: id={}, originalTitle='{}', newTitle='{}', matchedText='{}'",
                    evernoteLink.noteEntity().id(), evernoteLink.title(), newTitle, evernoteLink.matchedText());
            return Optional.empty();
        } else if (linkTargets.size() == 1) {
            var matchedTextReplacement = format("[%s](:/%s)", newTitle, linkTargets.get(0).id());
            return Optional.of(new JoplinLink(evernoteLink, matchedTextReplacement));
        } else {
            log.warn("Many target notes found: evernoteLink={}, targets={}", evernoteLink, linkTargets);
            return Optional.empty();
        }
    }
}
