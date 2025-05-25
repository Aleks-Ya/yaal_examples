import tempfile
from pathlib import Path
from typing import Sequence

import pytest
from anki.collection import Collection
from anki.cards import Card
from anki.errors import NotFoundError
from anki.notes import Note, NoteId


def test_create_empty_collection():
    _, full_name = tempfile.mkstemp()
    print(f"Collection path: {full_name}")
    col: Collection = Collection(full_name)
    print(col.sched.deck_due_tree())
    col.close()
    assert col.name() == Path(full_name).name


def test_create_note(col: Collection):
    note: Note = col.newNote()
    col.addNote(note)
    assert note.fields == ['', '']


def test_add_field_to_note(col: Collection):
    note: Note = col.newNote()
    note['Front'] = 'one'
    note['Back'] = 'two'
    col.addNote(note)
    assert note.fields == ['one', 'two']
    assert note.items() == [('Front', 'one'), ('Back', 'two')]


def test_get_existing_note(col: Collection):
    exp_note: Note = col.newNote()
    exp_note['Front'] = 'one'
    exp_note['Back'] = 'two'
    col.addNote(exp_note)
    note_id: NoteId = exp_note.id
    act_note: Note = col.get_note(note_id)
    assert act_note.items() == exp_note.items()


def test_get_absent_note(col: Collection):
    with pytest.raises(NotFoundError):
        note_id: NoteId = NoteId(1)
        col.get_note(note_id)


def test_card_ids_of_note(col: Collection):
    note: Note = col.newNote()
    col.addNote(note)
    card_ids: Sequence[int] = col.card_ids_of_note(note.id)
    assert len(card_ids) == 1


def test_get_card(col: Collection):
    note: Note = col.newNote()
    col.addNote(note)
    card_ids: Sequence[int] = col.card_ids_of_note(note.id)
    assert len(card_ids) == 1
    card_id: int = card_ids[0]
    card: Card = col.get_card(card_id)
    assert card is not None
