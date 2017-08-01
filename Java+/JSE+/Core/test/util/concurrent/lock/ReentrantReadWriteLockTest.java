package util.concurrent.lock;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockTest {

    /**
     * Can't unlock a lock that your thread didn't locked
     */
    @Test(expected = IllegalMonitorStateException.class)
    public void unlockNotLocked() {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().unlock();
    }
}