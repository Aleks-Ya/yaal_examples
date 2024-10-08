import time

from anki.collection import Collection
from aqt import mw
from aqt.operations import QueryOp
from aqt.utils import show_info

from ._common.disable import enabled
from ._common import menu


class NoProgressQueryOp:
    def __init__(self):
        self.end: int = 5
        self.success_message: str = "The background operation returned:"

    def run(self):
        QueryOp(parent=mw, op=self.__background_op, success=self.__on_success).run_in_background()

    def __background_op(self, _: Collection) -> int:
        for i in range(1, self.end):
            time.sleep(1)
        return self.end

    def __on_success(self, count: int) -> None:
        show_info(f"{self.success_message} {count}")


def __ui_action() -> None:
    NoProgressQueryOp().run()


if enabled(True):
    menu.add_mw_menu_item("Start a 5 sec background QueryOp", __ui_action)
