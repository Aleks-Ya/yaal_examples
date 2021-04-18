package jython;

import org.junit.jupiter.api.Test;
import org.python.util.PythonInterpreter;


public class ImportPackage {
    private final PythonInterpreter interp = new PythonInterpreter();

    @Test
    public void importPackage() {
        interp.exec("import sys");
        interp.exec("print sys");
    }
}
