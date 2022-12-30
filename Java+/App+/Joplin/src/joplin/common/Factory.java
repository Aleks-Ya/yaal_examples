package joplin.common;

import joplin.common.db.SqliteService;
import joplin.common.link.LinkService;
import joplin.common.note.NoteService;
import joplin.common.resource.ResourceService;

public class Factory {
    private static final String PROD_JOPLIN_DIR = "/home/aleks/.config/joplin-desktop";

    public static Facade createFacadeProd(boolean dryRun) {
        var resourceService = new ResourceService(PROD_JOPLIN_DIR);
        var linkService = new LinkService();
        var sqliteService = new SqliteService(PROD_JOPLIN_DIR + "/database.sqlite", dryRun);
        var noteService = new NoteService(sqliteService);
        return new Facade(noteService, linkService, resourceService);
    }

}
