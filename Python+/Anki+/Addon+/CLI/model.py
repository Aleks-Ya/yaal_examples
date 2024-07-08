import tempfile
import unittest
from typing import Optional, Sequence

from anki.collection import Collection
from anki.models import NotetypeDict, FieldDict
from anki.notetypes_pb2 import NotetypeNameId


# Model == NoteType
class TestModel(unittest.TestCase):
    basic_model_name: str = 'Basic'

    def setUp(self):
        self.col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])

    def test_get_all_models(self):
        all_models: list[NotetypeDict] = self.col.models.all()
        self.assertEqual(len(all_models), 6)

    def test_get_model_by_name(self):
        basic_model: Optional[NotetypeDict] = self.col.models.by_name(self.basic_model_name)
        self.assertEqual(basic_model['name'], self.basic_model_name)

    def test_get_all_model_names(self):
        names: list[str] = self.col.models.all_names()
        self.assertEqual(names, [self.basic_model_name, 'Basic (and reversed card)', 'Basic (optional reversed card)',
                                 'Basic (type in the answer)', 'Cloze', 'Image Occlusion'])

    def test_get_all_model_names_and_ids(self):
        names_ids: Sequence[NotetypeNameId] = self.col.models.all_names_and_ids()
        self.assertEqual(len(names_ids), 6)

    def test_get_field_names(self):
        basic_model: Optional[NotetypeDict] = self.col.models.by_name(self.basic_model_name)
        field_names: list[str] = self.col.models.field_names(basic_model)
        self.assertEqual(field_names, ['Front', 'Back'])

    def test_add_new_field(self):
        basic_model: Optional[NotetypeDict] = self.col.models.by_name(self.basic_model_name)
        field: FieldDict = self.col.models.new_field('Comment')
        self.col.models.add_field(basic_model, field)
        self.assertEqual(self.col.models.field_names(basic_model), ['Front', 'Back', 'Comment'])

    def tearDown(self):
        self.col.close()


if __name__ == '__main__':
    unittest.main()
