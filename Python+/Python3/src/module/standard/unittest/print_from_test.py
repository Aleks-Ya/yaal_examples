import unittest
from time import sleep


class TestPrintFromTest(unittest.TestCase):

    @staticmethod
    def test_print():
        for i in range(5):
            print(f"Hello from test: {i}")
            sleep(0.1)


if __name__ == '__main__':
    unittest.main(buffer=False)  # DISABLE BUFFERING !!!
