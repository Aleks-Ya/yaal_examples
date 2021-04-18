package jython;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.python.core.Py;
import org.python.core.PyBoolean;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class Russian {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();
    private static final PythonInterpreter interp = new PythonInterpreter();

    @Test
    public void exception() {
        expectedEx.expect(IllegalArgumentException.class);
        expectedEx.expectMessage("Cannot create PyString with non-byte value");
        interp.eval("'ы'");
    }

    @Test
    public void booleanResult() {
        interp.exec("result = len('ый') == 2");
        PyBoolean result = (PyBoolean) interp.get("result");
        assertTrue(result.getBooleanValue());
    }

    @Test
    public void compareStrings() {
        interp.exec("result = 'ый' == 'ый'");
        PyBoolean result = (PyBoolean) interp.get("result");
        assertTrue(result.getBooleanValue());
    }

    @Test
    public void compareStrings2() {
        PyString code = Py.newStringOrUnicode("result = 'аб' == 'ый'");
        interp.exec(code);
        PyBoolean result = (PyBoolean) interp.get("result");
        assertFalse(result.getBooleanValue());
    }

    @Test
    public void evalCompareStrings() {
        PyString code = Py.newStringOrUnicode("'аб' == 'ый'");
        PyBoolean result = (PyBoolean) interp.eval(code);
        assertFalse(result.getBooleanValue());
    }

    @Test
    public void stringResult() {
        interp.exec("result = 'ы'");
        PyObject result = interp.get("result");
        System.out.println(result.asString());
        assertThat(result.asString(), equalTo("?"));
    }

}
