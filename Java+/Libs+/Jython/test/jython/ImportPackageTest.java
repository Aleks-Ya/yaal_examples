package jython;

import org.junit.jupiter.api.Test;
import org.python.util.PythonInterpreter;


class ImportPackageTest {
    private final PythonInterpreter interp = new PythonInterpreter();

    @Test
    void importPackage() {
        interp.exec("import sys");
        interp.exec("print sys");
    }
}
