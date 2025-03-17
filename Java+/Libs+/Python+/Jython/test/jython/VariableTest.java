package jython;

import org.junit.jupiter.api.Test;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import static org.assertj.core.api.Assertions.assertThat;

class VariableTest {
    private final PythonInterpreter interp = new PythonInterpreter();

    @Test
    void setAndGetVar() {
        interp.set("a", new PyInteger(42));
        interp.exec("print a");
        interp.exec("x = 2+2");
        PyInteger x = (PyInteger) interp.get("x");
        assertThat(x.getValue()).isEqualTo(4);
    }

    @Test
    void getNotExistVar() {
        PyObject notExists = interp.get("not_exists");
        assertThat(notExists).isNull();
    }

    /**
     * PythonInterpreter#cleanup() doesn't remove variables.
     */
    @Test
    void removeVar() {
        String varName = "a";
        PyString varNamePy = new PyString(varName);

        interp.set(varName, new PyInteger(42));
        assertThat(interp.getLocals().__contains__(varNamePy)).isTrue();

        interp.set(varName, null);
        assertThat(interp.getLocals().__contains__(varNamePy)).isFalse();
    }

}
