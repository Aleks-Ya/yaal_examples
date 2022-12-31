package joplin.common;

import joplin.common.db.SqliteRepo;
import joplin.common.link.LinkService;
import joplin.common.note.NoteService;
import joplin.common.resource.ResourceService;

public class Factory {
    private static final String PROD_JOPLIN_DIR = "/home/aleks/.config/joplin-desktop";

    public static Facade createFacadeProd(boolean dryRun) {
        return createFacade(PROD_JOPLIN_DIR, PROD_JOPLIN_DIR + "/database.sqlite", dryRun);
    }

    public static Facade createFacade(String prodJoplinDir, String sqliteDbFile, boolean dryRun) {
        var resourceService = new ResourceService(prodJoplinDir);
        var linkService = new LinkService();
        var sqliteService = new SqliteRepo(sqliteDbFile, dryRun);
        var noteService = new NoteService(sqliteService);
        return new Facade(noteService, linkService, resourceService);
    }

}
