package snappy;

import org.junit.jupiter.api.Test;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.xerial.snappy.SnappyLoader.KEY_SNAPPY_PUREJAVA;

class PureJavaSnappyTest {
    @Test
    void compressUncompress() throws IOException {
        System.setProperty(KEY_SNAPPY_PUREJAVA, "true"); //DOES NOT WORK
        var input = "Hello snappy-java! Snappy-java is a JNI-based wrapper of Snappy, a fast compresser/decompresser.";
        var compressed = Snappy.compress(input.getBytes(StandardCharsets.UTF_8));
        var uncompressed = Snappy.uncompress(compressed);
        var result = new String(uncompressed, StandardCharsets.UTF_8);
        assertThat(result).isEqualTo(input);
    }
}
