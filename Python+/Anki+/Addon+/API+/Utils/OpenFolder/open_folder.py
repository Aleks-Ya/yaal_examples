import os
import tempfile
import unittest

from aqt.qt import QGuiApplication
from aqt import utils
from anki.collection import Collection


class TestUtils(unittest.TestCase):
    def setUp(self):
        self.col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])

    def test_openFolder(self):
        _ = QGuiApplication([])  # required
        utils.openFolder(os.getcwd())

    def tearDown(self):
        self.col.close()


if __name__ == '__main__':
    unittest.main()
