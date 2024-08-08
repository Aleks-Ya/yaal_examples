import time

from anki.collection import Collection
from aqt import mw, gui_hooks
from aqt.operations import QueryOp

from ._common.disable import enabled
from ._common import menu


class ConcurrentQueryOp:
    def __init__(self, name: str, end: int):
        self.__end: int = end
        self.__name: str = name

    def start(self):
        QueryOp(parent=mw, op=self.__background_op, success=self.__on_success).with_progress().run_in_background()

    def __background_op(self, _: Collection) -> int:
        for i in range(self.__end):
            self.__update_progress(f"{self.__name} working {i} seconds", i)
            if mw.progress.want_cancel():
                self.__update_progress(f"Cancelling {self.__name} at {i} second", i)
                time.sleep(1)
                break
            time.sleep(1)
        return self.__end

    def __update_progress(self, label: str, value: int) -> None:
        mw.taskman.run_on_main(lambda: self.__update_progress_on_main(label, value))

    def __update_progress_on_main(self, label: str, value: int) -> None:
        mw.progress.set_title(f"{self.__name} information")
        mw.progress.update(label=label, value=value, max=self.__end)

    def __on_success(self, count: int) -> None:
        pass


def __ui_action() -> None:
    ConcurrentQueryOp("Operation #1", 10).start()
    ConcurrentQueryOp("Operation #2", 5).start()
    ConcurrentQueryOp("Operation #3", 7).start()


if enabled():
    gui_hooks.profile_did_open.append(__ui_action)
    menu.add_mw_menu_item("Start concurrent QueryOps", __ui_action)
