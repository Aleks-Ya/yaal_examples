import logging
import os
from pathlib import Path
from typing import Sequence, List

from anki.collection import OpChanges, Collection, OpChangesWithCount
from anki.notes import NoteId, Note
from aqt import mw
from aqt.operations import CollectionOp, ResultWithChanges
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo

from .highlight import highlight

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


def _background_operation(col: Collection) -> ResultWithChanges:
    changes = OpChangesWithCount()
    _highlight(changes, col)
    return changes


def _highlight(changes, col):
    field_names: List[str] = [
        'Examples2-generated',
        'Example-my',
        'Example-real-life'
    ]
    for field in field_names:
        note_ids: Sequence[NoteId] = col.find_notes(f"{field}:_*")
        log.info(f"Found notes: field={field}, notes={len(note_ids)}")
        for note_id in note_ids:
            note: Note = col.get_note(note_id)
            english: str = note['English']
            log.info(f'Processing word: {english}')
            old_value: str = note[field]
            new_value: str = highlight(english, old_value)
            if new_value != old_value:
                log.info(f"Updating note: {note_id}")
                log.info(f"Field {field} old value:\n{old_value}")
                log.info(f"Field {field} new value:\n{new_value}")
                note[field] = new_value
                col.update_note(note)
                changes.count += 1
            else:
                log.info(f"Not changed field: nid={note_id}, field={field}, word={english}, value={old_value}")
            mw.taskman.run_on_main(
                lambda: mw.progress.update(label=f"Updated: {changes.count}")
            )
            if mw.progress.want_cancel():
                log.info(f"Cancelling")
                return


def on_success(changes: ResultWithChanges) -> None:
    showInfo(f"Finished: updated {changes.count} notes")


def on_failure(e: Exception) -> None:
    log.error(e)
    showInfo(f"Failed: {e}")


def _ui_action():
    op: CollectionOp[OpChanges] = CollectionOp(parent=mw, op=lambda col: _background_operation(col))
    op.success(on_success)
    op.failure(on_failure)
    op.run_in_background()


action = QAction('Highlight English words', mw)
qconnect(action.triggered, _ui_action)
mw.form.menuTools.addAction(action)
