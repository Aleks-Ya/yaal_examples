import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import static java.lang.System.out;

/**
 * Создание символической ссылки Linux с правами на исполнение.
 */
public class SymbolicLink {
    public static void main(String[] args) throws IOException {
        Path target = Files.createTempFile("target_", ".sh");
        Path link = Paths.get(target.getParent().toString(), "link_to_target");
        Files.deleteIfExists(link);
        Files.createSymbolicLink(link, target);
        out.printf("link   = %s%n", link);
        out.printf("target = %s%n", target);
    }
}