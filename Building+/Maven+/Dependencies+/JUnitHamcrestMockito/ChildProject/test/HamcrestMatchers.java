import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class HamcrestMatchers {

    @Test
    public void test() {
        InputStream is = mock(InputStream.class);
        assertNotNull(is);
        assertThat("string", containsString("str"));
    }
}