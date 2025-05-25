import time

from anki.collection import Collection
from aqt import mw
from aqt.operations import QueryOp
from aqt.utils import show_info

from ._common.disable import enabled
from ._common import menu


def __background_op(_: Collection) -> int:
    end = 5
    for i in range(1, end):
        time.sleep(1)
    return end


def __on_success(count: int) -> None:
    show_info(f"__background_op() returned {count}")


def __ui_action() -> None:
    op: QueryOp = QueryOp(parent=mw, op=__background_op, success=__on_success)
    op.without_collection().with_progress().run_in_background()


if enabled(False):
    menu.add_mw_menu_item("Start a QueryOp without Collection", __ui_action)
