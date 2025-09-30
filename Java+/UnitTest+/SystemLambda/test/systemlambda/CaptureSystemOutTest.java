package systemlambda;

import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOut;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOutNormalized;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrNormalized;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static org.assertj.core.api.Assertions.assertThat;

class CaptureSystemOutTest {

    @Test
    void application_writes_text_to_System_err() throws Exception {
        var text = tapSystemErr(() -> System.err.print("some text"));
        assertThat(text).isEqualTo("some text");
    }

    @Test
    void application_writes_mutliple_lines_to_System_err() throws Exception {
        var text = tapSystemErrNormalized(() -> {
            System.err.println("first line");
            System.err.println("second line");
        });
        assertThat(text).isEqualTo("first line\nsecond line\n");
    }

    @Test
    void application_writes_text_to_System_out() throws Exception {
        var text = tapSystemOut(() -> System.out.print("some text"));
        assertThat(text).isEqualTo("some text");
    }

    @Test
    void application_writes_mutliple_lines_to_System_out() throws Exception {
        var text = tapSystemOutNormalized(() -> {
            System.out.println("first line");
            System.out.println("second line");
        });
        assertThat(text).isEqualTo("first line\nsecond line\n");
    }

    @Test
    void application_writes_text_to_System_err_and_out() throws Exception {
        var text = tapSystemErrAndOut(() -> {
            System.err.print("text from err");
            System.out.print("text from out");
        });
        assertThat(text).isEqualTo("text from errtext from out");
    }

    @Test
    void application_writes_mutliple_lines_to_System_err_and_out() throws Exception {
        var text = tapSystemErrAndOutNormalized(() -> {
            System.err.println("text from err");
            System.out.println("text from out");
        });
        assertThat(text).isEqualTo("text from err\ntext from out\n");
    }
}
