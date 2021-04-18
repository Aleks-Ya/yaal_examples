package mockito.mock.create;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
