package nio.path;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class PathPartsTest {

    @Test
    void parts() {
        var path = Paths.get("/tmp/data/info.txt");
        assertThat(path.getNameCount()).isEqualTo(3);
        assertThat(path.getFileName()).isEqualTo(Paths.get("info.txt"));
    }

}
