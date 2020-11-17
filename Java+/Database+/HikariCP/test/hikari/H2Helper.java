package hikari;

import java.io.IOException;
import java.nio.file.Files;

import static java.lang.String.format;

public class H2Helper {
    public static String randomH2ServerUrl() {
        try {
            String dbFile = Files.createTempFile(H2Helper.class.getSimpleName(), ".h2").toString();
            return format("jdbc:h2:%s;AUTO_SERVER=TRUE", dbFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
