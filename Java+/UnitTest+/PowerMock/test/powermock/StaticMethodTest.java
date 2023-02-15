package powermock;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.assertj.core.api.Assertions.assertThat;

//@ExtendWith(PowerMockito.class)
@PrepareForTest(MyStatic.class)
class StaticMethodTest {
    @Test
    @Disabled("Not work")
    void mockStaticMethod() {
        assertThat(MyStatic.getName()).isEqualTo("John");
        var expValue = "Mary";
        PowerMockito.mockStatic(MyStatic.class);
        Mockito.when(MyStatic.getName()).thenReturn(expValue);
        assertThat(MyStatic.getName()).isEqualTo(expValue);
        PowerMockito.verifyStatic(MyStatic.class);
    }
}
