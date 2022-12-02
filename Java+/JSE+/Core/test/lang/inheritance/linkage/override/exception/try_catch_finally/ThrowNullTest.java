package lang.inheritance.linkage.override.exception.try_catch_finally;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class ThrowNullTest {
    @Test
    void main() throws IOException {
        try {
            throwNull();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            throwNullVar();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void throwNull() {
        throw null;
    }

    private void throwNullVar() throws IOException {
        IOException e = null;
        throw e;
    }
}