package lang.inheritance.linkage.override.exception.try_catch_finally;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * После System.exit() finally{} не вызовется.
 */
class SystemExit {
    @Test
    @Disabled("Невозможно отследить выполнение")
    public void test() {
        try{
            System.exit(0);
        }finally {
            throw new AssertionError();
        }
    }
}
