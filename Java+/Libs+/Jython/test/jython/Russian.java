package jython;

import org.junit.jupiter.api.Test;
import org.python.core.Py;
import org.python.core.PyBoolean;
import org.python.util.PythonInterpreter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Russian {
    private static final PythonInterpreter interp = new PythonInterpreter();

    @Test
    public void exception() {
        var e = assertThrows(IllegalArgumentException.class, () -> interp.eval("'ы'"));
        assertThat(e.getMessage(), equalTo("Cannot create PyString with non-byte value"));
    }

    @Test
    public void booleanResult() {
        interp.exec("result = len('ый') == 2");
        var result = (PyBoolean) interp.get("result");
        assertTrue(result.getBooleanValue());
    }

    @Test
    public void compareStrings() {
        interp.exec("result = 'ый' == 'ый'");
        var result = (PyBoolean) interp.get("result");
        assertTrue(result.getBooleanValue());
    }

    @Test
    public void compareStrings2() {
        var code = Py.newStringOrUnicode("result = 'аб' == 'ый'");
        interp.exec(code);
        var result = (PyBoolean) interp.get("result");
        assertFalse(result.getBooleanValue());
    }

    @Test
    public void evalCompareStrings() {
        var code = Py.newStringOrUnicode("'аб' == 'ый'");
        var result = (PyBoolean) interp.eval(code);
        assertFalse(result.getBooleanValue());
    }

    @Test
    public void stringResult() {
        interp.exec("result = 'ы'");
        var result = interp.get("result");
        System.out.println(result.asString());
        assertThat(result.asString(), equalTo("?"));
    }

}
