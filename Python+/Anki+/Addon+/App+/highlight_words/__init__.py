import logging
import os
from pathlib import Path
from typing import Sequence, List, Callable

from PyQt6.QtWidgets import QMenu
from anki.collection import OpChanges, Collection, OpChangesWithCount
from anki.notes import NoteId, Note
from aqt import mw
from aqt.operations import CollectionOp, ResultWithChanges
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo

from .highlight import highlight, remove_highlight

init_py_file: Path = Path(__file__)
addon_dir: Path = init_py_file.parent
addon_name: str = addon_dir.name
log_file = os.path.join(addon_dir, f"{addon_name}.log")
log: logging.Logger = logging.getLogger(addon_name)
log.setLevel(logging.DEBUG)
handler: logging.FileHandler = logging.FileHandler(log_file)
handler.setLevel(logging.DEBUG)
handler.setFormatter(logging.Formatter('%(asctime)s %(name)s %(funcName)s %(levelname)s %(message)s'))
log.addHandler(handler)
log.info(f'Logger is configured: file={log_file}')

dry_run: bool = False

field_names: List[str] = [
    'Examples2-generated',
    'Example-my',
    'Example-real-life'
]


def _process(col: Collection, operation: Callable[[str, str], str]) -> ResultWithChanges:
    changes = OpChangesWithCount()
    for field in field_names:
        note_ids: Sequence[NoteId] = col.find_notes(f"{field}:_*")
        log.info(f"Found notes: field={field}, notes={len(note_ids)}")
        for note_id in note_ids:
            note: Note = col.get_note(note_id)
            english: str = note['English']
            log.info(f'Processing word: {english}')
            old_value: str = note[field]
            new_value: str = operation(english, old_value)
            if new_value != old_value:
                log.info(f"Updating note: {note_id}")
                log.info(f"Field {field} old value:\n{old_value}")
                log.info(f"Field {field} new value:\n{new_value}")
                note[field] = new_value
                if not dry_run:
                    col.update_note(note)
                else:
                    log.warning(f"Skipped update because dry_run={dry_run}")
                changes.count += 1
            else:
                log.info(f"Not changed field: nid={note_id}, field={field}, word={english}, value={old_value}")
            mw.taskman.run_on_main(
                lambda: mw.progress.update(label=f"Updated: {changes.count}")
            )
            if mw.progress.want_cancel():
                log.info(f"Cancelling")
                return changes
    return changes


def _highlight_background_operation(col: Collection) -> ResultWithChanges:
    log.info("Add highlighting")
    return _process(col, lambda english, old_value: highlight(english, old_value))


def _remove_highlight_background_operation(col: Collection) -> ResultWithChanges:
    log.info("Remove highlighting")
    return _process(col, lambda english, old_value: remove_highlight(old_value))


def on_success(changes: ResultWithChanges) -> None:
    dry_run_str: str = " (DRY RUN)" if dry_run else ""
    showInfo(f"Finished: updated {changes.count} notes{dry_run_str}")


def on_failure(e: Exception) -> None:
    log.error(e)
    showInfo(f"Failed: {e}")


def _ui_highlight_action():
    op: CollectionOp[OpChanges] = CollectionOp(parent=mw, op=_highlight_background_operation)
    op.success(on_success)
    op.failure(on_failure)
    op.run_in_background()


def _ui_remove_highlighting_action():
    op: CollectionOp[OpChanges] = CollectionOp(parent=mw, op=_remove_highlight_background_operation)
    op.success(on_success)
    op.failure(on_failure)
    op.run_in_background()


parent_menu: QMenu = QMenu("Highlight words", mw)
mw.form.menuTools.addMenu(parent_menu)

highlight_action = QAction('Highlight words', mw)
qconnect(highlight_action.triggered, _ui_highlight_action)
parent_menu.addAction(highlight_action)

remove_action = QAction('Remove highlighting', mw)
qconnect(remove_action.triggered, _ui_remove_highlighting_action)
parent_menu.addAction(remove_action)
