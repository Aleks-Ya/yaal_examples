# Display progress (read-only)
import time

from aqt import mw
from aqt.operations import QueryOp
from aqt.qt import QAction, qconnect
from aqt.utils import showInfo


def my_background_op() -> int:
    end = 20
    last_progress = time.time()
    for i in range(1, end):
        time.sleep(1)
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
    return end


def on_success(count: int) -> None:
    showInfo(f"my_background_op() returned {count}")


def my_ui_action():
    op: QueryOp = QueryOp(parent=mw, op=lambda col: my_background_op(), success=on_success)
    op.with_progress().run_in_background()


action: QAction = QAction('Start long-running operation (read-only)', mw)
qconnect(action.triggered, my_ui_action)
mw.form.menuTools.addAction(action)
