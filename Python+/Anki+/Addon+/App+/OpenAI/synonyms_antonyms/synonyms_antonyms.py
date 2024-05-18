# Anki add-on.
# 1. Adds "Fill synonyms and antonyms" item in "Main menu"/"Tools".
# 2. On click, perform find by regex and replace in specified note fields.
import logging
from csv import DictReader
from io import StringIO
from typing import Sequence, List

from anki.collection import OpChanges, OpChangesWithCount, Collection
from anki.notes import NoteId, Note
from aqt import mw
from aqt.operations import CollectionOp, ResultWithChanges
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo

from common.fields import synonym1_field, synonyms_field, antonyms_field, antonym1_field
from common.tags import absent_synonym1_tag, absent_synonyms_tag, absent_antonyms_tag, unit_tag, absent_antonym1_tag
from .columns import nid_column
from .note_filler import fill_note
from .openai_api import request_opanai_answer

log: logging.Logger = logging.getLogger(__name__)

dry_run: bool = False


class WantCancelException(Exception):
    pass


def _update_notes(notes_to_update: List[Note], col: Collection, changes: OpChangesWithCount):
    log.info(f"Notes to update in slice: {len(notes_to_update)}")
    message = request_opanai_answer(notes_to_update)
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


def _background_operation(col: Collection) -> ResultWithChanges:
    changes: OpChangesWithCount = OpChangesWithCount()
    query: str = (f'(({synonym1_field}: -tag:{absent_synonym1_tag}) OR '
                  f'({synonyms_field}: -tag:{absent_synonyms_tag}) OR '
                  f'({antonym1_field}: -tag:{absent_antonym1_tag}) OR '
                  f'({antonyms_field}: -tag:{absent_antonyms_tag})) '
                  f'-tag:{unit_tag}')
    log.info(f"Query: '{query}'")
    note_ids: Sequence[NoteId] = col.find_notes(query)
    log.info(f"Found notes: {len(note_ids)}")
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
    _show_progress(changes, notes_to_update_size)
    slice_size: int = 10

    for i in range(0, notes_to_update_size, slice_size):
        log.info(f"Processed notes: {i} from {notes_to_update_size}")
        sublist: List[Note] = notes_to_update[i:i + slice_size]
        try:
            _update_notes(sublist, col, changes)
            _show_progress(changes, notes_to_update_size)
        except WantCancelException:
            log.info("Cancelling")
            return changes
    return changes


def _show_progress(changes: OpChangesWithCount, notes_len: int):
    mw.taskman.run_on_main(
        lambda: mw.progress.update(
            label=f"Updated: {changes.count} from {notes_len}",
            value=changes.count,
            max=notes_len
        )
    )


def on_success(changes: ResultWithChanges) -> None:
    dry_run_str: str = " (DRY RUN)" if dry_run else ""
    showInfo(f"Finished: updated {changes.count} notes{dry_run_str}")


def on_failure(e: Exception) -> None:
    showInfo(f"Failed: {e}")


def _ui_action():
    op: CollectionOp[OpChanges] = CollectionOp(parent=mw, op=lambda col: _background_operation(col))
    op.success(on_success)
    op.failure(on_failure)
    op.run_in_background()


def menu_action() -> QAction:
    action = QAction('Fill synonyms and antonyms', mw)
    qconnect(action.triggered, _ui_action)
    return action
