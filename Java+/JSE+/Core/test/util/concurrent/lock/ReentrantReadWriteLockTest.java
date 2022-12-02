package util.concurrent.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReentrantReadWriteLockTest {

    /**
     * Can't unlock a lock that your thread didn't locked
     */
    @Test
    void unlockNotLocked() {
        assertThatThrownBy(() -> {
            ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
            lock.readLock().unlock();
        }).isInstanceOf(IllegalMonitorStateException.class);
    }
}