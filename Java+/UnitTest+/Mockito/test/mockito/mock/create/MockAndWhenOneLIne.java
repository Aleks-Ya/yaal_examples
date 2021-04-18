package mockito.mock.create;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Create mock and define when() in one line.
 */
public class MockAndWhenOneLIne {

    @Test
    public void getMock() {
        long exp = 1L;
        Date mock = when(mock(Date.class).getTime()).thenReturn(exp).getMock();
        assertThat(mock.getTime(), equalTo(exp));
    }
}
