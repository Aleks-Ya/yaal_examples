import threading
import time
from unittest.mock import MagicMock

import aqt
from aqt import QWidget
from anki.collection import Collection
from anyio import sleep
from aqt.operations import QueryOp
from aqt.taskman import TaskManager


def test_run_query_op():
    assert threading.current_thread().name == "MainThread"
    aqt.mw = MagicMock()
    aqt.mw.taskman = TaskManager(aqt.mw)
    op: CustomQueryOp = CustomQueryOp(aqt.mw)
    op.run_in_background()
    time.sleep(2)
    assert op.get_final_result() == 77


class CustomQueryOp(QueryOp):
    def __init__(self, parent: QWidget):
        super().__init__(parent=parent, op=self.__background_op, success=self.__on_success)
        assert threading.current_thread().name == "MainThread"
        self.__final_result: int = -1

    def __background_op(self, _: Collection) -> int:
        assert threading.current_thread().name != "MainThread"
        sleep(1)
        return 77

    def __on_success(self, result: int) -> None:
        assert threading.current_thread().name == "MainThread"
        self.__final_result = result

    def get_final_result(self) -> int:
        assert threading.current_thread().name == "MainThread"
        return self.__final_result
