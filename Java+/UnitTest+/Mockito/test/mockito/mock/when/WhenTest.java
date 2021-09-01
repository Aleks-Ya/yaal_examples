package mockito.mock.when;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WhenTest {

    @Test
    void returnValue() {
        var mock = mock(Date.class);
        var value = 100L;
        when(mock.getTime()).thenReturn(value);
        assertThat(mock.getTime(), equalTo(value));
    }
}
