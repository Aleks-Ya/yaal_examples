# Display progress (read-only)
import time

from anki.collection import Collection
from aqt import mw
from aqt.operations import QueryOp
from aqt.utils import show_info

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
                if mw.progress.want_cancel():
                    time.sleep(3)
                    break
            col.find_notes("deck: *")
            last_progress = time.time()
        return self.end

    def __on_success(self, count: int) -> None:
        show_info(f"{self.success_message} {count}")


def __ui_action() -> None:
    WithProgressQueryOp().run()


if enabled(True):
    menu.add_mw_menu_item("Start long-running operation with progress 2", __ui_action)
