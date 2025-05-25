# Docs: https://addon-docs.ankiweb.net/the-anki-module.html?highlight=databa#the-database
from typing import Sequence

from anki.collection import Collection
from anki.decks import DeckId
from anki.models import NoteType
from anki.notes import Note


def test_regexp(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    note: Note = col.new_note(basic_note_type)
    note['Front'] = 'one'
    note['Back'] = 'two'
    col.add_note(note, deck_id)
    result: list[Sequence] = col.db.execute("select id from notes where flds regexp 'o.*'")
    assert result == [[note.id]]


def test_regexp_sound(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    note: Note = col.new_note(basic_note_type)
    note['Front'] = 'one'
    note['Back'] = '[sound:googletts-09ff5e30-283b276e-40388dc1-66b6b4d4-398b38f8.mp3]'
    col.add_note(note, deck_id)
    result: list[Sequence] = col.db.execute(
        "select id from notes where flds regexp '(?i)(\[sound:(?P<fname>[^]]+)\])'")
    assert result == [[note.id]]
