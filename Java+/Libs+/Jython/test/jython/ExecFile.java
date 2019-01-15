package jython;

import org.junit.Test;
import org.python.core.PyBoolean;
import org.python.util.PythonInterpreter;

import java.io.InputStream;

import static org.junit.Assert.assertTrue;


public class ExecFile {

    private final PythonInterpreter interp = new PythonInterpreter();

    @Test
    public void classInFile() {
        InputStream is = getClass().getResourceAsStream("classes.py");
        interp.execfile(is);
        interp.exec("c = Checks()");
        interp.exec("n = c.not_null(123)");
        PyBoolean n = (PyBoolean) interp.get("n");
        assertTrue(n.getBooleanValue());
    }

    @Test
    public void methodInFile() {
        InputStream is = getClass().getResourceAsStream("methods.py");
        interp.execfile(is);
        interp.set("str", "abc");
        interp.set("substr", "b");
        interp.exec("cont = contains(str, substr)");
        PyBoolean cont = (PyBoolean) interp.get("cont");
        assertTrue(cont.getBooleanValue());
    }
}
