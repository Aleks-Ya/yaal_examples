import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;

public class ClassWithDependenciesTest {
    @Test
    public void testMain() throws Exception {
        ClassWithDependencies myMock = mock(ClassWithDependencies.class);
    }
}
