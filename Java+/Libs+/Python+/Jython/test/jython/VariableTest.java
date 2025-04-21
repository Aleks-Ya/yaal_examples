package jython;

import org.junit.jupiter.api.Test;
import org.python.core.PyInteger;
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
        var x = (PyInteger) interp.get("x");
        assertThat(x.getValue()).isEqualTo(4);
    }

    @Test
    void getNotExistVar() {
        var notExists = interp.get("not_exists");
        assertThat(notExists).isNull();
    }

    /**
     * PythonInterpreter#cleanup() doesn't remove variables.
     */
    @Test
    void removeVar() {
        var varName = "a";
        var varNamePy = new PyString(varName);

        interp.set(varName, new PyInteger(42));
        assertThat(interp.getLocals().__contains__(varNamePy)).isTrue();

        interp.set(varName, null);
        assertThat(interp.getLocals().__contains__(varNamePy)).isFalse();
    }

}
