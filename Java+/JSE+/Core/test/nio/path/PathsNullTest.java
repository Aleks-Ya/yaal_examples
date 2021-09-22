package nio.path;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PathsNullTest {
    @Test
    void empty() {
        assertThat(Paths.get(""), hasToString(equalTo("")));
    }

    @Test
    void emptyInEnd() {
        assertThat(Paths.get("/abc", "efg", ""), hasToString(equalTo("/abc/efg")));
    }

    @Test
    void nullPath() {
        //noinspection ConstantConditions
        assertThrows(NullPointerException.class, () -> Paths.get(null));
    }

    @Test
    void nullInEnd() {
        //noinspection ConstantConditions
        assertThrows(NullPointerException.class, () -> Paths.get("/abc", null));
    }

}
