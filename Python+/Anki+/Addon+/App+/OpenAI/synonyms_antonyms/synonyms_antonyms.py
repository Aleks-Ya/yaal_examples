# Anki add-on.
# 1. Adds "Fill synonyms and antonyms" item in "Main menu"/"Tools".
# 2. On click, perform find by regex and replace in specified note fields.
import logging
from csv import DictReader
from io import StringIO
from typing import Sequence, List, Optional, Any, Callable

from PyQt6.QtWidgets import QWidget
from anki.collection import OpChanges, OpChangesWithCount, Collection
from anki.notes import NoteId, Note
from aqt import mw
from aqt.browser import Browser
from aqt.operations import CollectionOp, ResultWithChanges
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo

from common.fields import synonym1_field, synonyms_field, antonyms_field, antonym1_field
from common.tags import absent_synonym1_tag, absent_synonyms_tag, absent_antonyms_tag, unit_tag, absent_antonym1_tag
from .columns import nid_column
from .note_filler import fill_note
from .openai_api import OpenAiApi

log: logging.Logger = logging.getLogger(__name__)
dry_run: bool = False


class WantCancelException(Exception):
    pass


class SynonymsAntonyms:
    def __init__(self, config: Optional[dict[str, Any]]):
        self.open_ai_api: OpenAiApi = OpenAiApi(config)

    def _update_notes(self, notes_to_update: List[Note], col: Collection, changes: OpChangesWithCount):
        log.info(f"Notes to update in slice: {len(notes_to_update)}")
        message = self.open_ai_api.request_answer(notes_to_update)
        with StringIO(message) as csv_file:
            reader: DictReader = DictReader(csv_file, doublequote=True)
            for row in reader:
                if mw.progress.want_cancel():
                    raise WantCancelException()
                log.debug(f"Row: {row}")
                nid_int: int = int(row[nid_column])
                note: Note = col.get_note(NoteId(nid_int))
                note = fill_note(note, row)
                if not dry_run:
                    col.update_note(note)
                else:
                    log.warning(f"Skipped update because dry_run={dry_run}")
                changes.count += 1

    def _background_operation_all_notes(self, col: Collection) -> ResultWithChanges:
        query: str = (f'(({synonym1_field}: -tag:{absent_synonym1_tag}) OR '
                      f'({synonyms_field}: -tag:{absent_synonyms_tag}) OR '
                      f'({antonym1_field}: -tag:{absent_antonym1_tag}) OR '
                      f'({antonyms_field}: -tag:{absent_antonyms_tag})) '
                      f'-tag:{unit_tag}')
        log.info(f"Query: '{query}'")
        note_ids: Sequence[NoteId] = col.find_notes(query)
        return self._background_operation_given_notes(col, note_ids)

    def _background_operation_selected_notes(self, col: Collection, browser: Browser) -> ResultWithChanges:
        note_ids: Sequence[NoteId] = browser.selected_notes()
        return self._background_operation_given_notes(col, note_ids)

    def _background_operation_given_notes(self, col: Collection, note_ids: Sequence[NoteId]) -> ResultWithChanges:
        log.info(f"Found notes: {len(note_ids)}")
        changes: OpChangesWithCount = OpChangesWithCount()
        notes_to_update: List[Note] = []
        for note_id in note_ids:
            note: Note = col.get_note(note_id)
            synonym1_old: str = note[synonym1_field]
            synonyms_old: str = note[synonyms_field]
            antonym1_old: str = note[antonym1_field]
            antonyms_old: str = note[antonyms_field]
            need_update: bool = not synonym1_old or not synonyms_old or not antonym1_old or not antonyms_old
            if need_update:
                notes_to_update.append(note)
        notes_to_update_size: int = len(notes_to_update)
        log.info(f"Notes to update: {notes_to_update_size}")
        self._show_progress(changes, notes_to_update_size)
        slice_size: int = 10

        for i in range(0, notes_to_update_size, slice_size):
            log.info(f"Processed notes: {i} from {notes_to_update_size}")
            sublist: List[Note] = notes_to_update[i:i + slice_size]
            try:
                self._update_notes(sublist, col, changes)
                self._show_progress(changes, notes_to_update_size)
            except WantCancelException:
                log.info("Cancelling")
                return changes
        return changes

    def _show_progress(self, changes: OpChangesWithCount, notes_len: int):
        mw.taskman.run_on_main(
            lambda: mw.progress.update(
                label=f"Updated: {changes.count} from {notes_len}",
                value=changes.count,
                max=notes_len
            )
        )

    def on_success(self, changes: ResultWithChanges) -> None:
        dry_run_str: str = " (DRY RUN)" if dry_run else ""
        showInfo(f"Finished: updated {changes.count} notes{dry_run_str}")

    def on_failure(self, e: Exception) -> None:
        showInfo(f"Failed: {e}\nCause:{e.__cause__}")

    def _ui_action(self, parent: QWidget, operation: Callable[[Collection], ResultWithChanges]):
        op: CollectionOp[OpChanges] = CollectionOp(parent=parent, op=operation)
        op.success(self.on_success)
        op.failure(self.on_failure)
        op.run_in_background()

    def process_all_menu_action(self, parent: QWidget) -> QAction:
        action = QAction('Fill synonyms and antonyms for all notes', parent)
        qconnect(action.triggered,
                 lambda: self._ui_action(parent, lambda col: self._background_operation_all_notes(col)))
        return action

    def process_selected_menu_action(self, browser: Browser) -> QAction:
        action = QAction('Fill synonyms and antonyms for selected notes', browser)
        qconnect(action.triggered,
                 lambda: self._ui_action(browser, lambda col: self._background_operation_selected_notes(col, browser)))
        return action
