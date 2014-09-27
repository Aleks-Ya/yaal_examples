import org.junit.Test;

/**
 * Метод декларирует проверяемое исключение в throws, но никогда его не бросает.
 */
public class DeclareButDoNotThrow {
    @Test
    public void main() throws Exception {
      System.out.println(getInt());
    }
    
    private int getInt() throws Exception {
      return 1;
    }
}