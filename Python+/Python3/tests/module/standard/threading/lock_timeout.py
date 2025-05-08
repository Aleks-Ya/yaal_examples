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


class TestLockTimeout(unittest.TestCase):

    def test_lock_timeout(self):
        lock: Lock = Lock()
        thread: StoppableThread = StoppableThread(lock)
        thread.start()
        self.assertFalse(lock.acquire(timeout=1))
        thread.stop()
        time.sleep(1)
        self.assertTrue(lock.acquire())


if __name__ == '__main__':
    unittest.main(buffer=False)
