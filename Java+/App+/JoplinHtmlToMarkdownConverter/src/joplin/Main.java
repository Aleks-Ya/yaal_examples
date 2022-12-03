package joplin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        log.info("Started");
        var sqliteDbFile = "/home/aleks/.config/joplin-desktop/database.sqlite";
        var notebookId = "444bb0837d7d4f67afc21c0b12916425";
        var converter = new HtmlToMdConverter();
        converter.convert(sqliteDbFile, notebookId);
        log.info("Finished");
    }
}
