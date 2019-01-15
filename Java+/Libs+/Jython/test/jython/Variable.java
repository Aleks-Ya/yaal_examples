package jython;

import org.junit.Test;
import org.python.core.PyInteger;
import org.python.util.PythonInterpreter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


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

}
