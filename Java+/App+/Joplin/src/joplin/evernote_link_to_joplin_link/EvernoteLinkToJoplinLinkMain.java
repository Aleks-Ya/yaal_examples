package joplin.evernote_link_to_joplin_link;

import joplin.SqliteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Replaces Evernote internal links with Joplin MarkDown links.
 * E.g. "[Link 1](evernote:///view/48821034/s241/c438c7df-d101-4172-bedd-0c47d67cc636/e4ace13e-bf3d-41c9-a971-e8f3b9a823e3)"
 * -> "[Link 1](:/f62e68ee907f4a9d8a87a1391c8ea3f3)"
 * Searches Joplin notes by link title ("Link 1").
 */
public class EvernoteLinkToJoplinLinkMain {
    private static final Logger log = LoggerFactory.getLogger(EvernoteLinkToJoplinLinkMain.class);

    public static void main(String[] args) {
        log.info("Started");
        var sqliteDbFile = "/home/aleks/.config/joplin-desktop/database.sqlite";
        try (var sqliteService = new SqliteService(sqliteDbFile)) {
            var converter = new Converter(sqliteService);
            converter.convert();
        }
    }
}
