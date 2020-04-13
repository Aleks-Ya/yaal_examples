package nio.path;

import org.junit.Test;

import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasToString;

public class PathsNullTest {
    @Test
    public void empty() {
        assertThat(Paths.get(""), hasToString(equalTo("")));
    }

    @Test
    public void emptyInEnd() {
        assertThat(Paths.get("/abc", "efg", ""), hasToString(equalTo("\\abc\\efg")));
    }

    @Test(expected = NullPointerException.class)
    public void nullPath() {
        //noinspection ConstantConditions
        Paths.get(null);
    }

    @Test(expected = NullPointerException.class)
    public void nullInEnd() {
        //noinspection ConstantConditions
        Paths.get("/abc", null);
    }

}
