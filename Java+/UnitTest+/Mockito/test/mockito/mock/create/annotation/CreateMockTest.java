package mockito.mock.create.annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CreateMockTest {

    @Mock
    private List<String> mock;

    @Test
    public void test() {
        mock.add("a");
        verify(mock).add("a");
    }
}
