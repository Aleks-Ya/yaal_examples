package core.lang.inheritance.linkage.override.exception.try_catch_finally;

import org.junit.Ignore;
import org.junit.Test;

/**
 * После System.exit() finally{} не вызовется.
 */
public class SystemExit {
    @Test
    @Ignore("Невозможно отследить выполнение")
    public void test() {
        try{
            System.exit(0);
        }finally {
            throw new AssertionError();
        }
    }
}
