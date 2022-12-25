package joplin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.regex.Pattern.compile;

public class DateParser {
    private static final Logger log = LoggerFactory.getLogger(DateParser.class);
    private static final List<Format> FORMATTERS = List.of(
            new Format(compile("[\\^\\s]((\\d{2}\\.\\d{2}\\.\\d{4})[ПНВТСРЧБпнвтсрчб]{2})[$\\s]"), ofPattern("dd.MM.yyyy")),
            new Format(compile("[\\^\\s]((\\d{4}\\.\\d{2}\\.\\d{2})[ПНВТСРЧБпнвтсрчб]{2})[$\\s]"), ofPattern("yyyy.MM.dd")),
            new Format(compile("[\\^\\s]((\\d{4}-\\d{2}-\\d{2}))[$\\s]"), ofPattern("yyyy-MM-dd")),
            new Format(compile("[\\^\\s]((\\d{2}\\.\\d{2}\\.\\d{4}))[$\\s]"), ofPattern("dd.MM.yyyy")),
            new Format(compile("[\\^\\s]((\\d{2}\\.\\d{2}\\.\\d{2}))[$\\s]"), ofPattern("dd.MM.yy"))
    );

    public List<Date> parseDates(String text, NoteEntity note) {
        if (text == null) {
            return Collections.emptyList();
        }
        var links = new ArrayList<Date>();
        var cleanText = text;
        for (var format : FORMATTERS) {
            var pattern = format.pattern();
            var formatter = format.dateTimeFormatter();
            var matcher = pattern.matcher(cleanText);
            while (matcher.find()) {
                var matchedText = matcher.group(1);
                var dateText = matcher.group(2);
                try {
                    var localDate = LocalDate.from(formatter.parse(dateText));
                    links.add(new Date(note, matchedText, localDate));
                    cleanText = cleanText.replace(matchedText, "");
                } catch (DateTimeParseException e) {
                    log.debug("Cannot parse date '{}' with formatter '{}'", matchedText, formatter);
                }
            }
        }

        return links;
    }

    private record Format(Pattern pattern, DateTimeFormatter dateTimeFormatter) {
    }
}
