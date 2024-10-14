import tempfile
from pathlib import Path
from typing import Sequence

import pytest
from anki.collection import Collection
from anki.decks import DeckId
from anki.errors import NotFoundError
from anki.models import NoteType
from anki.notes import NoteId, Note
from anki.cards import CardId, Card


def test_create_empty_collection():
    _, full_name = tempfile.mkstemp()
    print(f"Collection path: {full_name}")
    col: Collection = Collection(full_name)
    print(col.sched.deck_due_tree())
    col.close()
    assert col.name() == Path(full_name).name


def test_create_note(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    note: Note = col.new_note(basic_note_type)
    col.add_note(note, deck_id)
    assert note.fields == ['', '']


def test_add_field_to_note(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    note: Note = col.new_note(basic_note_type)
    note['Front'] = 'one'
    note['Back'] = 'two'
    col.add_note(note, deck_id)
    assert note.fields == ['one', 'two']
    assert note.items() == [('Front', 'one'), ('Back', 'two')]


def test_get_existing_note(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    exp_note: Note = col.new_note(basic_note_type)
    exp_note['Front'] = 'one'
    exp_note['Back'] = 'two'
    col.add_note(exp_note, deck_id)
    note_id: NoteId = exp_note.id
    act_note: Note = col.get_note(note_id)
    assert act_note.items() == exp_note.items()


def test_get_absent_note(col: Collection):
    with pytest.raises(NotFoundError):
        note_id: NoteId = NoteId(1)
        col.get_note(note_id)


def test_card_ids_of_note(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    note: Note = col.new_note(basic_note_type)
    col.add_note(note, deck_id)
    card_ids: Sequence[CardId] = col.card_ids_of_note(note.id)
    assert len(card_ids) == 1


def test_get_card(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    note: Note = col.new_note(basic_note_type)
    col.add_note(note, deck_id)
    card_ids: Sequence[CardId] = col.card_ids_of_note(note.id)
    assert len(card_ids) == 1
    card_id: CardId = card_ids[0]
    card: Card = col.get_card(card_id)
    assert card is not None
