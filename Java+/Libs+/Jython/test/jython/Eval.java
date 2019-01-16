package jython;

import org.junit.Test;
import org.python.core.PyBoolean;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class Eval {
    private final PythonInterpreter interp = new PythonInterpreter();

    @Test
    public void pyString() {
        PyString result = (PyString) interp.eval("'a'.upper()");
        assertThat(result.getString(), equalTo("A"));
    }

    @Test
    public void pyBoolean() {
        PyBoolean result = (PyBoolean) interp.eval("1 < 2");
        assertTrue(result.getBooleanValue());
    }

}
