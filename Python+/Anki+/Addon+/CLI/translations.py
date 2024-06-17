import tempfile
import unittest

from anki.collection import Collection


class TranslationsTestCase(unittest.TestCase):
    def setUp(self):
        self.col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])

    def test_translate(self):
        msg = self.col.tr.addons_browse_addons()
        self.assertEqual("Browse Add-ons", msg)

    def tearDown(self):
        self.col.close()


if __name__ == '__main__':
    unittest.main()
