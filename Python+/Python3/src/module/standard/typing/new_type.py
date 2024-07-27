import unittest
from typing import NewType, Union

NoteId = NewType('NoteId', int)
CardId = NewType('CardId', int)
ItemId = Union[CardId, NoteId]


class TestNewType(unittest.TestCase):
    def test_id_equality(self):
        item_id_1: ItemId = NoteId(1)
        item_id_2: ItemId = CardId(1)
        self.assertEqual(item_id_1, item_id_2)

    def test_type(self):
        item_id: NoteId = NoteId(1)
        self.assertEqual(int, type(item_id))

    def test_is_instance(self):
        item_id: NoteId = NoteId(1)
        self.assertIsInstance(item_id, int)


if __name__ == '__main__':
    unittest.main()
