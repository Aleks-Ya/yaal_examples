import unittest
from threading import Thread

from threading import Event
import time


class StoppableThread(Thread):
    def __init__(self):
        super().__init__()
        self.__stop_event: Event = Event()

    def run(self):
        while not self.__stop_event.is_set():
            print("Thread is running")
            time.sleep(0.5)

    def stop(self):
        self.__stop_event.set()


class TestStoppableThread(unittest.TestCase):

    def test_stop_thread(self):
        thread: StoppableThread = StoppableThread()
        thread.start()
        time.sleep(1)
        self.assertTrue(thread.is_alive())
        thread.stop()
        time.sleep(1)
        self.assertFalse(thread.is_alive())


if __name__ == '__main__':
    unittest.main(buffer=False)
