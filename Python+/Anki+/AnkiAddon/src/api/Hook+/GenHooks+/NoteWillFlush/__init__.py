from logging import Logger

from anki import hooks
from anki.notes import Note

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event(note: Note) -> None:
    log.info(f"NoteWillFlush: note_id={note.id}, note_items={note.items()}")


if enabled():
    hooks.note_will_flush.append(__on_event)
