from logging import Logger

from anki.notes import Note
from aqt import gui_hooks

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event(note: Note) -> None:
    log.info(f"AddCardsDidAddNote: note_id={note.id}, note_items={note.items()}")


if enabled():
    gui_hooks.add_cards_did_add_note.append(__on_event)
