import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class IOUtilsTest {

    @Test
    public void inputStreamToString() throws IOException {
        String exp = "abc";
        InputStream is = new ByteArrayInputStream(exp.getBytes());
        String act = IOUtils.toString(is, Charset.defaultCharset());
        assertThat(act, equalTo(exp));
    }
}