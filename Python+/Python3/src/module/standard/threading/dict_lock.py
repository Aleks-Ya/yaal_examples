import unittest
from threading import Thread, Lock


class DictLockTestCase(unittest.TestCase):
    my_dict: dict[str, str] = {}
    lock: Lock = Lock()

    def test_start_thread(self):
        thread: Thread = Thread(target=DictLockTestCase._background_method)
        thread.start()
        thread.join()
        self.assertFalse(thread.is_alive())

    def safe_set(self, key, value):
        with self.lock:
            self.my_dict[key] = value

    def safe_get(self, key):
        with self.lock:
            return self.my_dict.get(key)

    def _background_method(self):
        self.safe_set('key', 'value')
        value: str = self.safe_get('key')


if __name__ == '__main__':
    unittest.main(buffer=False)
