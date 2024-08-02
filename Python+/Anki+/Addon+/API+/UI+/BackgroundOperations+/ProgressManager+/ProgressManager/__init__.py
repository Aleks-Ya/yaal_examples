import time

from anki.collection import Collection
from aqt import mw
from aqt.operations import QueryOp
from aqt.utils import showInfo

from ._common.disable import enabled
from ._common import menu


def __my_background_op(_: Collection) -> int:
    end: int = 20
    first: int = 0
    __update_progress_in_main_thread(__prefix(first, end), first, end)
    last_progress: float = time.time()
    for i in range(1, end):
        time.sleep(1)
        if time.time() - last_progress >= 0.1:
            __update_progress_in_main_thread(__prefix(i, end), i, end)
            if mw.progress.want_cancel():
                __update_progress_in_main_thread("Cancelling...", end - i, end)
                time.sleep(2)
                break
        last_progress = time.time()
    return end


def __update_progress_in_main_thread(label: str, i: int, end: int) -> None:
    mw.taskman.run_on_main(lambda: __update_progress(label, end - i, end))


def __update_progress(label: str, i: int, end: int):
    mw.progress.set_title("Custom progress")
    mw.progress.update(label=label, value=end - i, max=end)


def __prefix(i: int, end: int) -> str:
    return f"Remaining: {end - i}"


def __on_success(count: int) -> None:
    showInfo(f"Background operation returned {count}")


def __on_action() -> None:
    op: QueryOp = QueryOp(parent=mw, op=__my_background_op, success=__on_success)
    op.with_progress().run_in_background()


if enabled(True):
    menu.add_mw_menu_item("Show ProgressManager", __on_action)
