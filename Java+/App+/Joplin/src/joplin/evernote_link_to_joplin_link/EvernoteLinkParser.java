package joplin.evernote_link_to_joplin_link;

import joplin.NoteEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

class EvernoteLinkParser {
    private static final Pattern PATTERN = Pattern.compile("\\[([-.:?@#\\w\\s\\nЁёА-я\"()'\\\\/]*)]\\(evernote://[-\\w\\s/]*\\)");

    List<EvernoteLink> parseLinks(NoteEntity note) {
        var links = new ArrayList<EvernoteLink>();
        var matcher = PATTERN.matcher(note.body());
        while (matcher.find()) {
            var matchedText = matcher.group(0);
            var title = matcher.group(1);
            links.add(new EvernoteLink(note, matchedText, title));
        }
        return links;
    }
}
