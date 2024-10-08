import tempfile
import unittest

from anki.collection import Collection


# Model == Note Type
class TestModel(unittest.TestCase):
    def setUp(self):
        self.col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])

    def test_get_note_type_templates(self):
        note_type = self.col.models.by_name('Basic')
        templates = note_type["tmpls"]
        self.assertEqual(len(templates), 1)

    def test_create_template(self):
        mm = self.col.models
        note_type = mm.by_name('Basic')
        t = mm.new_template("Reverse")
        t["qfmt"] = "{{Back}}"
        t["afmt"] = "{{Front}}"
        mm.add_template(note_type, t)
        templates = note_type["tmpls"]
        self.assertEqual(len(templates), 2)

    def tearDown(self):
        self.col.close()


if __name__ == '__main__':
    unittest.main()
