package core.io.file.delete;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;

public class DeleteTest {

    @Test
    public void deleteNotExistsFile() throws IOException {
        File f = new File("not exists");
        Assert.assertFalse(f.delete());
    }
}