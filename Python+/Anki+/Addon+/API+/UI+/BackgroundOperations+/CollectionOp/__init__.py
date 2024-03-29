# Display progress (read-write)
import time

from anki.collection import OpChanges, Collection
from anki.notes import NoteId, Note
from aqt import mw
from aqt.operations import CollectionOp, ResultWithChanges
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo


def my_background_op(col: Collection) -> ResultWithChanges:
    note: Note = mw.col.get_note(NoteId(1699885664723))
    end = 10
    last_progress = time.time()
    for i in range(1, end + 1):
        time.sleep(1)
        note['Comment'] = f"Timer: {i}"
        col.update_note(note)
        if time.time() - last_progress >= 0.1:
            mw.taskman.run_on_main(
                lambda: mw.progress.update(
                    label=f"Remaining: {end - i}",
                    value=end - i,
                    max=end
                )
            )
            if mw.progress.want_cancel():
                mw.taskman.run_on_main(
                    lambda: mw.progress.update(
                        label="Cancelling...",
                        value=end - i,
                        max=end
                    )
                )
                time.sleep(3)
                break
        last_progress = time.time()
    return OpChanges(note_text=True)


def on_success(result: ResultWithChanges) -> None:
    showInfo(f"Long-running read-write operation succeeded: {result}")


def on_failure(e: Exception) -> None:
    showInfo(f"Long-running read-write operation failed: {e}")


def my_ui_action():
    op: CollectionOp[OpChanges] = CollectionOp(parent=mw, op=lambda col: my_background_op(col))
    op.success(on_success)
    op.failure(on_failure)
    op.run_in_background()


action: QAction = QAction('Start long-running operation (read-write)', mw)
qconnect(action.triggered, my_ui_action)
mw.form.menuTools.addAction(action)
