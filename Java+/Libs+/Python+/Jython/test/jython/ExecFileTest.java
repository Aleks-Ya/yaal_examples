package jython;

import org.junit.jupiter.api.Test;
import org.python.core.PyBoolean;
import org.python.util.PythonInterpreter;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;


class ExecFileTest {

    private final PythonInterpreter interp = new PythonInterpreter();

    @Test
    void classInFile() {
        InputStream is = getClass().getResourceAsStream("classes.py");
        interp.execfile(is);
        interp.exec("c = Checks()");
        interp.exec("n = c.not_null(123)");
        PyBoolean n = (PyBoolean) interp.get("n");
        assertThat(n.getBooleanValue()).isTrue();
    }

    @Test
    void methodInFile() {
        InputStream is = getClass().getResourceAsStream("methods.py");
        interp.execfile(is);
        interp.set("str", "abc");
        interp.set("substr", "b");
        interp.exec("cont = contains(str, substr)");
        PyBoolean cont = (PyBoolean) interp.get("cont");
        assertThat(cont.getBooleanValue()).isTrue();
    }
}
