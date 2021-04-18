package auth;

import de.jkeylockmanager.manager.KeyLockManager;
import de.jkeylockmanager.manager.KeyLockManagers;
import de.jkeylockmanager.manager.LockCallback;
import de.jkeylockmanager.manager.ReturnValueLockCallback;
import org.junit.jupiter.api.Test;

public class HelloWorld {

    @Test
    public void test() {
        KeyLockManager lockManager = KeyLockManagers.newLock();
        LockCallback callback = () -> System.out.println("aaa");
        String key = "abc";
        lockManager.executeLocked(key, callback);
        ReturnValueLockCallback<Integer> returnValueCallback = () -> 1;
        Integer value = lockManager.executeLocked(key, returnValueCallback);
        System.out.println(value);
    }
}