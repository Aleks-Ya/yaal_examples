package nio.path;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class PathsTest {

    private static Path getCommonEnd(Path path1, Path path2) {
        var p1 = Objects.requireNonNull(path1);
        var p2 = Objects.requireNonNull(path2);
        if (p1.equals(p2)) {
            return p1;
        } else if (p1.startsWith(p2)) {
            return Paths.get(p1.toString().substring(p2.toString().length() + 1));
        } else if (p2.startsWith(p1)) {
            return Paths.get(p2.toString().substring(p1.toString().length() + 1));
        } else {
            return Paths.get("");
        }
    }

    private static void assertCommonEnd(String path1, String path2, String expCommonEnd) {
        assertThat(getCommonEnd(Paths.get(path1), Paths.get(path2)).toString(), equalTo(expCommonEnd));
    }

    @Test
    public void get() {
        assertThat(Paths.get("/home/aleks", "Downloads", "torrent").toString(),
                equalTo("/home/aleks/Downloads/torrent"));
    }

    /**
     * toRealPath()K проверяет наличие файла на диске.
     */
    @Test(expected = NoSuchFileException.class)
    public void toRealPath() throws IOException {
        Paths.get("/nofile").toRealPath(LinkOption.NOFOLLOW_LINKS);
    }

    /**
     * normalize() удаляет "." и ".." из пути.
     */
    @Test
    public void normalize() {
        var torrent = Paths.get("/home/aleks", "..", "Downloads");
        assertThat(torrent.toString(), equalTo("/home/aleks/../Downloads"));
        assertThat(torrent.normalize().toString(), equalTo("/home/Downloads"));
    }

    /**
     * toAbsolutePath() в относительные пути добавляет текущую директорию.
     */
    @Test
    public void toAbsolutePath() {
        var relative = Paths.get("Downloads", "torrent").toAbsolutePath().toString();
        assertThat(relative, allOf(startsWith("/"), endsWith("Downloads/torrent")));

        var absolute = Paths.get("/folder/Downloads", "torrent").toAbsolutePath().toString();
        assertThat(absolute, equalTo("/folder/Downloads/torrent"));
    }

    /**
     * resolve() объединяет 2 пути (к первому прицепляет 2ой).
     */
    @Test
    public void resolve() {
        var relative = Paths.get("folder");
        assertThat(relative.resolve("Downloads").toString(), equalTo("folder/Downloads"));

        var absolute = Paths.get("/folder/Downloads");
        assertThat(absolute.resolve("torrent").toString(), equalTo("/folder/Downloads/torrent"));
        assertThat(absolute.resolve("/video/film").toString(), equalTo("/video/film"));
    }

    /**
     * relativize() возвращает путь между двумя путями (какой командой перейти из 1го пути во 2ой).
     */
    @Test
    public void relativize() {
        var relative1 = Paths.get("video");
        var relative2 = Paths.get("torrent");
        assertThat(relative1.relativize(relative2).toString(), equalTo("../torrent"));
        assertThat(relative2.relativize(relative1).toString(), equalTo("../video"));

        var absolute1 = Paths.get("/home/folder/Downloads");
        var absolute2 = Paths.get("/home/video/film");
        assertThat(absolute1.relativize(absolute2).toString(), equalTo("../../video/film"));
        assertThat(absolute2.relativize(absolute1).toString(), equalTo("../../folder/Downloads"));
    }

    @Test
    public void commonEnd() {
        assertCommonEnd("/a/b/c/d", "/a/b", "c/d");
        assertCommonEnd("/a/b", "/a/b/c/d", "c/d");
        assertCommonEnd("/a/b/c/d", "/a/b/", "c/d");
        assertCommonEnd("/a/b/c/d/", "/a/b", "c/d");
        assertCommonEnd("/a/b/c/d", "/a/b/c/d", "/a/b/c/d");
        assertCommonEnd("/a/b/c/d", "/x/y/z", "");
    }

}
