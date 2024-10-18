# Set up and tear down tests
import unittest


class Connection:

    def __init__(self):
        self.connected = False

    def connect(self):
        self.connected = True

    def is_connected(self):
        return self.connected

    def close(self):
        self.connected = False


class TestSetup(unittest.TestCase):

    def setUp(self):
        self.connection = Connection()
        self.connection.connect()

    def tearDown(self):
        self.connection.close()

    def test_is_connected(self):
        self.assertTrue(self.connection.is_connected())


if __name__ == '__main__':
    unittest.main()
