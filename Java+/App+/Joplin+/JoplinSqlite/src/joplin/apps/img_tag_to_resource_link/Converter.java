package joplin.apps.img_tag_to_resource_link;

import joplin.common.Facade;
import joplin.common.note.Note;
import joplin.common.note.NoteId;
import joplin.common.note.Replacement;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

class Converter {
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    private static final Pattern imgTagPattern = Pattern.compile("<img[^>]*>", CASE_INSENSITIVE);
    private static final Pattern resourceLinkPattern = Pattern.compile("^:/[a-z0-9]{32}");
    private static final Predicate<ParsedImgTag> isResourceImgTag = parsedImgTag -> {
        var isResourceImgTag = parsedImgTag.src() != null && resourceLinkPattern.matcher(parsedImgTag.src()).matches();
        if (!isResourceImgTag) {
            log.info("Skip replacing IMG tag: '{}'", parsedImgTag.rawString());
        }
        return isResourceImgTag;
    };
    public static final Function<ParsedImgTag, Replacement> parsedImgTagToReplacement =
            parsedImgTag -> new Replacement(parsedImgTag.noteId(), parsedImgTag.rawString(),
                    String.format("![%s](%s)", parsedImgTag.alt(), parsedImgTag.src()));
    private final Facade facade;


    Converter(Facade facade) {
        this.facade = facade;
    }

    void convert() {
        var size = facade.fetchAllNotes().stream()
                .flatMap(this::findImgTags)
                .flatMap(this::parseImgTag)
                .filter(isResourceImgTag)
                .map(parsedImgTagToReplacement)
                .peek(facade::updateNoteBody)
                .count();
        log.info("Finished (replaced {} IMG tags with links)", size);
    }

    private Stream<ImgTag> findImgTags(Note note) {
        var matcher = imgTagPattern.matcher(note.body());
        var tags = new ArrayList<ImgTag>();
        while (matcher.find()) {
            tags.add(new ImgTag(note.noteId(), matcher.group()));
        }
        return tags.stream();
    }

    private Stream<ParsedImgTag> parseImgTag(ImgTag imgTag) {
        return Jsoup.parse(imgTag.rawString())
                .select("img")
                .stream()
                .map(element -> new ParsedImgTag(
                        imgTag.noteId(),
                        imgTag.rawString(),
                        element.attr("src"),
                        element.attr("alt"))
                );
    }

    private record ImgTag(NoteId noteId, String rawString) {
    }

    private record ParsedImgTag(NoteId noteId, String rawString, String src, String alt) {
    }
}
