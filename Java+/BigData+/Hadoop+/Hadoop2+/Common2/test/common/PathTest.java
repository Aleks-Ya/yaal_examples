package common;

import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PathTest {
    @Test
    void path() {
        var p1 = new Path("dir", "file.txt");
        var p2 = new Path("dir/file.txt");
        assertThat(p1).isEqualTo(p2);
    }
}
