import tempfile
import unittest

from anki.collection import Collection


# Model == Note Type
class ModelTestCase(unittest.TestCase):
    def setUp(self):
        self.col = Collection(tempfile.mkstemp()[1])

    def test_get_all_models(self):
        all_models = self.col.models.all()
        self.assertEqual(len(all_models), 6)

    def test_get_model_by_name(self):
        basic_model = self.col.models.by_name('Basic')
        self.assertEqual(basic_model['name'], 'Basic')

    def test_get_all_model_names(self):
        names = self.col.models.all_names()
        self.assertEqual(names, ['Basic', 'Basic (and reversed card)', 'Basic (optional reversed card)',
                                 'Basic (type in the answer)', 'Cloze', 'Image Occlusion'])

    def test_get_all_model_names_and_ids(self):
        names_ids = self.col.models.all_names_and_ids()
        self.assertEqual(len(names_ids), 6)

    def test_get_field_names(self):
        basic_model = self.col.models.by_name('Basic')
        field_names = self.col.models.field_names(basic_model)
        self.assertEqual(field_names, ['Front', 'Back'])

    def test_add_new_field(self):
        basic_model = self.col.models.by_name('Basic')
        field = self.col.models.new_field('Comment')
        self.col.models.add_field(basic_model, field)
        self.assertEqual(self.col.models.field_names(basic_model), ['Front', 'Back', 'Comment'])

    def tearDown(self):
        self.col.close()


if __name__ == '__main__':
    unittest.main()
