package rule;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

import static org.junit.Assert.assertEquals;

/**
 * Использование правила (Rule) @TemporaryFolder.
 */
public class TemporaryFolderRule {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void catchExc() throws IOException {

        File file = folder.newFile();
        FileWriter writer = new FileWriter(file);

        String expLine = "TemporaryFolder";
        writer.write(expLine);
        writer.flush();
        writer.close();

        String filePath = file.getAbsolutePath();
        System.out.println(filePath);

        LineNumberReader reader = new LineNumberReader(new FileReader(filePath));
        String actLine = reader.readLine();
        reader.close();

        assertEquals(expLine, actLine);
    }
}
