# Docs: https://addon-docs.ankiweb.net/the-anki-module.html?highlight=databa#the-database
from typing import Sequence, Any

from anki.collection import Collection
from anki.decks import DeckId
from anki.models import NoteType
from anki.notes import Note


def test_scalar(col: Collection, basic_note_type: NoteType):
    col.addNote(col.new_note(basic_note_type))
    note_count_list: int = col.db.scalar("select count(*) from notes")
    assert note_count_list == 1


def test_list(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    note: Note = col.new_note(basic_note_type)
    col.addNote(note)
    id_list: list[Any] = col.db.list("select id, flds from notes")
    assert id_list == [note.id]


def test_list_on_empty_collection(col: Collection):
    assert col.db.list("select id from notes") == []


def test_all(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    note: Note = col.new_note(basic_note_type)
    note['Front'] = 'one'
    note['Back'] = 'two'
    col.add_note(note, deck_id)
    field_length_list: list[Any] = col.db.all("select id, length(flds) from notes")
    assert field_length_list == [[note.id, 7]]


def test_execute(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    note: Note = col.new_note(basic_note_type)
    note['Front'] = 'one'
    note['Back'] = 'two'
    col.add_note(note, deck_id)
    result: list[Sequence] = col.db.execute("select id from notes where flds like '%n%'")
    assert result == [[note.id]]


def test_create_table(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    col.db.execute("create table my_events(id, event)")
    col.db.execute("insert into my_events(id, event) values(1, 'event1')")
    rows: list[Any] = col.db.all("select id, event from my_events")
    assert rows == [[1, 'event1']]


def test_all_note_types(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    note: Note = col.new_note(basic_note_type)
    note['Front'] = 'one'
    note['Back'] = 'two'
    col.add_note(note, deck_id)
    note_types: list[Any] = col.db.all("select * from notetypes")
    assert len(note_types) == 6
