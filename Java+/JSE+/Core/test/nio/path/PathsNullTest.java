package nio.path;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PathsNullTest {
    @Test
    void empty() {
        assertThat(Paths.get("")).hasToString("");
    }

    @Test
    void emptyInEnd() {
        assertThat(Paths.get("/abc", "efg", "")).hasToString("/abc/efg");
    }

    @Test
    void nullPath() {
        //noinspection ConstantConditions
        assertThatThrownBy(() -> Paths.get(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void nullInEnd() {
        //noinspection ConstantConditions
        assertThatThrownBy(() -> Paths.get("/abc", null)).isInstanceOf(NullPointerException.class);
    }

}
