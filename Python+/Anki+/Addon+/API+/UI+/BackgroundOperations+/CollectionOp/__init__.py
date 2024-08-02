# Display progress (read-write)
import time

from anki.collection import OpChanges, Collection
from anki.notes import NoteId, Note
from aqt import mw
from aqt.operations import CollectionOp, ResultWithChanges
from aqt.utils import showInfo

from ._common.disable import enabled
from ._common import menu


def __my_background_op(col: Collection) -> ResultWithChanges:
    note: Note = mw.col.get_note(NoteId(1722727150214))
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


def __on_success(result: ResultWithChanges) -> None:
    showInfo(f"Long-running read-write operation succeeded: {result}")


def __on_failure(e: Exception) -> None:
    showInfo(f"Long-running read-write operation failed: {e}")


def __my_ui_action():
    op: CollectionOp[OpChanges] = CollectionOp(parent=mw, op=lambda col: __my_background_op(col))
    op.success(__on_success)
    op.failure(__on_failure)
    op.run_in_background()


if enabled():
    menu.add_mw_menu_item("Start long-running operation (read-write)", __my_ui_action)
