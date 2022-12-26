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
            new Format(compile("(\\d{2}\\.\\d{2}\\.\\d{4})([ПНВТСРЧБпнвтсрчбMOTUWEHFRSAmotuwehfrsa]{2})?"), ofPattern("dd.MM.yyyy")),
            new Format(compile("(\\d{4}\\.\\d{2}\\.\\d{2})([ПНВТСРЧБпнвтсрчбMOTUWEHFRSAmotuwehfrsa]{2})?"), ofPattern("yyyy.MM.dd")),
            new Format(compile("(\\d{4}-\\d{2}-\\d{2})([ПНВТСРЧБпнвтсрчбMOTUWEHFRSAmotuwehfrsa]{2})?"), ofPattern("yyyy-MM-dd")),
            new Format(compile("(\\d{2}\\.\\d{2}\\.\\d{2})([ПНВТСРЧБпнвтсрчбMOTUWEHFRSAmotuwehfrsa]{2})?"), ofPattern("dd.MM.yy")),
            new Format(compile("(\\d{2}/\\d{2}/\\d{4})([ПНВТСРЧБпнвтсрчбMOTUWEHFRSAmotuwehfrsa]{2})?"), ofPattern("MM/dd/yyyy")),
            new Format(compile("(\\d{2}/\\d{1}/\\d{4})([ПНВТСРЧБпнвтсрчбMOTUWEHFRSAmotuwehfrsa]{2})?"), ofPattern("MM/d/yyyy")),
            new Format(compile("(\\d{1}/\\d{2}/\\d{4})([ПНВТСРЧБпнвтсрчбMOTUWEHFRSAmotuwehfrsa]{2})?"), ofPattern("M/dd/yyyy")),
            new Format(compile("(\\d{1}/\\d{1}/\\d{4})([ПНВТСРЧБпнвтсрчбMOTUWEHFRSAmotuwehfrsa]{2})?"), ofPattern("M/d/yyyy"))
    );

    public List<Date> parseDates(String text, NoteId noteId) {
        if (text == null) {
            return Collections.emptyList();
        }
        var dates = new ArrayList<Date>();
        var words = text.split("\\s");
        for (String word : words) {
            for (var format : FORMATTERS) {
                var pattern = format.pattern();
                var formatter = format.dateTimeFormatter();
                var matcher = pattern.matcher(word);
                if (matcher.matches()) {
                    var matchedText = matcher.group(0);
                    var dateText = matcher.group(1);
                    try {
                        var localDate = LocalDate.from(formatter.parse(dateText));
                        dates.add(new Date(noteId, matchedText, localDate));
                    } catch (DateTimeParseException e) {
                        log.debug("Cannot parse date '{}' with formatter '{}'", matchedText, formatter);
                    }
                }
            }
        }
        return dates;
    }

    private record Format(Pattern pattern, DateTimeFormatter dateTimeFormatter) {
    }
}
