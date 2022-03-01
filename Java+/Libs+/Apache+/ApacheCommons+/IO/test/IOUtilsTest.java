import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

class IOUtilsTest {

    @Test
    void inputStreamToString() throws IOException {
        var exp = "abc";
        InputStream is = new ByteArrayInputStream(exp.getBytes());
        var act = IOUtils.toString(is, Charset.defaultCharset());
        assertThat(act).isEqualTo(exp);
    }
}