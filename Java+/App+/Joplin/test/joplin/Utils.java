package joplin;

import joplin.common.Facade;
import joplin.common.db.SqliteService;
import joplin.common.link.LinkService;
import joplin.common.note.NoteService;
import joplin.common.resource.ResourceService;
import util.FileUtil;
import util.ResourceUtil;

import java.sql.DriverManager;

import static org.mockito.Mockito.spy;
import static util.ResourceUtil.resourceToFile;

public class Utils {
    public static String populateDatabase() {
        try {
            var dbFile = FileUtil.createAbsentTempFile(".sqlite");
            var initScript = ResourceUtil.resourceToString("joplin/notes_create.sql");
            var statements = initScript.split(";\n");
            try (var connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.getAbsolutePath())) {
                var statement = connection.createStatement();
                for (var st : statements) {
                    if (!st.isBlank()) {
                        statement.addBatch(st);
                    }
                }
                statement.executeBatch();
            }
            return dbFile.getAbsolutePath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getJoplinDir() {
        return resourceToFile(Utils.class, "common/resource/resources/00b0cb57260c885342e9a5cda5efd5eb.txt")
                .getParentFile().getParent();
    }

    public static Facade createFacadeFake() {
        var dbFile = populateDatabase();
        var resourceService = spy(new ResourceService(getJoplinDir()));
        var linkService = spy(new LinkService());
        var sqliteService = spy(new SqliteService(dbFile, false));
        var noteService = spy(new NoteService(sqliteService));
        return new Facade(noteService, linkService, resourceService);
    }
}
