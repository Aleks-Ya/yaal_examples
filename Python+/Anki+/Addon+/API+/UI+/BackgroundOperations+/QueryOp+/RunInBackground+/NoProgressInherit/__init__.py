import time

from anki.collection import Collection
from aqt import mw, QWidget
from aqt.operations import QueryOp
from aqt.utils import show_info, show_critical

from ._common.disable import enabled
from ._common import menu


class NoProgressQueryOp(QueryOp):
    def __init__(self, parent: QWidget):
        super().__init__(parent=parent, op=self.__background_op, success=self.__on_success)
        self.failure(self.__on_failure)
        self.__end: int = 5
        self.__success_message: str = "The background operation returned"
        self.__failure_message: str = "Failed"

    def __background_op(self, _: Collection) -> int:
        for i in range(1, self.__end):
            time.sleep(1)
        return self.__end

    def __on_success(self, count: int) -> None:
        show_info(f"{self.__success_message}: {count}")

    def __on_failure(self, e: Exception) -> None:
        show_critical(f"{self.__failure_message}: {e}")


def __ui_action() -> None:
    parent: QWidget = mw
    op: NoProgressQueryOp = NoProgressQueryOp(parent)
    op.run_in_background()


if enabled(True):
    menu.add_mw_menu_item("Start a 5 sec background QueryOp", __ui_action)
