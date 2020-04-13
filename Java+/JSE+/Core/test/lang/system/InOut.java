package lang.system;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class InOut {

    @Test
    public void replaceOutputStream() {
        ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteOS);
        System.setOut(printStream);

        String output = "abc";
        System.out.print(output);

        assertThat(byteOS.toString(), equalTo("abc"));
    }
}