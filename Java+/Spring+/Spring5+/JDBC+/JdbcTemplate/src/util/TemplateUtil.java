package util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TemplateUtil {

    @Autowired
    private JdbcTemplate template;

    public void recreateNamesTable(boolean populateWithTestData) {
        String createSchemaDdl = ResourceUtil.resourceToString("schema.sql");
        template.execute(createSchemaDdl);
        if (populateWithTestData) {
            String insertTestDataScript = ResourceUtil.resourceToString("test-data.sql");
            template.execute(insertTestDataScript);
        }
    }
}