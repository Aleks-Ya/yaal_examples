package mockito.mock.create;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Create mock for Generic classes.
 */
public class Generics {

    @Test
    public void string() {
        List<String> mock = mock(List.class);
        assertThat(mock.size(), equalTo(0));
    }
}
