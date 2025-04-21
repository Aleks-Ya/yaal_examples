package jython;

import org.junit.jupiter.api.Test;
import org.python.core.PyBoolean;
import org.python.util.PythonInterpreter;

import static org.assertj.core.api.Assertions.assertThat;


class ExecFileTest {

    private final PythonInterpreter interp = new PythonInterpreter();

    @Test
    void classInFile() {
        var is = getClass().getResourceAsStream("classes.py");
        interp.execfile(is);
        interp.exec("c = Checks()");
        interp.exec("n = c.not_null(123)");
        var n = (PyBoolean) interp.get("n");
        assertThat(n.getBooleanValue()).isTrue();
    }

    @Test
    void methodInFile() {
        var is = getClass().getResourceAsStream("methods.py");
        interp.execfile(is);
        interp.set("str", "abc");
        interp.set("substr", "b");
        interp.exec("cont = contains(str, substr)");
        var cont = (PyBoolean) interp.get("cont");
        assertThat(cont.getBooleanValue()).isTrue();
    }
}
