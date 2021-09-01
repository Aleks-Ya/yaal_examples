package mockito.mock.create.annotation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateMockTest {

    @Mock
    private List<String> mock;

    @Test
    void test() {
        mock.add("a");
        verify(mock).add("a");
    }
}
