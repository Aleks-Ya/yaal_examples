# Display progress (read-only)
import time

from anki.collection import Collection
from aqt import mw
from aqt.operations import QueryOp
from aqt.utils import showInfo

from ._common.disable import enabled
from ._common import menu


class WithProgressQueryOp:
    def __init__(self):
        self.end: int = 5
        self.success_message: str = "The background operation returned:"

    def run(self):
        QueryOp(parent=mw, op=self.__background_op, success=self.__on_success).with_progress().run_in_background()
        mw.progress.set_title("WithProgressQueryOp is working")

    def __background_op(self, col: Collection) -> int:
        last_progress = time.time()
        for i in range(1, self.end):
            time.sleep(1)
            if time.time() - last_progress >= 0.1:
                self.__update_progress(f"Remaining: {self.end - i}", self.end - i, self.end)
                if mw.progress.want_cancel():
                    self.__update_progress("Cancelling...", self.end - i, self.end)
                    time.sleep(3)
                    break
            col.find_notes("deck: *")
            last_progress = time.time()
        return self.end

    @staticmethod
    def __update_progress(label: str, value: int, max_value: int) -> None:
        mw.taskman.run_on_main(lambda: mw.progress.update(label, value, max_value))

    def __on_success(self, count: int) -> None:
        showInfo(f"{self.success_message} {count}")


def __ui_action() -> None:
    WithProgressQueryOp().run()


if enabled(True):
    menu.add_mw_menu_item("Start long-running operation with progress 2", __ui_action)
