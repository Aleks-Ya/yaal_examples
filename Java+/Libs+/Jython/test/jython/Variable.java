package jython;

import org.junit.Test;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;


public class Variable {
    private final PythonInterpreter interp = new PythonInterpreter();

    @Test
    public void setAndGetVar() {
        interp.set("a", new PyInteger(42));
        interp.exec("print a");
        interp.exec("x = 2+2");
        PyInteger x = (PyInteger) interp.get("x");
        assertThat(x.getValue(), equalTo(4));
    }

    @Test
    public void getNotExistVar() {
        PyObject notExists = interp.get("not_exists");
        assertNull(notExists);
    }

    /**
     * PythonInterpreter#cleanup() doesn't remove variables.
     */
    @Test
    public void removeVar() {
        String varName = "a";
        PyString varNamePy = new PyString(varName);

        interp.set(varName, new PyInteger(42));
        assertTrue(interp.getLocals().__contains__(varNamePy));

        interp.set(varName, null);
        assertFalse(interp.getLocals().__contains__(varNamePy));
    }

}
