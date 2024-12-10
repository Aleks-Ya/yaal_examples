import tempfile
from pathlib import Path
from typing import Sequence

import pytest
from anki.collection import Collection, AddNoteRequest
from anki.collection_pb2 import OpChangesWithCount
from anki.decks import DeckId
from anki.errors import NotFoundError
from anki.models import NoteType
from anki.notes import NoteId, Note
from anki.cards import CardId, Card
from anki.tags import TagManager


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


def test_create_notes(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    note1: Note = col.new_note(basic_note_type)
    note2: Note = col.new_note(basic_note_type)
    assert note1.id == 0
    assert note2.id == 0
    requests: list[AddNoteRequest] = [AddNoteRequest(note1, deck_id), AddNoteRequest(note2, deck_id)]
    col.add_notes(requests)
    assert note1.id != 0
    assert note2.id != 0


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


def test_add_tag_to_note(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    # Create a note without tags
    note: Note = col.new_note(basic_note_type)
    col.add_note(note, deck_id)
    assert note.tags == []
    # Add 1st tag
    important_tag: str = "important"
    note.add_tag(important_tag)
    assert note.tags == [important_tag]
    assert col.get_note(note.id).tags == []
    col.update_note(note)
    assert col.get_note(note.id).tags == [important_tag]
    # Add 2nd tag
    urgent_tag: str = "urgent"
    note.tags.append(urgent_tag)
    assert note.tags == [important_tag, urgent_tag]
    assert col.get_note(note.id).tags == [important_tag]
    col.update_note(note)
    assert col.get_note(note.id).tags == [important_tag, urgent_tag]


def test_remove_tag_from_collection(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    # Create notes with tag
    important_tag: str = "important"
    urgent_tag: str = "urgent"
    note1: Note = col.new_note(basic_note_type)
    note1.tags.append(important_tag)
    note2: Note = col.new_note(basic_note_type)
    note2.tags.append(important_tag)
    note2.tags.append(urgent_tag)
    col.add_notes([AddNoteRequest(note1, deck_id), AddNoteRequest(note2, deck_id)])
    assert col.get_note(note1.id).tags == [important_tag]
    assert col.get_note(note2.id).tags == [important_tag, urgent_tag]
    # Remove tag
    tag_manager: TagManager = col.tags
    assert tag_manager.all() == [important_tag, urgent_tag]
    op_changes_with_count: OpChangesWithCount = tag_manager.remove(important_tag)
    assert op_changes_with_count.count == 2
    assert col.get_note(note1.id).tags == []
    assert col.get_note(note2.id).tags == [urgent_tag]
    assert tag_manager.all() == [urgent_tag]


def test_remove_absent_tag_from_collection(col: Collection, basic_note_type: NoteType, deck_id: DeckId):
    tag_manager: TagManager = col.tags
    assert tag_manager.all() == []
    op_changes_with_count: OpChangesWithCount = tag_manager.remove("absent-tag")
    assert op_changes_with_count.count == 0
    assert tag_manager.all() == []
