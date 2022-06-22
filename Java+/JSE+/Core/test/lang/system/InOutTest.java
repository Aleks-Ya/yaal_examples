package lang.system;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class InOutTest {

    @Test
    void replaceOutputStream() {
        var byteOS = new ByteArrayOutputStream();
        var printStream = new PrintStream(byteOS);
        System.setOut(printStream);

        var output = "abc";
        System.out.print(output);

        assertThat(byteOS).hasToString("abc");
    }
}