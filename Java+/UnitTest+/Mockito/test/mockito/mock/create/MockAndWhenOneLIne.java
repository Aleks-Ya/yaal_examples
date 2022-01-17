package mockito.mock.create;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Create mock and define when() in one line.
 */
class MockAndWhenOneLIne {

    @Test
    void getMock() {
        var exp = 1L;
        Date mock = when(mock(Date.class).getTime()).thenReturn(exp).getMock();
        assertThat(mock.getTime()).isEqualTo(exp);
    }
}
