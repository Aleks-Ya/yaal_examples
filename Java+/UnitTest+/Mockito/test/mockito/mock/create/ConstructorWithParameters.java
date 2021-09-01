package mockito.mock.create;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

class ConstructorWithParameters {

    @Test
    void string() {
        var mock = mock(Data.class);
        assertNull(mock.getTitle());
    }

    private static class Data {
        private final String title;

        private Data(String title) {
            this.title = title;
        }

        String getTitle() {
            return title;
        }

    }

}
