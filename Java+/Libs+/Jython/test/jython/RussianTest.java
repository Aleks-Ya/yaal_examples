package jython;

import org.junit.jupiter.api.Test;
import org.python.core.Py;
import org.python.core.PyBoolean;
import org.python.util.PythonInterpreter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class RussianTest {
    private static final PythonInterpreter interp = new PythonInterpreter();

    @Test
    void exception() {
        var e = assertThrows(IllegalArgumentException.class, () -> interp.eval("'ы'"));
        assertThat(e.getMessage()).isEqualTo("Cannot create PyString with non-byte value");
    }

    @Test
    void booleanResult() {
        interp.exec("result = len('ый') == 2");
        var result = (PyBoolean) interp.get("result");
        assertThat(result.getBooleanValue()).isTrue();
    }

    @Test
    void compareStrings() {
        interp.exec("result = 'ый' == 'ый'");
        var result = (PyBoolean) interp.get("result");
        assertThat(result.getBooleanValue()).isTrue();
    }

    @Test
    void compareStrings2() {
        var code = Py.newStringOrUnicode("result = 'аб' == 'ый'");
        interp.exec(code);
        var result = (PyBoolean) interp.get("result");
        assertThat(result.getBooleanValue()).isFalse();
    }

    @Test
    void evalCompareStrings() {
        var code = Py.newStringOrUnicode("'аб' == 'ый'");
        var result = (PyBoolean) interp.eval(code);
        assertThat(result.getBooleanValue()).isFalse();
    }

    @Test
    void stringResult() {
        interp.exec("result = 'ы'");
        var result = interp.get("result");
        System.out.println(result.asString());
        assertThat(result.asString()).isEqualTo("?");
    }

}
