# Add a column into Browser window.
from logging import Logger
from typing import Sequence

from anki import hooks
from anki.collection import Collection
from anki.notes import NoteId

from ._common.disable import enabled
from ._common.log import get_addon_logger

log: Logger = get_addon_logger()


def __on_event(_: Collection, ids: Sequence[NoteId]) -> None:
    log.info(f"NotesWillBeDeleted: ids={ids}")


if enabled():
    hooks.notes_will_be_deleted.append(__on_event)
