package nio.charset;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

class CharsetTest {

    @Test
    void availableCharsets() {
        var charsets = Charset.availableCharsets();
        assertThat(charsets).isNotEmpty();
        System.out.println(charsets.values().stream().map(Charset::displayName).collect(joining(", ")));
    }

}
