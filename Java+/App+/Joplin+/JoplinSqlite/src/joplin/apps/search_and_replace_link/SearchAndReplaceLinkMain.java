package joplin.apps.search_and_replace_link;

import joplin.common.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find and replace in Joplin note body.
 */
public class SearchAndReplaceLinkMain {
    private static final Logger log = LoggerFactory.getLogger(SearchAndReplaceLinkMain.class);

    public static void main(String[] args) {
        log.info("Started");
        try (var facade = Factory.createFacadeProd(false)) {
            var converter = new Converter(facade);
            converter.convert();
        }
    }
}
