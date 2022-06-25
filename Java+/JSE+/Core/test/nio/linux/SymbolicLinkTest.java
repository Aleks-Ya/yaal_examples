package nio.linux;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.System.out;

/**
 * Создание символической ссылки Linux с правами на исполнение.
 */
class SymbolicLinkTest {

    @Test
    void main() throws IOException {
        var target = Files.createTempFile("target_", ".sh");
        var link = Paths.get(target.getParent().toString(), "link_to_target");
        Files.deleteIfExists(link);
        Files.createSymbolicLink(link, target);
        out.printf("link   = %s%n", link);
        out.printf("target = %s%n", target);
    }
}