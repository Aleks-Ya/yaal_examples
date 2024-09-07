import threading
import time
from unittest.mock import MagicMock

import aqt
from anki.collection import Collection
from anyio import sleep
from aqt.operations import QueryOp
from aqt.taskman import TaskManager


def test_run_query_op():
    assert threading.current_thread().name == "MainThread"
    aqt.mw = MagicMock()
    aqt.mw.taskman = TaskManager(aqt.mw)
    op: CustomQueryOp = CustomQueryOp()
    op.run()
    time.sleep(2)
    assert op.finished()


class CustomQueryOp:
    def __init__(self):
        self.__finished: bool = False

    def run(self):
        QueryOp(parent=aqt.mw, op=self.__background_op, success=self.__on_success).run_in_background()

    def __background_op(self, _: Collection) -> int:
        assert threading.current_thread().name != "MainThread"
        sleep(1)
        return 77

    def __on_success(self, _: int) -> None:
        self.__finished = True

    def finished(self) -> bool:
        return self.__finished
