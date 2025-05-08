import unittest
from threading import Thread
from time import sleep


class TestExecuteMethodInThread(unittest.TestCase):

    def test_start_thread(self):
        thread: Thread = Thread(target=TestExecuteMethodInThread._background_method)
        thread.start()
        thread.join()
        self.assertFalse(thread.is_alive())

    @staticmethod
    def _background_method():
        print("Starting background method...")
        sleep(2)
        print("Finished background method")

    def test_exception_in_thread_silent(self):
        thread = Thread(target=TestExecuteMethodInThread._background_method_with_exception_silent)
        thread.start()
        thread.join()
        self.assertFalse(thread.is_alive())

    @staticmethod
    def _background_method_with_exception_silent():
        raise RuntimeError("Serious failure")

    def test_exception_in_thread_log(self):
        thread: Thread = Thread(target=TestExecuteMethodInThread._background_method_with_exception_log)
        thread.start()
        thread.join()
        self.assertFalse(thread.is_alive())

    @staticmethod
    def _background_method_with_exception_log():
        try:
            raise RuntimeError("Serious failure")
        except Exception as e:
            print(f"Exception in thread: {e}")


if __name__ == '__main__':
    unittest.main(buffer=False)
