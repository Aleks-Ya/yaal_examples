# Docs: https://addon-docs.ankiweb.net/the-anki-module.html?highlight=databa#the-database
import tempfile
import unittest
from typing import Any, Sequence

from anki.collection import Collection
from anki.decks import DeckId
from anki.models import NotetypeDict
from anki.notes import Note


class TestDatabase(unittest.TestCase):
    def setUp(self):
        self.col: Collection = Collection(tempfile.mkstemp(suffix=".anki2")[1])
        self.basic_note_type: NotetypeDict = self.col.models.by_name('Basic')
        self.deck_id: DeckId = self.col.decks.get_current_id()

    def test_scalar(self):
        self.col.addNote(self.col.new_note(self.basic_note_type))
        note_count_list: int = self.col.db.scalar("select count(*) from notes")
        self.assertEqual(1, note_count_list)

    def test_list(self):
        note: Note = self.col.new_note(self.basic_note_type)
        self.col.addNote(note)
        id_list: list[Any] = self.col.db.list("select id, flds from notes")
        self.assertEqual([note.id], id_list)

    def test_all(self):
        note: Note = self.col.new_note(self.basic_note_type)
        note['Front'] = 'one'
        note['Back'] = 'two'
        self.col.add_note(note, self.deck_id)
        field_length_list: list[Any] = self.col.db.all("select id, length(flds) from notes")
        self.assertEqual([[note.id, 7]], field_length_list)

    def test_execute(self):
        note: Note = self.col.new_note(self.basic_note_type)
        note['Front'] = 'one'
        note['Back'] = 'two'
        self.col.add_note(note, self.deck_id)
        result: list[Sequence] = self.col.db.execute("select id from notes where flds like '%n%'")
        self.assertEqual([[note.id]], result)

    def test_regexp(self):
        note: Note = self.col.new_note(self.basic_note_type)
        note['Front'] = 'one'
        note['Back'] = 'two'
        self.col.add_note(note, self.deck_id)
        result: list[Sequence] = self.col.db.execute("select id from notes where flds regexp 'o.*'")
        self.assertEqual([[note.id]], result)

    def test_regexp_sound(self):
        note: Note = self.col.new_note(self.basic_note_type)
        note['Front'] = 'one'
        note['Back'] = '[sound:googletts-09ff5e30-283b276e-40388dc1-66b6b4d4-398b38f8.mp3]'
        self.col.add_note(note, self.deck_id)
        result: list[Sequence] = self.col.db.execute("select id from notes where flds regexp '(?i)(\[sound:(?P<fname>[^]]+)\])'")
        self.assertEqual([[note.id]], result)

    def tearDown(self):
        self.col.close()


if __name__ == '__main__':
    unittest.main()
