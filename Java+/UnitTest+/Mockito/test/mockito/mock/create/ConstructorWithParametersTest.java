package mockito.mock.create;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ConstructorWithParametersTest {

    @Test
    void string() {
        var mock = mock(Data.class);
        assertThat(mock.getTitle()).isNull();
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
