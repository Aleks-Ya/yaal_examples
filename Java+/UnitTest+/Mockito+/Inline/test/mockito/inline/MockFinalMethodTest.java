package mockito.inline;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MockFinalMethodTest {

    @Test
    void finalMethod() {
        var mock = mock(Service.class);
        var mockedName = "Mary";
        when(mock.getName()).thenReturn(mockedName);
        assertThat(mock.getName()).isEqualTo(mockedName);
    }

    static class Service {
        public final String getName() {
            return "John";
        }
    }
}
