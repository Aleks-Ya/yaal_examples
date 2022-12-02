package lang.operator.precedence;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.assertj.core.api.Assertions.assertThat;

class PrecedenceTest {

    @Test
    public void main() {
        assertThat(2 + 2 * 2).isEqualTo(6);
    }

    @Test
    void orAnd() {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        out.println(a++ == 0 || b++ < 0 && c++ < 0 || d++ < 0);
        out.println("a=" + a);
        out.println("b=" + b);
        out.println("c=" + c);
        out.println("d=" + d);
    }

    @Test
    public void newAndDot() {
        assertThat(new StringBuilder().capacity()).isEqualTo(16);
    }

    @Test
    void castAndDot() {
        Object o = new String();
        assertThat(((String) o).isEmpty()).isTrue();
    }

    @Test
    public void constructor() {
        String s = "a";
        assertThat(new String(s = "b")).isEqualTo("b");
        assertThat(s).isEqualTo("b");
    }
}