from typing import Sequence

from anki.collection import OpChanges
from anki.notes import NoteId
from aqt import AnkiQt
from aqt.operations import ResultWithChanges
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo

from .clean_operation import CleanOperation


class CleanAllNotesAction(QAction):
    def __init__(self, mw: AnkiQt, dry_run: bool = True):
        super().__init__('Clean fields in all notes', mw)
        self.__mw: AnkiQt = mw
        self.__dry_run: bool = dry_run
        qconnect(self.triggered, self.on_click)

    def on_success(self, changes: ResultWithChanges) -> None:
        dry_run_str: str = " (DRY RUN)" if self.__dry_run else ""
        showInfo(f"Finished: updated {changes.count} notes{dry_run_str}")

    def on_failure(self, e: Exception) -> None:
        showInfo(f"Failed: {e}")

    def on_click(self) -> None:
        note_ids: Sequence[NoteId] = self.__mw.col.find_notes("")
        op: CleanOperation[OpChanges] = CleanOperation(note_ids, self.__mw, self.__dry_run)
        op.success(self.on_success)
        op.failure(self.on_failure)
        op.run_in_background()
