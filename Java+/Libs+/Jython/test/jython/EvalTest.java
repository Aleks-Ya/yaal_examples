package jython;

import org.junit.jupiter.api.Test;
import org.python.core.PyBoolean;
import org.python.core.PyInteger;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import static org.assertj.core.api.Assertions.assertThat;


class EvalTest {
    private static final PythonInterpreter interp = new PythonInterpreter();

    @Test
    void pyString() {
        var result = (PyString) interp.eval("'a'.upper()");
        assertThat(result.getString()).isEqualTo("A");
    }

    @Test
    void pyBoolean() {
        var result = (PyBoolean) interp.eval("1 < 2");
        assertThat(result.getBooleanValue()).isTrue();
    }

    @Test
    void pyInteger() {
        var result = (PyInteger) interp.eval("1+2");
        assertThat(result.getValue()).isEqualTo(3);
    }

}
