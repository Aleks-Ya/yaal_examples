package joplin.apps.search_and_replace_body;

import joplin.common.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find and replace in Joplin note body.
 */
public class SearchAndReplaceBodyMain {
    private static final Logger log = LoggerFactory.getLogger(SearchAndReplaceBodyMain.class);

    public static void main(String[] args) {
        log.info("Started");
        try (var facade = Factory.createFacadeProd(false)) {
            var converter = new Converter(facade);
            converter.convert();
        }
    }
}
