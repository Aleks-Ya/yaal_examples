package mockito.mock.create;

import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class ConstructorWithParameters {

    @Test
    public void string() {
        Data mock = mock(Data.class);
        assertNull(mock.getTitle());
    }

    private static class Data {
        private String title;

        private Data(String title) {
            this.title = title;
        }

        String getTitle() {
            return title;
        }

    }

}
