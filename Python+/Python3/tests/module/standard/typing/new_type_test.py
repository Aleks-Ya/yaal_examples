from typing import NewType, Union

NoteId = NewType('NoteId', int)
CardId = NewType('CardId', int)
ItemId = Union[CardId, NoteId]


def test_id_equality():
    item_id_1: ItemId = NoteId(1)
    item_id_2: ItemId = CardId(1)
    assert item_id_1 == item_id_2


def test_type():
    item_id: NoteId = NoteId(1)
    assert type(item_id) == int


def test_is_instance():
    item_id: NoteId = NoteId(1)
    assert isinstance(item_id, int)
