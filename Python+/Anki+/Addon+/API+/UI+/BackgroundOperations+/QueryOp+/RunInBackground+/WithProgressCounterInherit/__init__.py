import time

from anki.collection import Collection
from aqt import mw, QWidget
from aqt.operations import QueryOp
from aqt.progress import ProgressManager
from aqt.taskman import TaskManager
from aqt.utils import show_info, show_critical

from ._common.disable import enabled
from ._common import menu


class WithProgressQueryOp(QueryOp):
    def __init__(self, parent: QWidget, progress_manager: ProgressManager, task_manager: TaskManager):
        super().__init__(parent=parent, op=self.__background_op, success=self.__on_success)
        self.with_progress("Long operation")
        self.__progress_manager: ProgressManager = progress_manager
        self.__task_manager: TaskManager = task_manager
        self.__end: int = 5
        self.__success_message: str = "The background operation returned:"
        self.__failure_message: str = "Failed"

    def __background_op(self, col: Collection) -> int:
        self.__progress_manager.set_title("WithProgressQueryOp is working")
        last_progress: float = time.time()
        for i in range(1, self.__end):
            time.sleep(1)
            if time.time() - last_progress >= 0.1:
                self.__update_progress(f"Remaining: {self.__end - i}", self.__end - i, self.__end)
                if self.__progress_manager.want_cancel():
                    self.__update_progress("Cancelling...", self.__end - i, self.__end)
                    time.sleep(2)
                    return i
            col.find_notes("deck: *")
            last_progress = time.time()
        return self.__end

    def __update_progress(self, label: str, value: int, max_value: int) -> None:
        self.__task_manager.run_on_main(lambda: self.__progress_manager.update(label, value, True,
                                                                               True, max_value))

    def __on_success(self, count: int) -> None:
        show_info(f"{self.__success_message} {count}")

    def __on_failure(self, e: Exception) -> None:
        show_critical(f"{self.__failure_message}: {e}")


def __ui_action() -> None:
    parent: QWidget = mw
    progress_manager: ProgressManager = mw.progress
    task_manager: TaskManager = mw.taskman
    op: WithProgressQueryOp = WithProgressQueryOp(parent, progress_manager, task_manager)
    op.run_in_background()


if enabled(True):
    menu.add_mw_menu_item("Start long-running operation with progress", __ui_action)
