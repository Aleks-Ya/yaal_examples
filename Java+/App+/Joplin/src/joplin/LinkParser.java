package joplin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LinkParser {
    private static final Pattern PATTERN = Pattern.compile("\\[([-.:?@#\\w\\s\\nЁёА-я\"()'\\\\/]*)]\\(([\\w/:.-]*)\\)");

    public List<Link> parseLinks(NoteEntity note) {
        var links = new ArrayList<Link>();
        var matcher = PATTERN.matcher(note.body());
        while (matcher.find()) {
            var matchedText = matcher.group(0);
            var title = matcher.group(1);
            var url = matcher.group(2);
            links.add(new Link(note, matchedText, title, url));
        }
        return links;
    }
}
