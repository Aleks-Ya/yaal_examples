package joplin.apps.format_dates_in_titles;

import joplin.common.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find dates in note titles in any formats and convert them into one format ("yyyy-MM-dd").
 */
public class FormatDatesInTitlesMain {
    private static final Logger log = LoggerFactory.getLogger(FormatDatesInTitlesMain.class);

    public static void main(String[] args) {
        log.info("Started");
        try (var facade = Factory.createFacadeProd(false)) {
            var converter = new Converter(facade);
            converter.convert();
        }
    }
}
