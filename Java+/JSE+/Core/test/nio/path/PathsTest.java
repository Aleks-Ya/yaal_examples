package nio.path;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class PathsTest {
    @Test
    public void get() {
        assertThat(Paths.get("/home/aleks", "Downloads", "torrent").toString(), equalTo("/home/aleks/Downloads/torrent"));
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
    public void normalize() throws IOException {
        Path torrent = Paths.get("/home/aleks", "..", "Downloads");
        assertThat(torrent.toString(), equalTo("/home/aleks/../Downloads"));
        assertThat(torrent.normalize().toString(), equalTo("/home/Downloads"));
    }

    /**
     * toAbsolutePath() в относительные пути добавляет текущую директорию.
     */
    @Test
    public void toAbsolutePath() {
        String relative = Paths.get("Downloads", "torrent").toAbsolutePath().toString();
        assertThat(relative, allOf(startsWith("/"), endsWith("Downloads/torrent")));

        String absolute = Paths.get("/folder/Downloads", "torrent").toAbsolutePath().toString();
        assertThat(absolute, equalTo("/folder/Downloads/torrent"));
    }

    /**
     * resolve() объединяет 2 пути (к первому прицепляет 2ой).
     */
    @Test
    public void resolve() {
        Path relative = Paths.get("folder");
        assertThat(relative.resolve("Downloads").toString(), equalTo("folder/Downloads"));

        Path absolute = Paths.get("/folder/Downloads");
        assertThat(absolute.resolve("torrent").toString(), equalTo("/folder/Downloads/torrent"));
        assertThat(absolute.resolve("/video/film").toString(), equalTo("/video/film"));
    }

    /**
     * relativize() возвращает путь между двумя путями (какой командой перейти из 1го пути во 2ой).
     */
    @Test
    public void relativize() {
        Path relative1 = Paths.get("video");
        Path relative2 = Paths.get("torrent");
        assertThat(relative1.relativize(relative2).toString(), equalTo("../torrent"));
        assertThat(relative2.relativize(relative1).toString(), equalTo("../video"));

        Path absolute1 = Paths.get("/home/folder/Downloads");
        Path absolute2 = Paths.get("/home/video/film");
        assertThat(absolute1.relativize(absolute2).toString(), equalTo("../../video/film"));
        assertThat(absolute2.relativize(absolute1).toString(), equalTo("../../folder/Downloads"));
    }
}
