package mockito.mock.create;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

class MockStaticMethodTest {
    @Test
    void staticMethod() {
        assertThat(Service.getName()).isEqualTo("John");
        try (MockedStatic mocked = mockStatic(Service.class)) {
            mocked.when(Service::getName).thenReturn("Mary");
            assertThat(Service.getName()).isEqualTo("Mary");
            mocked.verify(Service::getName);
        }
        assertThat(Service.getName()).isEqualTo("John");
    }

    static class Service {
        public static String getName() {
            return "John";
        }
    }
}
