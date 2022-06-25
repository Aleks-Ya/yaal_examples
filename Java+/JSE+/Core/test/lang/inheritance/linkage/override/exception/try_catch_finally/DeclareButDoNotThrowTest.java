package lang.inheritance.linkage.override.exception.try_catch_finally;

import org.junit.jupiter.api.Test;

/**
 * Метод декларирует проверяемое исключение в throws, но никогда его не бросает.
 */
class DeclareButDoNotThrowTest {
    @Test
    void main() throws Exception {
      System.out.println(getInt());
    }
    
    private int getInt() throws Exception {
      return 1;
    }
}