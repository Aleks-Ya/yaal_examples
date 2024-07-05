from logging import Logger

from anki import hooks
from anki.collection import Collection
from anki.decks import DeckId
from anki.notes import Note

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event(_: Collection, note: Note, deck_id: DeckId) -> None:
    log.info(f"NoteWillBeAdded: deck_id={deck_id}, note_id={note.id}, note_items={note.items()}")


if enabled():
    hooks.note_will_be_added.append(__on_event)
