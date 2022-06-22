package nio.path;

import org.junit.jupiter.api.Test;

import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PathsTest {

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
        assertThat(getCommonEnd(Paths.get(path1), Paths.get(path2)).toString()).isEqualTo(expCommonEnd);
    }

    @Test
    void get() {
        assertThat(Paths.get("/home/aleks", "Downloads", "torrent").toString())
                .isEqualTo("/home/aleks/Downloads/torrent");
    }

    /**
     * toRealPath()K проверяет наличие файла на диске.
     */
    @Test
    void toRealPath() {
        assertThatThrownBy(() -> Paths.get("/nofile").toRealPath(LinkOption.NOFOLLOW_LINKS))
                .isInstanceOf(NoSuchFileException.class);
    }

    /**
     * normalize() удаляет "." и ".." из пути.
     */
    @Test
    void normalize() {
        var torrent = Paths.get("/home/aleks", "..", "Downloads");
        assertThat(torrent.toString()).isEqualTo("/home/aleks/../Downloads");
        assertThat(torrent.normalize().toString()).isEqualTo("/home/Downloads");
    }

    /**
     * toAbsolutePath() в относительные пути добавляет текущую директорию.
     */
    @Test
    void toAbsolutePath() {
        var relative = Paths.get("Downloads", "torrent").toAbsolutePath().toString();
        assertThat(relative).startsWith("/").endsWith("Downloads/torrent");

        var absolute = Paths.get("/folder/Downloads", "torrent").toAbsolutePath().toString();
        assertThat(absolute).isEqualTo("/folder/Downloads/torrent");
    }

    /**
     * resolve() объединяет 2 пути (к первому прицепляет 2ой).
     */
    @Test
    void resolve() {
        var relative = Paths.get("folder");
        assertThat(relative.resolve("Downloads").toString()).isEqualTo("folder/Downloads");

        var absolute = Paths.get("/folder/Downloads");
        assertThat(absolute.resolve("torrent").toString()).isEqualTo("/folder/Downloads/torrent");
        assertThat(absolute.resolve("/video/film").toString()).isEqualTo("/video/film");
    }

    /**
     * relativize() возвращает путь между двумя путями (какой командой перейти из 1го пути во 2ой).
     */
    @Test
    void relativize() {
        var relative1 = Paths.get("video");
        var relative2 = Paths.get("torrent");
        assertThat(relative1.relativize(relative2).toString()).isEqualTo("../torrent");
        assertThat(relative2.relativize(relative1).toString()).isEqualTo("../video");

        var absolute1 = Paths.get("/home/folder/Downloads");
        var absolute2 = Paths.get("/home/video/film");
        assertThat(absolute1.relativize(absolute2).toString()).isEqualTo("../../video/film");
        assertThat(absolute2.relativize(absolute1).toString()).isEqualTo("../../folder/Downloads");
    }

    @Test
    void commonEnd() {
        assertCommonEnd("/a/b/c/d", "/a/b", "c/d");
        assertCommonEnd("/a/b", "/a/b/c/d", "c/d");
        assertCommonEnd("/a/b/c/d", "/a/b/", "c/d");
        assertCommonEnd("/a/b/c/d/", "/a/b", "c/d");
        assertCommonEnd("/a/b/c/d", "/a/b/c/d", "/a/b/c/d");
        assertCommonEnd("/a/b/c/d", "/x/y/z", "");
    }

}
