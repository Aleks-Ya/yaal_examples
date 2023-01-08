package joplin.common.link;

import joplin.common.note.Note;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class LinkService {
    private static final Pattern PATTERN =
            Pattern.compile("\\[([-.,:?@#\\w\\s\\nЁёА-я«»\"()'\\\\/<>]*)]\\(([\\w/:&=_?#.-]*)( \".*\")?\\)");

    public Note parseLinks(Note note) {
        var links = new ArrayList<Link>();
        var matcher = PATTERN.matcher(note.body());
        while (matcher.find()) {
            var matchedText = matcher.group(0);
            var title = matcher.group(1);
            var url = matcher.group(2);
            links.add(new Link(matchedText, title, url, null));
        }
        links.sort(Comparator.comparing(Link::element));
        return note.withLinks(links);
    }

    public List<Note> parseLinks(List<Note> notes) {
        return notes.stream().map(this::parseLinks).toList();
    }
}
