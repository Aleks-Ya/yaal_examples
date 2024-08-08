import time
import unittest
from threading import Lock, Thread, Event


class StoppableThread(Thread):
    def __init__(self, lock: Lock):
        super().__init__()
        self.__lock: Lock = lock
        self._stop_event: Event = Event()

    def run(self):
        with self.__lock:
            while not self._stop_event.is_set():
                time.sleep(0.5)

    def stop(self):
        self._stop_event.set()


class TestIsLockReleased(unittest.TestCase):

    def test_acquire_released_lock(self):
        lock: Lock = Lock()
        own: bool = lock.acquire(blocking=False)
        self.assertTrue(own)

    def test_acquire_busy_lock(self):
        lock: Lock = Lock()
        thread: StoppableThread = StoppableThread(lock)
        thread.start()
        self.assertFalse(lock.acquire(blocking=False))
        thread.stop()
        time.sleep(1)
        self.assertTrue(lock.acquire())


if __name__ == '__main__':
    unittest.main(buffer=False)
