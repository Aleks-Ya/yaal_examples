package util.concurrent.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReentrantReadWriteLockTest {

    /**
     * Can't unlock a lock that your thread didn't locked
     */
    @Test
    public void unlockNotLocked() {
        assertThrows(IllegalMonitorStateException.class, () -> {
            ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
            lock.readLock().unlock();
        });
    }
}