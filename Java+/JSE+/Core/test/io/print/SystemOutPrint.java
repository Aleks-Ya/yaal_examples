package io.print;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;

class SystemOutPrint {
    @Test
    void print() {
        double d = 0;
        out.print("double: ");
        out.println(d);

        float f = 1;
        out.print("float: ");
        out.println(f);
    }
}