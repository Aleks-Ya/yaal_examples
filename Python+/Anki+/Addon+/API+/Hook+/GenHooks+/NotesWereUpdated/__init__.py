from logging import Logger
from typing import Sequence

from anki import hooks
from anki.collection import Collection
from anki.notes import Note

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event(col: Collection, notes: Sequence[Note]) -> None:
    items: list[str] = [f"note_id={note.id}, items={str(note.items())}" for note in notes]
    log.info(f"NotesWereUpdated: col={col}, notes={items}\n")


if enabled():
    # PR with this hook was rejected: https://github.com/ankitects/anki/pull/3310
    hooks.notes_were_updated.append(__on_event)
