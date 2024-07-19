import os
import tempfile
import unittest

from aqt.qt import QUrl, QGuiApplication, QDesktopServices
from aqt import utils
from anki.collection import Collection


class TestUtils(unittest.TestCase):
    def setUp(self):
        self.col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])

    def test_openFolder(self):
        _ = QGuiApplication([])  # required
        utils.openFolder(os.getcwd())

    def test_openLink(self):
        _ = QGuiApplication([])  # required
        path = os.path.join(os.getcwd(), "utils.py")
        QDesktopServices.openUrl(QUrl(path))  # TODO fix
        # utils.openLink(path) # requires mw object

    def tearDown(self):
        self.col.close()


if __name__ == '__main__':
    unittest.main()
