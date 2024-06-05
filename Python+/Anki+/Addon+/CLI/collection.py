import tempfile
import unittest
from pathlib import Path

from anki.collection import Collection
from anki.errors import NotFoundError
from anki.notes import Note, NoteId


class CollectionTestCase(unittest.TestCase):
    def setUp(self):
        self.col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])

    def test_create_empty_collection(self):
        _, full_name = tempfile.mkstemp()
        print(f"Collection path: {full_name}")
        col = Collection(full_name)
        print(col.sched.deck_due_tree())
        col.close()
        self.assertEqual(col.name(), Path(full_name).name)

    def test_create_note(self):
        note: Note = self.col.newNote()
        self.col.addNote(note)
        self.assertEqual(note.fields, ['', ''])

    def test_add_field_to_note(self):
        note: Note = self.col.newNote()
        note['Front'] = 'one'
        note['Back'] = 'two'
        self.col.addNote(note)
        self.assertEqual(note.fields, ['one', 'two'])
        self.assertEqual(note.items(), [('Front', 'one'), ('Back', 'two')])

    def test_get_existing_note(self):
        exp_note: Note = self.col.newNote()
        exp_note['Front'] = 'one'
        exp_note['Back'] = 'two'
        self.col.addNote(exp_note)
        note_id: NoteId = exp_note.id
        act_note: Note = self.col.get_note(note_id)
        self.assertListEqual(act_note.items(), exp_note.items())

    def test_get_absent_note(self):
        with self.assertRaises(NotFoundError):
            note_id: NoteId = NoteId(1)
            self.col.get_note(note_id)

    def tearDown(self):
        self.col.close()


if __name__ == '__main__':
    unittest.main()
