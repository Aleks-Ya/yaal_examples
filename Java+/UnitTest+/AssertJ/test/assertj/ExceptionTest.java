package assertj;

import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ExceptionTest {

    @Test
    public void syntax1() {
        assertThatThrownBy(() -> {
            throw new Exception("boom!");
        })
                .isInstanceOf(Exception.class)
                .hasMessageContaining("boom");
    }

    @Test
    public void syntax2() {
        assertThatExceptionOfType(IOException.class).isThrownBy(() -> {
            throw new IOException("boom!");
        })
                .withMessage("%s!", "boom")
                .withMessageContaining("boom")
                .withNoCause();
    }


    /**
     * By BDD.
     */
    @Test
    public void givenWhenThen() {
        // given some preconditions

        // when
        Throwable thrown = catchThrowable(() -> {
            throw new Exception("boom!");
        });

        // then
        assertThat(thrown).isInstanceOf(Exception.class)
                .hasMessageContaining("boom");
    }
}
