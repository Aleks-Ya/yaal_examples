from logging import Logger, getLogger
from typing import Sequence

import aqt
from anki.collection import OpChanges
from anki.notes import NoteId
from aqt.operations import ResultWithChanges
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo

from .clean_operation import CleanOperation

log: Logger = getLogger(__name__)


class CleanSelectedNotesAction(QAction):
    def __init__(self, browser: 'aqt.browser.Browser', dry_run: bool = True):
        super().__init__('Clean fields in selected notes', browser)
        from aqt.browser import Browser
        self.__browser: Browser = browser
        self.__dry_run: bool = dry_run
        qconnect(self.triggered, self.on_click)

    def on_success(self, changes: ResultWithChanges) -> None:
        dry_run_str: str = " (DRY RUN)" if self.__dry_run else ""
        if self.__browser and self.__browser.editor:
            self.__browser.editor.loadNoteKeepingFocus()
        showInfo(f"Finished: updated {changes.count} notes{dry_run_str}")

    def on_failure(self, e: Exception) -> None:
        showInfo(f"Failed: {e}")

    def on_click(self) -> None:
        log.info("on_click")
        note_ids: Sequence[NoteId] = self.__browser.selected_notes()
        log.info(f"Selected notes: {len(note_ids)}")
        op: CleanOperation[OpChanges] = CleanOperation(note_ids, self.__browser, self.__dry_run)
        op.success(self.on_success)
        op.failure(self.on_failure)
        op.run_in_background()
