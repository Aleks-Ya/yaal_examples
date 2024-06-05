from typing import NewType, Union

NoteId = NewType('NoteId', int)
CardId = NewType('CardId', int)
ItemId = Union[CardId, NoteId]

item_id_1: ItemId = NoteId(1)
item_id_2: ItemId = CardId(1)

assert item_id_1 == item_id_2
