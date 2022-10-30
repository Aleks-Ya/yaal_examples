package mockito.inline;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MockFinalClassTest {

    @Test
    void finalClass() {
        var mock = mock(Service.class);
        var mockedName = "Mary";
        when(mock.getName()).thenReturn(mockedName);
        assertThat(mock.getName()).isEqualTo(mockedName);
    }

    static final class Service {
        public String getName() {
            return "John";
        }
    }
}
