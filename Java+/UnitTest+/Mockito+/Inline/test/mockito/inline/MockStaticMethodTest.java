package mockito.inline;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

class MockStaticMethodTest {
    private static final String ORIGINAL_NAME = "John";
    private static final String MOCKED_NAME = "Mary";

    @Test
    void staticMethod() {
        assertThat(Service.getName()).isEqualTo(ORIGINAL_NAME);
        try (MockedStatic<Service> mocked = mockStatic(Service.class)) {
            mocked.when(Service::getName).thenReturn(MOCKED_NAME);
            assertThat(Service.getName()).isEqualTo(MOCKED_NAME);
            mocked.verify(Service::getName);
        }
        assertThat(Service.getName()).isEqualTo(ORIGINAL_NAME);
    }

    @Test
    void staticMethodWithArgument() {
        assertThat(Service.toUpperCase("abc")).isEqualTo("ABC");
        try (MockedStatic<Service> mocked = mockStatic(Service.class)) {
            mocked.when(() -> Service.toUpperCase("abc")).thenReturn("XYZ");
            assertThat(Service.toUpperCase("abc")).isEqualTo("XYZ");
            mocked.verify(() -> Service.toUpperCase("abc"));
        }
        assertThat(Service.toUpperCase("abc")).isEqualTo("ABC");
    }

    static class Service {
        public static String getName() {
            return ORIGINAL_NAME;
        }

        public static String toUpperCase(String origin) {
            return origin.toUpperCase();
        }
    }
}
