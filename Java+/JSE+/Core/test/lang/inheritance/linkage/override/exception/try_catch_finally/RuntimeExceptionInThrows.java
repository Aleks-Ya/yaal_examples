package lang.inheritance.linkage.override.exception.try_catch_finally;

import org.junit.jupiter.api.Test;

/**
 * Объявление НЕпроверяемых исключений в throws.
 */
public class RuntimeExceptionInThrows {
    @Test
    public void main() {
      System.out.println(getInt());
    }
    
    static int getInt() throws RuntimeException {
      return 1;
    }
}